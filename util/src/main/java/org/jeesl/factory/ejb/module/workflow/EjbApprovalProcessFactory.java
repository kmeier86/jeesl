package org.jeesl.factory.ejb.module.workflow;

import org.jeesl.interfaces.model.module.workflow.process.JeeslWorkflowProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbApprovalProcessFactory<PROCESS extends JeeslWorkflowProcess<?,?,?>
>
{
	final static Logger logger = LoggerFactory.getLogger(EjbApprovalProcessFactory.class);
	
	final Class<PROCESS> cProcess;
    
	public EjbApprovalProcessFactory(final Class<PROCESS> cProcess)
	{       
        this.cProcess = cProcess;
	}
	    
	public PROCESS build()
	{
		PROCESS ejb = null;
		try
		{
			ejb = cProcess.newInstance();
			ejb.setPosition(1);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}