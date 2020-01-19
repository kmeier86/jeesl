package org.jeesl.controller.handler.system.io.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.controller.handler.system.io.JeeslTemplateHandler;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractlIoTemplateHandler<L extends UtilsLang,D extends UtilsDescription,LOC extends JeeslLocale<L,D,LOC,?>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CHANNEL extends JeeslTemplateChannel<L,D,CHANNEL,?>,
									TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
									SCOPE extends UtilsStatus<SCOPE,L,D>,
									DEFINITION extends JeeslIoTemplateDefinition<D,CHANNEL,TEMPLATE>,
									TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE,TOKENTYPE>,
									TOKENTYPE extends UtilsStatus<TOKENTYPE,L,D>>
								implements JeeslTemplateHandler<L,D,LOC,CATEGORY,CHANNEL,TEMPLATE,SCOPE,DEFINITION,TOKEN,TOKENTYPE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractlIoTemplateHandler.class);

	private final List<LOC> locales; @Override public List<LOC> getLocales() {return locales;}
	private final List<DEFINITION> definitons; @Override public List<DEFINITION> getDefinitons() {return definitons;}
	
	private final Map<CHANNEL,Map<String,String>> header; public Map<CHANNEL,Map<String, String>> getHeader() {return header;}
	private final Map<CHANNEL,Map<String,String>> body; public Map<CHANNEL,Map<String, String>> getBody() {return body;}
	
	public AbstractlIoTemplateHandler()
	{
		locales = new ArrayList<>();
		definitons = new ArrayList<>();
		header = new HashMap<>();
		body = new HashMap<>();
	}
	
	public void addLocale(LOC locale){this.locales.add(locale);}
	public void addLocales(List<LOC> locales){this.locales.addAll(locales);}
	
	public void initDefinitions(List<DEFINITION> definitions)
	{
		this.definitons.clear();
		this.definitons.addAll(definitions);
		logger.info("adding "+definitions.size());
		for(DEFINITION d : definitions)
		{
			header.put(d.getType(),new HashMap<>());
			body.put(d.getType(),new HashMap<>());
			for(LOC loc : locales)
			{
				header.get(d.getType()).put(loc.getCode(),d.getHeader().get(loc.getCode()).getLang());
				body.get(d.getType()).put(loc.getCode(),d.getDescription().get(loc.getCode()).getLang());
			}
		}
	}
	
	@Override public String toHeader(DEFINITION definition, LOC locale)
	{
		if(header.containsKey(definition.getType()) && header.get(definition.getType()).containsKey(locale.getCode())) {return header.get(definition.getType()).get(locale.getCode());}
		else {return "";}
	}
	@Override public String toBody(DEFINITION definition, LOC locale)
	{
		if(body.containsKey(definition.getType()) && body.get(definition.getType()).containsKey(locale.getCode())) {return body.get(definition.getType()).get(locale.getCode());}
		else {return "";}
	}
}