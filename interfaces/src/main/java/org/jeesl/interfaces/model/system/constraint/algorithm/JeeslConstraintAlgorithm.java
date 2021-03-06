package org.jeesl.interfaces.model.system.constraint.algorithm;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.status.JeeslWithCategory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslConstraintAlgorithm<L extends JeeslLang, D extends JeeslDescription,
									CATEGORY extends JeeslStatus<CATEGORY,L,D>
									>
			extends Serializable,EjbSaveable,EjbRemoveable,
					EjbWithId,EjbWithCode,
					JeeslWithCategory<CATEGORY>,
					EjbWithPosition,EjbWithPositionParent,
					EjbWithLangDescription<L,D>
{
	public enum Attributes{category}
}