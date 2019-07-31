package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.api.rest.module.workflow.JeeslWorkflowRestExportInterface;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
import org.jeesl.factory.xml.module.workflow.XmlProcessFactory;
import org.jeesl.factory.xml.module.workflow.XmlProcessesFactory;
import org.jeesl.factory.xml.module.workflow.XmlWorkflowFactory;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowAction;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowBot;
import org.jeesl.interfaces.model.module.workflow.action.JeeslWorkflowCommunication;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalLink;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowContext;
import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowModificationLevel;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransitionType;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.module.workflow.Processes;
import org.jeesl.model.xml.module.workflow.Workflow;
import org.jeesl.util.query.xml.module.XmlWorkflowQuery;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class WorkflowRestService <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									WX extends JeeslWorkflowContext<L,D,WX,?>,
									WP extends JeeslWorkflowProcess<L,D,WX,WS>,
									WS extends JeeslWorkflowStage<L,D,WP,WST,?>,
									WST extends JeeslWorkflowStageType<WST,?,?,?>,
									ASP extends JeeslWorkflowStagePermission<WS,APT,WML,SR>,
									APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
									WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
									AT extends JeeslWorkflowTransition<L,D,WS,WTT,SR,?>,
									WTT extends JeeslWorkflowTransitionType<L,D,WTT,?>,
									AC extends JeeslWorkflowCommunication<AT,MT,MC,SR,RE>,
									WA extends JeeslWorkflowAction<AT,AB,AO,RE,RA>,
									AB extends JeeslWorkflowBot<AB,L,D,?>,
									AO extends EjbWithId,
									MT extends JeeslIoTemplate<L,D,?,?,?,?>,
									MC extends JeeslTemplateChannel<L,D,MC,?>,
									SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
									RE extends JeeslRevisionEntity<L,D,?,?,RA>,
									RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
									AL extends JeeslApprovalLink<AW,RE>,
									AW extends JeeslApprovalWorkflow<WP,WS,WY>,
									WY extends JeeslApprovalActivity<AT,AW,FRC,USER>,
									FRC extends JeeslFileContainer<?,?>,
									USER extends JeeslUser<SR>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslWorkflowRestExportInterface
{
	final static Logger logger = LoggerFactory.getLogger(WorkflowRestService.class);
	
	private final JeeslWorkflowFacade<L,D,LOC,WX,WP,WS,WST,ASP,APT,WML,AT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow;
	private final WorkflowFactoryBuilder<L,D,WX,WP,WS,WST,ASP,APT,WML,AT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow;
	
	private final XmlProcessFactory<L,D,WX,WP,WS,WST> xfProcess;
	
	private WorkflowRestService(WorkflowFactoryBuilder<L,D,WX,WP,WS,WST,ASP,APT,WML,AT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow,
			JeeslWorkflowFacade<L,D,LOC,WX,WP,WS,WST,ASP,APT,WML,AT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow)
	{
		super(fWorkflow,fbWorkflow.getClassL(),fbWorkflow.getClassD());
		this.fWorkflow=fWorkflow;
		this.fbWorkflow=fbWorkflow;
		xfProcess = new XmlProcessFactory<>(XmlWorkflowQuery.get(XmlWorkflowQuery.Key.xProcess)); xfProcess.lazy(fbWorkflow, fWorkflow);
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
						WX extends JeeslWorkflowContext<L,D,WX,?>,
						WP extends JeeslWorkflowProcess<L,D,WX,WS>,
						WS extends JeeslWorkflowStage<L,D,WP,AST,?>,
						AST extends JeeslWorkflowStageType<AST,?,?,?>,
						ASP extends JeeslWorkflowStagePermission<WS,APT,WML,SR>,
						APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
						WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
						AT extends JeeslWorkflowTransition<L,D,WS,WTT,SR,?>,
						WTT extends JeeslWorkflowTransitionType<L,D,WTT,?>,
						AC extends JeeslWorkflowCommunication<AT,MT,MC,SR,RE>,
						WA extends JeeslWorkflowAction<AT,AB,AO,RE,RA>,
						AB extends JeeslWorkflowBot<AB,L,D,?>,
						AO extends EjbWithId,
						MT extends JeeslIoTemplate<L,D,?,?,?,?>,
						MC extends JeeslTemplateChannel<L,D,MC,?>,
						SR extends JeeslSecurityRole<L,D,?,?,?,?,?>,
						RE extends JeeslRevisionEntity<L,D,?,?,RA>,
						RA extends JeeslRevisionAttribute<L,D,RE,?,?>,
						AL extends JeeslApprovalLink<AW,RE>,
						AW extends JeeslApprovalWorkflow<WP,WS,WY>,
						WY extends JeeslApprovalActivity<AT,AW,FRC,USER>,
						FRC extends JeeslFileContainer<?,?>,
						USER extends JeeslUser<SR>>
			WorkflowRestService<L,D,LOC,WX,WP,WS,AST,ASP,APT,WML,AT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER>
			factory(WorkflowFactoryBuilder<L,D,WX,WP,WS,AST,ASP,APT,WML,AT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow,
					JeeslWorkflowFacade<L,D,LOC,WX,WP,WS,AST,ASP,APT,WML,AT,WTT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow)
	{
		return new WorkflowRestService<>(fbWorkflow,fWorkflow);
	}

	@Override public Container exportWorkflowContext() {return xfContainer.build(fWorkflow.allOrderedPosition(fbWorkflow.getClassContext()));}

	@Override
	public Workflow exportWorkflowProcesses()
	{
		Processes xml = XmlProcessesFactory.build();
		
		for(WP process : fWorkflow.all(fbWorkflow.getClassProcess()))
		{
			xml.getProcess().add(xfProcess.build(process));
		}
		
		return XmlWorkflowFactory.build(xml);
	}
}