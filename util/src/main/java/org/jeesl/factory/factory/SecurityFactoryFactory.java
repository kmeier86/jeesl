package org.jeesl.factory.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class SecurityFactoryFactory<L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityFactoryFactory.class);
	
    
	public SecurityFactoryFactory()
	{

	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
					R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
					V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
					U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
					A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
					AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
					USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		SecurityFactoryFactory<L,D,C,R,V,U,A,AT,USER>
		factory()
	{
		return new SecurityFactoryFactory<L,D,C,R,V,U,A,AT,USER>();
	}
}