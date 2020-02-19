package org.jeesl.interfaces.controller.handler;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;

public interface JeeslTranslationProvider <LOC extends JeeslLocale<?,?,LOC,?>> extends OfxTranslationProvider
{
	void setLanguages(List<LOC> locales);
}