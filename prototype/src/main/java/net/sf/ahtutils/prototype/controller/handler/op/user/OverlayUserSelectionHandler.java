package net.sf.ahtutils.prototype.controller.handler.op.user;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.bean.op.OpUserBean;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.interfaces.controller.handler.op.user.OpUserSelectionHandler;

public class OverlayUserSelectionHandler <L extends JeeslLang, D extends JeeslDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											USER extends JeeslUser<R>>
	implements OpUserSelectionHandler<L,D,C,R,V,U,A,AT,USER>
{
	public static final long serialVersionUID=1;

    private OpUserBean<L,D,C,R,V,U,A,AT,USER> bean;

    private USER user;
    public USER getUser() {return user;}
    public void setUser(USER user) {this.user = user;}

    private List<USER> fvUser;
    public List<USER> getFvUser() {return fvUser;}
    public void setFvUser(List<USER> fvUser) {this.fvUser = fvUser;}

    public OverlayUserSelectionHandler(OpUserBean<L,D,C,R,V,U,A,AT,USER> bean)
    {
        this.bean=bean;
        uiUsers = new ArrayList<USER>();
    }

    public void selectListener() throws JeeslLockingException, JeeslConstraintViolationException
    {
        bean.selectOpUser(user);
        user = null;
    }

    //UI
    private List<USER> uiUsers;
    public List<USER> getUiUsers() {return uiUsers;}
    public void setUiUsers(List<USER> uiUsers) {this.uiUsers = uiUsers;}

    private USER uiUser;
    public USER getUiUser(){return uiUser;}
    public void setUiUser(USER uiUser){this.uiUser = uiUser;}

    public void clearUi()
    {
        uiUsers.clear();
        uiUser = null;
    }

    public void addUiUser(USER item)
    {
        if(!uiUsers.contains(item)) {uiUsers.add(item);}
        uiUser=null;
    }

    public void removeUiUser()
    {
        if(uiUsers.contains(uiUser)) {uiUsers.remove(uiUser);}
        uiUser=null;
    }

    public void selectUiUser()
    {

    }
}