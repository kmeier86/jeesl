package org.jeesl.factory.ejb.module.log;

import org.jeesl.interfaces.model.module.log.JeeslLogBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLogFactory<LOG extends JeeslLogBook<?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLogFactory.class);
	
	private final Class<LOG> cLog;
    
	public EjbLogFactory(final Class<LOG> cLog)
	{  
        this.cLog = cLog;
	}
	    
	public LOG build()
	{
		LOG ejb = null;
		try
		{
			ejb = cLog.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}