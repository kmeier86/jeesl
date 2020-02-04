package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithImageAlt extends EjbWithId
{
	String getImageAlt();
	void setImageAlt(String image);
}