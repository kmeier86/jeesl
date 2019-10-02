package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslTsSample 
		extends Serializable,EjbRemoveable,EjbPersistable,EjbWithId,EjbSaveable,EjbWithRecord
{
	enum Attributes{record}
}