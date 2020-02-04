package net.sf.ahtutils.interfaces.model.with;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithUtilsType<S extends JeeslStatus<S,L,D>, L extends JeeslLang, D extends JeeslDescription> extends EjbWithId
{
	S getType();
	void setType(S type);
}
