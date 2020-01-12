package org.jeesl.interfaces.bean.th;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

public interface ThMultiFilterBean
{
	void toggled(ThToggleFilter filter, Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException;
}