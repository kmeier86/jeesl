package org.jeesl.interfaces.model.system.locale.status;

import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

public interface JeeslStatusFixedCode extends EjbWithCode
{					
	public List<String> getFixedCodes();
}