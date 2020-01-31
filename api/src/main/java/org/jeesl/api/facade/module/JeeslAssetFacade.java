package org.jeesl.api.facade.module;

import java.util.List;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAssetFacade <L extends UtilsLang, D extends UtilsDescription,
									REALM extends JeeslAomRealm<L,D,REALM,?>,
									COMPANY extends JeeslAomCompany<REALM,SCOPE>,
									SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
									ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
									STATUS extends JeeslAomStatus<L,D,STATUS,?>,
									TYPE extends JeeslAomType<L,D,REALM,TYPE,?>>
			extends JeeslFacade
{	
	<RREF extends EjbWithId> ASSET fcAssetRoot(REALM realm, RREF rref);
	<RREF extends EjbWithId> TYPE fcAssetRootType(REALM realm, RREF rref);
	<RREF extends EjbWithId> List<COMPANY> fAssetCompanies(REALM realm, RREF rref, SCOPE scope);
}