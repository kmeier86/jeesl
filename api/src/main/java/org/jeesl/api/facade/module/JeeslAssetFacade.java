package org.jeesl.api.facade.module;

import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAssetFacade <L extends UtilsLang, D extends UtilsDescription,
									REALM extends JeeslAssetRealm<L,D,REALM,?>,
									ASSET extends JeeslAsset<REALM,ASSET,AS>,
									MANU extends JeeslAssetManufacturer,
									AS extends JeeslAssetStatus<L,D,AS,?>>
			extends UtilsFacade
{	
	<RREF extends EjbWithId> ASSET fcAssetRoot(REALM realm, RREF realmReference);
}