package org.jeesl.interfaces.model.system.security.with;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslSecurityWithActions<L extends UtilsLang,
						 		   D extends UtilsDescription, 
						 		   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
						 		   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
						 		   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
						 		   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
						 		   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
						 		  AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
						 		   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	public List<A> getActions();
	public void setActions(List<A> actions);
}