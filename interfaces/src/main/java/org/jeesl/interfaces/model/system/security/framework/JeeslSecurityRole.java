package org.jeesl.interfaces.model.system.security.framework;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithActions;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithCategory;
import org.jeesl.interfaces.model.system.security.with.JeeslSecurityWithViews;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityRole<L extends JeeslLang, D extends JeeslDescription, 
						 		   C extends JeeslSecurityCategory<L,D>,
						 		   V extends JeeslSecurityView<L,D,C,?,U,A>,
						 		   U extends JeeslSecurityUsecase<L,D,C,?,V,A>,
						 		   A extends JeeslSecurityAction<L,D,?,V,U,?>,
						 		   USER extends JeeslUser<?>>
			extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
					EjbWithPositionVisible,EjbWithParentAttributeResolver,
					EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>,
					JeeslSecurityWithCategory<C>,
					JeeslSecurityWithViews<V>,
					JeeslSecurityWithActions<A>
					
{
	public static final String extractId = "securityRoles";
	
	public List<U> getUsecases();
	public void setUsecases(List<U> usecases);
	
	List<USER> getUsers();
	void setUsers(List<USER> users);
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}