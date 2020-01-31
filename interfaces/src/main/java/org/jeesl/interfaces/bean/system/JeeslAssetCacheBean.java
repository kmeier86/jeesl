package org.jeesl.interfaces.bean.system;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAssetCacheBean <L extends UtilsLang, D extends UtilsDescription,
										REALM extends JeeslAomRealm<L,D,REALM,?>, RREF extends EjbWithId,
										ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
										COMPANY extends JeeslAomCompany<REALM,SCOPE>,
										SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
										STATUS extends JeeslAomStatus<L,D,STATUS,?>,
										TYPE extends JeeslAomType<L,D,REALM,TYPE,?>>
								extends Serializable
{
	
}