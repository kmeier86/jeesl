package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetCompany;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetScope;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.asset.JeeslAssetType;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAssetFacade <L extends UtilsLang, D extends UtilsDescription,
									REALM extends JeeslAssetRealm<L,D,REALM,?>,
									ASSET extends JeeslAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
									COMPANY extends JeeslAssetCompany<REALM,SCOPE>,
									SCOPE extends JeeslAssetScope<L,D,SCOPE,?>,
									STATUS extends JeeslAssetStatus<L,D,STATUS,?>,
									TYPE extends JeeslAssetType<L,D,REALM,TYPE,?>>
			extends JeeslFacade
{	
	<RREF extends EjbWithId> ASSET fcAssetRoot(REALM realm, RREF realmReference);
	<RREF extends EjbWithId> TYPE fcAssetRootType(REALM realm, RREF realmReference);
}