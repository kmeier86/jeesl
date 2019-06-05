package org.jeesl.factory.ejb.module.workflow;

import java.util.Date;

import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalActivity;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslApprovalWorkflow;
import org.jeesl.interfaces.model.module.workflow.transition.JeeslWorkflowTransition;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbWorkflowActivityFactory<AT extends JeeslWorkflowTransition<?,?,?,?>,
										AW extends JeeslApprovalWorkflow<?,?,AY>,
										AY extends JeeslApprovalActivity<AT,AW,USER>,
										USER extends JeeslUser<?>

>
{
	final static Logger logger = LoggerFactory.getLogger(EjbWorkflowActivityFactory.class);
	
	final Class<AY> cActivity;
    
	public EjbWorkflowActivityFactory(final Class<AY> cActivity)
	{       
        this.cActivity = cActivity;
	}
	    
	public AY build(AW workflow, AT transition, USER user)
	{
		AY ejb = null;
		try
		{
			ejb = cActivity.newInstance();
			ejb.setWorkflow(workflow);
			ejb.setTransition(transition);
			ejb.setRecord(new Date());
			ejb.setUser(user);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}