package net.sf.ahtutils.interfaces.controller.handler.op.user;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

public interface OpUserSelectionHandler <L extends JeeslLang,
											D extends JeeslDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											USER extends JeeslUser<R>>
{
	public static final long serialVersionUID=1;

    public USER getUser();
    public void setUser(USER user);

    public List<USER> getFvUser();
    public void setFvUser(List<USER> fvUser);

    public List<USER> getUiUsers();
    public void setUiUsers(List<USER> uiUsers);

    public USER getUiUser();
    public void setUiUser(USER uiUser);
    
    public void selectListener() throws JeeslLockingException, JeeslConstraintViolationException;

    public void clearUi();
    public void addUiUser(USER item);
    public void removeUiUser();

    public void selectUiUser();
}