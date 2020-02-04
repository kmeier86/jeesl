package org.jeesl.interfaces.model.with.parent;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;

import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;

public interface JeeslWithParentAttributeStatus <STATUS extends JeeslStatus<STATUS,?,?>> 
								extends EjbWithParentAttributeResolver,JeeslWithStatus<STATUS>
{


}