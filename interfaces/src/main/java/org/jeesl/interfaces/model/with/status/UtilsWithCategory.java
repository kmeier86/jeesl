package org.jeesl.interfaces.model.with.status;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsWithCategory<L extends JeeslLang,D extends JeeslDescription,CATEGORY extends JeeslStatus<CATEGORY,L,D>>
						extends EjbWithId
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}