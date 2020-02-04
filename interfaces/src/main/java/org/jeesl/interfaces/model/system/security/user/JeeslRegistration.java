package org.jeesl.interfaces.model.system.security.user;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslRegistration <L extends JeeslLang, D extends JeeslDescription,
							USER extends JeeslUser<?>,
							REGSTATUS extends JeeslStatus<REGSTATUS,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable
{	
	USER getUser();
	void setUser(USER user);
	
	REGSTATUS getStatus();
	void setStatus(REGSTATUS status);
}