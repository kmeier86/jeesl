package net.sf.ahtutils.controller.factory.ejb.security;

import java.util.UUID;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.util.JeeslRememberMe;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.joda.time.DateTime;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbRememberMeFactory <L extends UtilsLang, D extends UtilsDescription,
									C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
									REM extends JeeslRememberMe<L,D,C,R,V,U,A,AT,USER>>
{		
	private final Class<REM> cRem;
	
	private EjbRememberMeFactory(final Class<REM> cRem)
	{
		this.cRem=cRem;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
				   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
				   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
				   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
				   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
				   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
				   AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
				   USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>,
				   REM extends JeeslRememberMe<L,D,C,R,V,U,A,AT,USER>>
		EjbRememberMeFactory<L,D,C,R,V,U,A,AT,USER,REM> factory(final Class<REM> cRem)
	{
		return new EjbRememberMeFactory<L,D,C,R,V,U,A,AT,USER,REM>(cRem);
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