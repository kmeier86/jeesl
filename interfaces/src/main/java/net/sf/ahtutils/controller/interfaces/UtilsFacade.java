package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithType;

public interface UtilsFacade
{
	<T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException;
	<T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException;
	
	<T extends Object> List<T> all(Class<T> type);
	<T extends EjbWithType> List<T> allForType(Class<T> clazz, String type);
	
	<T extends Object> T persist(T o) throws UtilsContraintViolationException;
	<T extends Object> T update(T o) throws UtilsContraintViolationException,UtilsLockingException;
	
	<T extends EjbRemoveable> void rm(T o) throws UtilsIntegrityException;
}
