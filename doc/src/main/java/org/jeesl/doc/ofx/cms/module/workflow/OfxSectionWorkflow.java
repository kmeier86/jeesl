package org.jeesl.doc.ofx.cms.module.workflow;

import java.io.FileNotFoundException;

import org.jeesl.doc.ofx.cms.generic.AbstractJeeslOfxFactory;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslLocaleProvider;
import org.jeesl.model.xml.module.workflow.Processes;
import org.jeesl.model.xml.module.workflow.Workflow;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.list.XmlListFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.status.Context;
import net.sf.ahtutils.xml.status.Contexts;
import net.sf.exlp.util.xml.JaxbUtil;

public class OfxSectionWorkflow <L extends UtilsLang, LOC extends JeeslLocale<L,?,LOC,?>,
								E extends JeeslIoCmsElement<?,?,?,?,?,?>>
							extends AbstractJeeslOfxFactory<L,LOC>
{
	final static Logger logger = LoggerFactory.getLogger(OfxSectionWorkflow.class);
	
	private final OfxTableWorkflowProcess<L,LOC> ofxProcess;
	
	public OfxSectionWorkflow(OfxTranslationProvider tp)
	{
		super(tp);
		ofxProcess = new OfxTableWorkflowProcess<>(tp);
	}
	
	public Section build(JeeslLocaleProvider<LOC> lp, E element) throws OfxAuthoringException
	{
		try
		{
			Workflow workflow = JaxbUtil.loadJAXB("/Volumes/ramdisk/workflow.xml",Workflow.class);
			return build(lp,workflow);
		}
		catch (FileNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
	}

	public Section build(JeeslLocaleProvider<LOC> lp, Workflow workflow)
	{
		
		Section xml = XmlSectionFactory.build();
		xml.setContainer(true);
		xml.getContent().add(contextList(lp,workflow.getContexts()));
		
		for(Context context : workflow.getContexts().getContext())
		{
			xml.getContent().add(build(lp,context,workflow.getProcesses()));
		}
		
		return xml;
	}
	
	public org.openfuxml.content.list.List contextList(JeeslLocaleProvider<LOC> lp, Contexts contexts)
	{

		org.openfuxml.content.list.List list = XmlListFactory.description();
		
		logger.info("Building for"+contexts.getContext().size());
		for(Context context : contexts.getContext())
		{
			list.getItem().addAll(ofxMultiLocale.listDescription(lp, context.getLangs(), context.getDescriptions()));
		}
		
		return list;
	}
	
	private Section build(JeeslLocaleProvider<LOC> lp, Context context, Processes processes)
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().addAll(ofxMultiLocale.title(lp, context.getLangs()));
		xml.getContent().addAll(ofxMultiLocale.paragraphs(lp,context.getDescriptions(),false));
		for(org.jeesl.model.xml.module.workflow.Process process : processes.getProcess())
		{
			if(process.getContext().getCode().equals(context.getCode()))
			{
				Section sub = XmlSectionFactory.build();
				sub.getContent().addAll(ofxMultiLocale.title(lp, process.getLangs()));
				xml.getContent().addAll(ofxMultiLocale.paragraphs(lp,process.getDescriptions(),false));
				sub.getContent().add(ofxProcess.build(lp,process));
				xml.getContent().add(sub);
			}
		}
		
		return xml;
	}
}