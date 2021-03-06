package net.sf.ahtutils.controller.factory.ejb.mail;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.system.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbMailTrackerFactory<T extends UtilsMailTracker<S,L,U,D>,S extends JeeslStatus<S,L,D>, L extends JeeslLang, U extends EjbWithId, D extends JeeslDescription>
{
	final static Logger logger = LoggerFactory.getLogger(EjbMailTrackerFactory.class);
	
    final Class<T> clTracker;
	
    public static <T extends UtilsMailTracker<S,L,U,D>,S extends JeeslStatus<S,L,D>, L extends JeeslLang, U extends EjbWithId,D extends JeeslDescription> EjbMailTrackerFactory<T,S,L,U,D> createFactory(final Class<T> clTracker)
    {
        return new EjbMailTrackerFactory<T,S,L,U,D>(clTracker);
    }
    
    public EjbMailTrackerFactory(final Class<T> clTracker)
    {
        this.clTracker = clTracker;
    } 
    
    public T create(S type, U user, long refId) {return create(type, user, refId, new Date());}
    public T create(S type, U user, long refId, Date created){return create(type, user, refId, created,-1);}
    public T create(S type, U user, long refId, Date created, int retryCounter)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clTracker.newInstance();
			ejb.setType(type);
			ejb.setRefId(refId);
			ejb.setRecordCreated(created);
			ejb.setRetryCounter(retryCounter);
			ejb.setUser(user);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}