package org.jeesl.doc.ofx.cms.factory;

import org.jeesl.doc.ofx.OfxMultiLangFactory;
import org.jeesl.doc.ofx.OfxMultiLocaleFactory;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractJeeslOfxFactory<L extends UtilsLang, LOC extends JeeslLocale<L,?,LOC,?>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslOfxFactory.class);
	
	protected OfxTranslationProvider tp;
	
	protected final OfxMultiLangFactory<L> ofxMultiLang;
	protected final OfxMultiLocaleFactory<L,LOC> ofxMultiLocale;
	
	public AbstractJeeslOfxFactory(OfxTranslationProvider tp)
	{	
		this.tp=tp;

		ofxMultiLang = new OfxMultiLangFactory<>(tp.getLocaleCodes());
		ofxMultiLocale = new OfxMultiLocaleFactory<>();
	}
}