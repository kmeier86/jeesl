package org.jeesl.api.facade.component.sb;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslHydroDecadeFacade <L extends UtilsLang,D extends UtilsDescription,
											HD extends UtilsStatus<HD,L,D>>
			extends UtilsFacade
{

}