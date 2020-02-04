package org.jeesl.interfaces.model.system.locale;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLang extends EjbWithId,EjbRemoveable,EjbSaveable
{	
	public static String attributeLang = "lang";
	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String lang);
}