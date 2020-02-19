package org.jeesl.api.bean;

public interface JeeslLabelResolver
{	
	String tlEntity(String localeCode, Class<?> c); 
	<E extends Enum<E>> String xpAttribute(String localeCode, Class<?> c, E code);
}