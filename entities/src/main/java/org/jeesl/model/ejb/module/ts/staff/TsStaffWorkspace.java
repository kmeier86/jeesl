package org.jeesl.model.ejb.module.ts.staff;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.jeesl.interfaces.model.system.security.util.JeeslStaff;
import org.jeesl.model.ejb.module.ts.core.TsWorkspace;
import org.jeesl.model.ejb.system.security.SecurityAction;
import org.jeesl.model.ejb.system.security.SecurityActionTemplate;
import org.jeesl.model.ejb.system.security.SecurityCategory;
import org.jeesl.model.ejb.system.security.SecurityRole;
import org.jeesl.model.ejb.system.security.SecurityUsecase;
import org.jeesl.model.ejb.system.security.SecurityView;
import org.jeesl.model.ejb.system.status.Description;
import org.jeesl.model.ejb.system.status.Lang;
import org.jeesl.model.ejb.user.User;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(name = "StaffWorkspace",uniqueConstraints = @UniqueConstraint(columnNames = {"domain_id","role_id","user_id"}))
@EjbErNode(name="Staff",category="ts",level=2,subset="ts")
public class TsStaffWorkspace implements Serializable,EjbWithId,EjbPersistable,EjbRemoveable,EjbSaveable,
					JeeslStaff<Lang,Description,SecurityCategory,SecurityRole,SecurityView,SecurityUsecase,SecurityAction,SecurityActionTemplate,User,TsWorkspace,TsWorkspace>
{
	public static final long serialVersionUID=1;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id){this.id = id;}
	
	@NotNull @ManyToOne
	private TsWorkspace domain;
	@Override public TsWorkspace getDomain() {return domain;}
	@Override public void setDomain(TsWorkspace domain) {this.domain = domain;}
	
	@Transient
	private TsWorkspace domain2;
	@Override public TsWorkspace getDomain2() {return domain2;}
	@Override public void setDomain2(TsWorkspace domain2) {this.domain2=domain2;}
	
	@NotNull @ManyToOne
	private SecurityRole role;
	@Override public SecurityRole getRole() {return role;}
	@Override public void setRole(SecurityRole role) {this.role = role;}

	@NotNull @ManyToOne
	private User user;
	@Override public User getUser() {return user;}
	@Override  public void setUser(User user) {this.user = user;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(" user:");
		if(user!=null){sb.append(user.toString());}else{sb.append("null");}
		sb.append(" district:");
		if(domain!=null){sb.append(domain.toString());}else{sb.append("null");}
		sb.append(" role");
		if(role!=null){sb.append(role.toString());}else{sb.append("null");}
		return sb.toString();
	}
	
	@Override public boolean equals(Object object) {return (object instanceof TsStaffWorkspace) ? id == ((TsStaffWorkspace) object).getId() : (object == this);}
}