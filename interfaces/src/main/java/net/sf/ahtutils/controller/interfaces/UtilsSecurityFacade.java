package net.sf.ahtutils.controller.interfaces;

import java.util.List;

import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

public interface UtilsSecurityFacade extends UtilsFacade
{	
	<L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,R extends UtilsSecurityRole<L,D,C,R,V,U,A>,V extends UtilsSecurityView<L,D,C,R,V,U,A>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
		List<V> allViewsForRoles(Class<R> clRole, List<R> roles);
}
