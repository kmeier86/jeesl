package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithImage extends EjbWithId
{
	String getImage();
	void setImage(String image);
}
