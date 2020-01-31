package org.jeesl.api.facade.module;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.asset.JeeslAssetCompany;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslOmFacade <L extends UtilsLang, D extends UtilsDescription,
									COMPANY extends JeeslAssetCompany<?,?>>
			extends JeeslFacade
{	

}