package org.jeesl.interfaces.bean.op;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.controller.handler.op.user.OpUserSelectionHandler;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface OpUserBean <L extends UtilsLang,
							D extends UtilsDescription,
							C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
							R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
							V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
							U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
							A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
							AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	OpUserSelectionHandler<L,D,C,R,V,U,A,AT,USER> getOpUserHandler();
	void selectOpUser(USER user) throws UtilsLockingException, UtilsConstraintViolationException;
}