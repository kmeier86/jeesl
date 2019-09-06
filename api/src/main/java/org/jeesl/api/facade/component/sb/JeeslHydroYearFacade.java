package org.jeesl.api.facade.component.sb;

import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslHydroYearFacade <L extends UtilsLang,D extends UtilsDescription,
											HD extends UtilsStatus<HD,L,D>,
											HY extends JeeslHydroYear<L,D,HD,HY>>
			extends UtilsFacade
{

}