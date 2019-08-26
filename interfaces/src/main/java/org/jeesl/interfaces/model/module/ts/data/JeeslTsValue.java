package org.jeesl.interfaces.model.module.ts.data;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslTsValue extends EjbWithId,EjbWithRecord
{
	Double getValue();
	void setValue(Double value);
}