package net.sf.ahtutils.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsWithScope<L extends JeeslLang,D extends JeeslDescription,SCOPE extends JeeslStatus<SCOPE,L,D>>
						extends EjbWithId
{	
	SCOPE getScope();
	void setScope(SCOPE scope);
}