package org.jeesl.interfaces.model.module.approval;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslApprovalAction <T extends JeeslApprovalTransition<?,?,?>,
										AB extends JeeslApprovalBot<AB,?,?,?>,
										RA extends JeeslRevisionAttribute<?,?,?,?,?>>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithPosition,EjbWithParentAttributeResolver
				
{
	public static enum Attributes{transition}
	
	T getTransition();
	void setTransition(T transition);

	AB getBot();
	void setBot(AB bot);
	
	RA getAttribute();
	void setAttribute(RA attribute);
}