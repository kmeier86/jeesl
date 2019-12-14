package org.jeesl.interfaces.model.module.log;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLogBook <ITEM extends JeeslLogItem<?,?,?,?,?,?,?,?>>
		extends Serializable,EjbSaveable,EjbWithId
{
	
}