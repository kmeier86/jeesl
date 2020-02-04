package org.jeesl.interfaces.model.system.constraint;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslConstraintResolution<L extends JeeslLang, D extends JeeslDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									LEVEL extends JeeslStatus<LEVEL,L,D>,
									TYPE extends JeeslStatus<TYPE,L,D>,
									RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
			extends EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithPosition,
					EjbWithParentAttributeResolver,
					EjbWithLangDescription<L,D>
{
	public static enum Attributes{constraint};
	
	CONSTRAINT getConstraint();
	void setConstraint(CONSTRAINT constraint);
}