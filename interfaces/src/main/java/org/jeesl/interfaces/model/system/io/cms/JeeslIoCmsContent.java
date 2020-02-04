package org.jeesl.interfaces.model.system.io.cms;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoCmsContent<V extends JeeslIoCmsVisiblity,
								E extends JeeslIoCmsElement<V,?,?,?,?,?>,
								MT extends JeeslIoCmsMarkupType<?,?,MT,?>>
		extends Serializable,EjbPersistable,
					EjbWithId,EjbSaveable,EjbRemoveable,EjbWithParentAttributeResolver
{	
	public enum Attributes{element}
	
	E getElement();
	void setElement(E element);
	
	String getLkey();
	void setLkey(String lkey);
	
	String getLang();
	void setLang(String name);
	
	MT getMarkup();
	void setMarkup(MT markup);
	
	boolean isFallback();
	void setFallback(boolean fallback);
}