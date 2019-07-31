package org.jeesl.factory.xml.module.workflow;

import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.model.xml.module.workflow.Processes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlProcessesFactory<L extends UtilsLang, D extends UtilsDescription,
								WX extends JeeslWorkflowContext<L,D,WX,?>,
								WP extends JeeslWorkflowProcess<L,D,WX,WS>,
								WS extends JeeslWorkflowStage<L,D,WP,WST,?>,
								WST extends JeeslWorkflowStageType<WST,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlProcessesFactory.class);
	
	
	@SuppressWarnings("unused") private final String localeCode;
	@SuppressWarnings("unused") private final Processes q;
	
	public XmlProcessesFactory(String localeCode, Processes q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public static Processes build(){return new Processes();}
}