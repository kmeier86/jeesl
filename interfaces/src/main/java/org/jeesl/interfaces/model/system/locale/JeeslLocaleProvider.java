package org.jeesl.interfaces.model.system.locale;

import java.io.Serializable;
import java.util.List;

public interface JeeslLocaleProvider<LOC extends JeeslLocale<?,?,LOC,?>> extends Serializable
{	
	String getPrimaryLocaleCode();
	List<String> getLocaleCodes();
	boolean hasLocale(String localeCode);
}