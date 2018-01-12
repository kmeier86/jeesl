package org.jeesl.interfaces.controller.handler;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslFileRepositoryHandler <L extends UtilsLang, D extends UtilsDescription,
											STORAGE extends JeeslFileStorage<L,D,ENGINE>,
											ENGINE extends UtilsStatus<ENGINE,L,D>,
											CONTAINER extends JeeslFileContainer<STORAGE,META>,
											META extends JeeslFileMeta<CONTAINER,TYPE>,
											TYPE extends UtilsStatus<TYPE,L,D>>
		extends Serializable
{
	
}