package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

public interface JeeslSecurityMenu<V extends JeeslSecurityView<?,?,?,?,?,?>,
								   M extends JeeslSecurityMenu<V,M>>
			extends Serializable,EjbSaveable,EjbWithPosition
{
	public static final String extractId = "securityMenu";
	public static final String keyRoot = "root";
	
	M getParent();
	void setParent(M menu);
	
	V getView();
	void setView(V view);
}