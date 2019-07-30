package org.jeesl.interfaces.model.system.with;

import java.util.Map;

import org.jeesl.interfaces.model.system.locale.JeeslMarkup;

public interface JeeslWithMarkup <M extends JeeslMarkup<?>>
{
	Map<String,M> getMarkup();
	void setMarkup(Map<String,M> translation);
}