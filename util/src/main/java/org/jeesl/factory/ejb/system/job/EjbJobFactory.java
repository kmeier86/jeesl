package org.jeesl.factory.ejb.system.job;

import java.util.Date;

import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobCategory;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedbackType;
import org.jeesl.interfaces.model.system.job.JeeslJobPriority;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.interfaces.model.system.job.JeeslJobType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbJobFactory <L extends JeeslLang,D extends JeeslDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY,?>,
									CATEGORY extends JeeslJobCategory<L,D,CATEGORY,?>,
									TYPE extends JeeslJobType<L,D,TYPE,?>,
									JOB extends JeeslJob<TEMPLATE,PRIORITY,FEEDBACK,STATUS,USER>,
									PRIORITY extends JeeslJobPriority<L,D,PRIORITY,?>,
									FEEDBACK extends JeeslJobFeedback<JOB,FT,USER>,
									FT extends JeeslJobFeedbackType<L,D,FT,?>,
									STATUS extends JeeslStatus<STATUS,L,D>,
									ROBOT extends JeeslJobRobot<L,D>,
									CACHE extends JeeslJobCache<TEMPLATE,?>,
									USER extends EjbWithEmail
									>
{
	final static Logger logger = LoggerFactory.getLogger(EjbJobFactory.class);
	
	private final Class<JOB> cJob;

	public EjbJobFactory(final Class<JOB> cJob)
	{
        this.cJob = cJob;
	}
 
	public JOB build(USER user, TEMPLATE template, STATUS status, String code, String name, String jsonFilter)
	{
		JOB ejb = null;
		try
		{
			ejb = cJob.newInstance();
			ejb.setUser(user);
			ejb.setTemplate(template);
			ejb.setStatus(status);
			if(template!=null) {ejb.setPriority(template.getPriority());}
			ejb.setRecordCreation(new Date());
			ejb.setCode(code);
			ejb.setName(name);
			ejb.setJsonFilter(jsonFilter);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}