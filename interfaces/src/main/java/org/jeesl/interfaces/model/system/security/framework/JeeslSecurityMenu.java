package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;

import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;

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