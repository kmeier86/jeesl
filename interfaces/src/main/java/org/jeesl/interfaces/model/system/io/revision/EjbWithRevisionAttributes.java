package org.jeesl.interfaces.model.system.io.revision;

import java.util.List;

import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithRevisionAttributes <RA extends JeeslRevisionAttribute<?,?,?,?,?>>
		extends EjbWithId
{
	List<RA> getAttributes();
	void setAttributes(List<RA> attributes);
}