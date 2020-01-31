package org.jeesl.api.bean.module.aom;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAssetCacheBean <L extends UtilsLang, D extends UtilsDescription,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
										STATUS extends JeeslAomStatus<L,D,STATUS,?>,
										TYPE extends JeeslAomType<L,D,REALM,TYPE,?>>
								extends Serializable
{
//	void reloadAssetTypes(JeeslAssetFacade<L,D,REALM,COMPANY,SCOPE,ASSET,STATUS,TYPE> fAsset, REALM realm, RREF rref);
	Map<REALM,Map<RREF,List<TYPE>>> getMapAssetType();
}