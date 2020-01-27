package org.jeesl.interfaces.model.module.om;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslOmCompany 
		extends Serializable,EjbSaveable,EjbWithName,EjbWithNonUniqueCode
{

}