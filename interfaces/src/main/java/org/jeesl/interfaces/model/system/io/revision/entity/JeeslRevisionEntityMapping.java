package org.jeesl.interfaces.model.system.io.revision.entity;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;

public interface JeeslRevisionEntityMapping<RS extends JeeslRevisionScope<?,?,?,?>,
											RST extends JeeslStatus<RST,?,?>,
											RE extends JeeslRevisionEntity<?,?,?,?,?,?>>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible
{			
	public static enum Type{xpath,jpqlTree}
	
	RS getScope();
	void setScope(RS scope);
	
	RST getType();
	void setType(RST type);
	
	RE getEntity();
	void setEntity(RE entity);
	
	String getXpath();
	void setXpath(String xpath);
	
	String getJpqlTree();
	void setJpqlTree(String jpqlTree);
}