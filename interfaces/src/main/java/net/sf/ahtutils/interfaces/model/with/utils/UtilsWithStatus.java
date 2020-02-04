package net.sf.ahtutils.interfaces.model.with.utils;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsWithStatus<L extends JeeslLang,D extends JeeslDescription,STATUS extends JeeslStatus<STATUS,L,D>>
						extends EjbWithId
{	
	//move to package status
	
	STATUS getStatus();
	void setStatus(STATUS status);
}