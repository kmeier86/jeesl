package org.jeesl.web.mbean.prototype.module.aom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.module.aom.JeeslAssetCacheBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAssetCacheBean <L extends UtilsLang, D extends UtilsDescription,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
										STATUS extends JeeslAomStatus<L,D,STATUS,?>,
										TYPE extends JeeslAomType<L,D,REALM,TYPE,?>>
								implements JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,STATUS,TYPE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetCacheBean.class);
	
	private JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fbAsset;

	private final Map<REALM,Map<RREF,List<TYPE>>> mapAssetType; @Override public Map<REALM,Map<RREF,List<TYPE>>> getMapAssetType() {return mapAssetType;}
	private final Map<REALM,Map<RREF,List<COMPANY>>> mapCompanyManufacturer; public Map<REALM,Map<RREF,List<COMPANY>>> getMapCompanyManufacturer() {return mapCompanyManufacturer;}
	
	public AbstractAssetCacheBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fbAsset)
	{
		this.fbAsset=fbAsset;
		
		mapAssetType = new HashMap<>();
		mapCompanyManufacturer = new HashMap<>();
	}
	
	@Override public void reloadAssetTypes(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset, REALM realm, RREF rref)
	{
		this.fAsset=fAsset;
		
		if(!mapAssetType.containsKey(realm)) {mapAssetType.put(realm,new HashMap<>());}
		
		
		if(!mapAssetType.get(realm).containsKey(rref)) {mapAssetType.get(realm).put(rref,new ArrayList<>());}
		else {mapAssetType.get(realm).get(rref).clear();}

		TYPE root = fAsset.fcAssetRootType(realm,rref);
		reloadTypes(realm,rref,fAsset.allForParent(fbAsset.getClassType(),root));
		logger.info(AbstractLogMessage.reloaded(fbAsset.getClassType(), mapAssetType.get(realm).get(rref), rref)+" in realm "+realm.toString());
	}
	
	private void reloadTypes(REALM realm, RREF rref, List<TYPE> types)
	{
		for(TYPE type : types)
		{
			mapAssetType.get(realm).get(rref).add(type);
			reloadTypes(realm,rref,fAsset.allForParent(fbAsset.getClassType(),type));
		}
	}
}