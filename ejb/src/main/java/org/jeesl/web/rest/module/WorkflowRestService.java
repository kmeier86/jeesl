package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslWorkflowFacade;
import org.jeesl.api.rest.module.workflow.JeeslWorkflowRestExport;
import org.jeesl.factory.builder.module.WorkflowFactoryBuilder;
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
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslTemplateChannel;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class WorkflowRestService <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
									WX extends JeeslWorkflowContext<L,D,WX,G>,
									WP extends JeeslWorkflowProcess<L,D,WX>,
									AS extends JeeslWorkflowStage<L,D,WP,AST,G>,
									AST extends JeeslWorkflowStageType<AST,?,?,?>,
									ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
									APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
									WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
									AT extends JeeslWorkflowTransition<L,D,AS,ATT,SR,G>,
									ATT extends JeeslWorkflowTransitionType<ATT,L,D,?>,
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
									AW extends JeeslApprovalWorkflow<WP,AS,WY>,
									WY extends JeeslApprovalActivity<AT,AW,FRC,USER>,
									FRC extends JeeslFileContainer<?,?>,
									G extends JeeslGraphic<L,D,GT,?,?>, GT extends UtilsStatus<GT,L,D>,
									USER extends JeeslUser<SR>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslWorkflowRestExport
{
	final static Logger logger = LoggerFactory.getLogger(WorkflowRestService.class);
	
	private final JeeslWorkflowFacade<L,D,LOC,WX,WP,AS,AST,ASP,APT,WML,AT,ATT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow;
	private final WorkflowFactoryBuilder<L,D,WX,WP,AS,AST,ASP,APT,WML,AT,ATT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow;
	
	private WorkflowRestService(WorkflowFactoryBuilder<L,D,WX,WP,AS,AST,ASP,APT,WML,AT,ATT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow,
			JeeslWorkflowFacade<L,D,LOC,WX,WP,AS,AST,ASP,APT,WML,AT,ATT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow)
	{
		super(fWorkflow,fbWorkflow.getClassL(),fbWorkflow.getClassD());
		this.fWorkflow=fWorkflow;
		this.fbWorkflow=fbWorkflow;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
						WX extends JeeslWorkflowContext<L,D,WX,G>,
						WP extends JeeslWorkflowProcess<L,D,WX>,
						AS extends JeeslWorkflowStage<L,D,WP,AST,G>,
						AST extends JeeslWorkflowStageType<AST,?,?,?>,
						ASP extends JeeslWorkflowStagePermission<AS,APT,WML,SR>,
						APT extends JeeslWorkflowPermissionType<APT,L,D,?>,
						WML extends JeeslWorkflowModificationLevel<WML,?,?,?>,
						AT extends JeeslWorkflowTransition<L,D,AS,ATT,SR,G>,
						ATT extends JeeslWorkflowTransitionType<ATT,L,D,?>,
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
						AW extends JeeslApprovalWorkflow<WP,AS,WY>,
						WY extends JeeslApprovalActivity<AT,AW,FRC,USER>,
						FRC extends JeeslFileContainer<?,?>,
						G extends JeeslGraphic<L,D,GT,?,?>, GT extends UtilsStatus<GT,L,D>,
						USER extends JeeslUser<SR>>
			WorkflowRestService<L,D,LOC,WX,WP,AS,AST,ASP,APT,WML,AT,ATT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,G,GT,USER>
			factory(WorkflowFactoryBuilder<L,D,WX,WP,AS,AST,ASP,APT,WML,AT,ATT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fbWorkflow,
					JeeslWorkflowFacade<L,D,LOC,WX,WP,AS,AST,ASP,APT,WML,AT,ATT,AC,WA,AB,AO,MT,MC,SR,RE,RA,AL,AW,WY,FRC,USER> fWorkflow)
	{
		return new WorkflowRestService<>(fbWorkflow,fWorkflow);
	}

	@Override public Container exportWorkflowContext() {return xfContainer.build(fWorkflow.allOrderedPosition(fbWorkflow.getClassContext()));}

}