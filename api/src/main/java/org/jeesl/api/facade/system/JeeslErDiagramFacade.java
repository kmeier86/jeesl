package org.jeesl.api.facade.system;

import org.jeesl.interfaces.model.system.erdiagram.JeeslErDiagram;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslErDiagramFacade <L extends UtilsLang,D extends UtilsDescription,
											C extends UtilsStatus<C,L,D>,
											ERD extends JeeslErDiagram<L,D,C,ERD>>
			extends UtilsFacade
{

}