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
import org.jeesl.interfaces.model.module.aom.op.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.op.JeeslAomEventType;
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
										TYPE extends JeeslAomType<L,D,REALM,TYPE,?>,
										EVENT extends JeeslAomEvent<COMPANY,ASSET>,
										ETYPE extends JeeslAomEventType<L,D,ETYPE,?>>
								implements JeeslAssetCacheBean<L,D,REALM,RREF,COMPANY,SCOPE,ASSET,STATUS,TYPE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetCacheBean.class);
	
//	private JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE,EVENT,ETYPE> fbAsset;

	private final Map<REALM,Map<RREF,List<TYPE>>> mapAssetType; @Override public Map<REALM,Map<RREF,List<TYPE>>> getMapAssetType() {return mapAssetType;}
	private final Map<REALM,Map<RREF,List<COMPANY>>> mapManufacturer; public Map<REALM,Map<RREF,List<COMPANY>>> getMapManufacturer() {return mapManufacturer;}
	private final Map<REALM,Map<RREF,List<COMPANY>>> mapVendor; public Map<REALM,Map<RREF,List<COMPANY>>> getMapVendor() {return mapVendor;}
	
    private final List<STATUS> assetStatus; public List<STATUS> getAssetStatus() {return assetStatus;}
    
	public AbstractAssetCacheBean(AssetFactoryBuilder<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE,EVENT,ETYPE> fbAsset)
	{
		this.fbAsset=fbAsset;
		
		mapAssetType = new HashMap<>();
		mapManufacturer = new HashMap<>();
		mapVendor = new HashMap<>();
		
		assetStatus = new ArrayList<>();
	}
	
	public void postConstruct(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset)
	{
		if(assetStatus.isEmpty()) {assetStatus.addAll(fAsset.allOrderedPositionVisible(fbAsset.getClassStatus()));}
	}
	
	public void reloadRealm(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset, REALM realm, RREF rref)
	{
		reloadAssetTypes(fAsset,realm,rref,false);
		reloadCompanies(fAsset,realm,rref,false,fAsset.fByEnum(fbAsset.getClassScope(),JeeslAomScope.Code.manufacturer),mapManufacturer);
		reloadCompanies(fAsset,realm,rref,false,fAsset.fByEnum(fbAsset.getClassScope(),JeeslAomScope.Code.vendor),mapVendor);
	}
	
	public void reloadAssetTypes(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset, REALM realm, RREF rref,boolean force)
	{		
		if(!mapAssetType.containsKey(realm)) {mapAssetType.put(realm,new HashMap<>());}	
		if(!mapAssetType.get(realm).containsKey(rref)) {mapAssetType.get(realm).put(rref,new ArrayList<>());}

		if(force || mapAssetType.get(realm).get(rref).isEmpty())
		{
			mapAssetType.get(realm).get(rref).clear();
			TYPE root = fAsset.fcAssetRootType(realm,rref);
			reloadTypes(fAsset,realm,rref,fAsset.allForParent(fbAsset.getClassAssetType(),root));
			logger.info(AbstractLogMessage.reloaded(fbAsset.getClassAssetType(), mapAssetType.get(realm).get(rref), rref)+" in realm "+realm.toString());
		}
	}
	private void reloadTypes(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset, REALM realm, RREF rref, List<TYPE> types)
	{
		for(TYPE type : types)
		{
			mapAssetType.get(realm).get(rref).add(type);
			reloadTypes(fAsset,realm,rref,fAsset.allForParent(fbAsset.getClassAssetType(),type));
		}
	}
	
	private void reloadCompanies(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset,
								REALM realm, RREF rref, boolean force, SCOPE scope,
								Map<REALM,Map<RREF,List<COMPANY>>> map)
	{		
		if(!map.containsKey(realm)) {map.put(realm,new HashMap<>());}
		if(!map.get(realm).containsKey(rref)) {map.get(realm).put(rref,new ArrayList<>());}
		
		if(force || map.get(realm).get(rref).isEmpty())
		{
			map.get(realm).get(rref).addAll(fAsset.fAssetCompanies(realm, rref, scope));
			logger.info(AbstractLogMessage.reloaded(fbAsset.getClassCompany(), map.get(realm).get(rref), rref)+" in realm "+realm.toString());
		}
	}
}