package net.sf.ahtutils.controller.factory.ejb.security;

import java.util.UUID;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslRememberMe;
import org.joda.time.DateTime;

public class EjbRememberMeFactory <USER extends JeeslUser<?>, REM extends JeeslRememberMe<USER>>
{		
	private final Class<REM> cRem;
	
	private EjbRememberMeFactory(final Class<REM> cRem)
	{
		this.cRem=cRem;
	}
	
	public static <USER extends JeeslUser<?>, REM extends JeeslRememberMe<USER>>
		EjbRememberMeFactory<USER,REM> factory(final Class<REM> cRem)
	{
		return new EjbRememberMeFactory<USER,REM>(cRem);
	}
	
	public REM create(USER user, int validDays)
	{
		DateTime dt = new DateTime();
		REM ejb = null;
		
		try
		{
			ejb = cRem.newInstance();
			ejb.setUser(user);
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setValidUntil(dt.plusDays(validDays).toDate());
		}
	    	catch (InstantiationException e) {e.printStackTrace();}
	    	catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}