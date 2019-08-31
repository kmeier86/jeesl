package org.jeesl.factory.xml.module.workflow;

import org.jeesl.model.xml.module.workflow.Permission;
import org.jeesl.model.xml.module.workflow.Permissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPermissionsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlPermissionsFactory.class);

	
	public static Permissions build(){return new Permissions();}
	public static Permissions build(Permission permission)
	{
		Permissions xml = build();
		xml.getPermission().add(permission);
		return xml;
	}
}