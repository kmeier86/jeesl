package org.jeesl.factory.xml.system.security;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.xml.jeesl.QuerySecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Staff;
import net.sf.ahtutils.xml.security.Staffs;

public class XmlStaffsFactory<L extends JeeslLang, D extends JeeslDescription, 
								C extends JeeslSecurityCategory<L,D>,
								R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
								V extends JeeslSecurityView<L,D,C,R,U,A>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
								A extends JeeslSecurityAction<L,D,R,V,U,AT>,
								AT extends JeeslSecurityTemplate<L,D,C>,
								USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlStaffsFactory.class);
	
	private final Staffs q;
	
	private XmlRoleFactory<L,D,C,R,V,U,A,AT,USER> xfRole;
	
	public XmlStaffsFactory(QuerySecurity query)
	{
		this(query.getLocaleCode(),query.getStaffs());
	}
	public XmlStaffsFactory(String localeCode, Staffs q)
	{
		this.q=q;
		if(q.isSetRole()) {xfRole = new XmlRoleFactory<L,D,C,R,V,U,A,AT,USER>(localeCode,q.getRole());}
	}
	
	public Staffs build(R role)
	{
		Staffs xml = new Staffs();
		if(q.isSetRole() && role!=null) {xml.setRole(xfRole.build(role));}
		return xml;
	}
	
	public static Staffs build() {return new Staffs();}
	
	public static Staffs build(Staff staff)
	{
		Staffs xml = build();
		xml.getStaff().add(staff);
		return xml;
	}
}