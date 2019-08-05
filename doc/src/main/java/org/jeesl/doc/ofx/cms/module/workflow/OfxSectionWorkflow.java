package org.jeesl.doc.ofx.cms.module.workflow;

import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslLocaleProvider;
import org.jeesl.model.xml.module.workflow.Workflow;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxSectionWorkflow <LOC extends JeeslLocale<?,?,LOC,?>>
{
	final static Logger logger = LoggerFactory.getLogger(OfxSectionWorkflow.class);
	
	public OfxSectionWorkflow(OfxTranslationProvider tp)
	{
	
	}

	public Section build(JeeslLocaleProvider<LOC> lp, Workflow workflow)
	{
		
		Section xml = XmlSectionFactory.build();
		
		
		return xml;
	}
}