package org.jeesl.interfaces.model.module.ts.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisibleParent;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslTsEntityClass <L extends JeeslLang, D extends JeeslDescription,
									CAT extends JeeslStatus<CAT,L,D>>
		extends Serializable,EjbPersistable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,
				EjbWithPositionVisibleParent,EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{
	CAT getCategory();
	void setCategory(CAT category);
	
	String getAttribute();
	void setAttribute(String attribute);
	
	String getXpathParent();
	void setXpathParent(String xpathParent);
	
	String getXpath();
	void setXpath(String xpath);
	
	String getXpathName();
	void setXpathName(String xpathName);
}