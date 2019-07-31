package org.jeesl.factory.xml.module.workflow;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlContextFactory;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.model.xml.jeesl.QueryWf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlProcessFactory<L extends UtilsLang, D extends UtilsDescription,
								WX extends JeeslWorkflowContext<L,D,WX,?>,
								WP extends JeeslWorkflowProcess<L,D,WX,WS>,
								WS extends JeeslWorkflowStage<L,D,WP,WST,?>,
								WST extends JeeslWorkflowStageType<WST,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlProcessFactory.class);
	
	private final org.jeesl.model.xml.module.workflow.Process q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescription;
	private XmlContextFactory<L,D,WX> xfContext;
	private XmlStageFactory<L,D,WS,WST> xfStage;
	
	private WorkflowFactoryBuilder<L,D,WX,WP,WS,WST,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fbWorkflow;
	private JeeslWorkflowFacade<L,D,?,WX,WP,WS,WST,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fWorkflow;
	
	public XmlProcessFactory(QueryWf query) {this(query.getLocaleCode(),query.getProcess());}
	public XmlProcessFactory(String localeCode, org.jeesl.model.xml.module.workflow.Process q)
	{
		this.q=q;
		if(q.isSetLangs()) {xfLangs = new XmlLangsFactory<>(q.getLangs());}
		if(q.isSetDescriptions()) {xfDescription = new XmlDescriptionsFactory<>(q.getDescriptions());}
		if(q.isSetContext()) {xfContext = new XmlContextFactory<>(localeCode,q.getContext());}
		if(q.isSetStage()) {xfStage = new XmlStageFactory<L,D,WS,WST>(localeCode,q.getStage().get(0));}
	}
	
	public void lazy(WorkflowFactoryBuilder<L,D,WX,WP,WS,WST,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fbWorkflow, JeeslWorkflowFacade<L,D,?,WX,WP,WS,WST,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fWorkflow)
	{
		this.fbWorkflow=fbWorkflow;
		this.fWorkflow=fWorkflow;
	}
	
	public static org.jeesl.model.xml.module.workflow.Process build(){return new org.jeesl.model.xml.module.workflow.Process();}
	
	public org.jeesl.model.xml.module.workflow.Process build(WP process)
	{
		org.jeesl.model.xml.module.workflow.Process xml = build();
		if(q.isSetId()) {xml.setId(process.getId());}
		if(q.isSetLangs()) {xml.setLangs(xfLangs.getUtilsLangs(process.getName()));}
		if(q.isSetDescriptions()) {xml.setDescriptions(xfDescription.create(process.getDescription()));}
		if(q.isSetContext()) {xml.setContext(xfContext.build(process.getContext()));}
		
		if(q.isSetStage())
		{
			List<WS> stages = new ArrayList<WS>();
			if(fbWorkflow!=null && fWorkflow!=null) {stages.addAll(fWorkflow.allForParent(fbWorkflow.getClassStage(), process));}
			else {stages.addAll(process.getStages());}
			
			for(WS stage : stages)
			{
				xml.getStage().add(xfStage.build(stage));
			}
		}
		
		return xml;
	}
}