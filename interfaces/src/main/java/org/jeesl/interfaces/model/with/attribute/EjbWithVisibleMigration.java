package org.jeesl.interfaces.model.with.attribute;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithVisibleMigration extends EjbWithId
{
	public Boolean getVisible();
	public void setVisible(Boolean visible);
}