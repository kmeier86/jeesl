package org.jeesl.factory.xml.module.workflow;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowModificationLevel;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.model.xml.module.workflow.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlLevelFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlPermissionFactory<L extends UtilsLang, D extends UtilsDescription,
								WS extends JeeslWorkflowStage<L,D,?,?,WSP,?,?>,
								WSP extends JeeslWorkflowStagePermission<WS,WPT,WML,SR>,
								WPT extends JeeslWorkflowPermissionType<L,D,WPT,?>,
								WML extends JeeslWorkflowModificationLevel<L,D,WML,?>,
								SR extends JeeslSecurityRole<L,D,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlPermissionFactory.class);
	
	private final Permission q;
	
	private XmlTypeFactory<L,D,WPT> xfType;
	private XmlLevelFactory<L,D,WML> xfLevel;
	
//	public XmlPermissionFactory(QueryWf query) {this(query.getLocaleCode(),query.getStage());}
	public XmlPermissionFactory(String localeCode, Permission q)
	{
		this.q=q;
		if(q.isSetType()) {xfType = new XmlTypeFactory<>(localeCode,q.getType());}
		if(q.isSetLevel()) {xfLevel = new XmlLevelFactory<>(localeCode,q.getLevel());}
//		if(q.isSetLangs()) {xfLangs = new XmlLangsFactory<>(q.getLangs());}
//		if(q.isSetDescriptions()) {xfDescription = new XmlDescriptionsFactory<>(q.getDescriptions());}
	}
	
	public static Permission build(){return new Permission();}
	
	public Permission build(WSP permission)
	{
		Permission xml = build();
		if(q.isSetId()) {xml.setId(permission.getId());}
		if(q.isSetPosition()) {xml.setPosition(permission.getPosition());}
		if(q.isSetType()) {xml.setType(xfType.build(permission.getType()));}
		if(q.isSetLevel()) {xml.setLevel(xfLevel.build(permission.getModificationLevel()));}
		
		return xml;
	}
}