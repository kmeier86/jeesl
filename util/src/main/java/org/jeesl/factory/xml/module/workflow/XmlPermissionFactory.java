package org.jeesl.factory.xml.module.workflow;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowModificationLevel;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowPermissionType;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStagePermission;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.model.xml.module.workflow.Permission;
import org.jeesl.model.xml.module.workflow.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlPermissionFactory<L extends UtilsLang, D extends UtilsDescription,
								WS extends JeeslWorkflowStage<L,D,?,?,WSP,?,?>,
								WSP extends JeeslWorkflowStagePermission<WS,WPT,WML,SR>,
								WPT extends JeeslWorkflowPermissionType<L,D,WPT,?>,
								WML extends JeeslWorkflowModificationLevel<?,?,WML,?>,
								SR extends JeeslSecurityRole<L,D,?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlPermissionFactory.class);
	
	private final Permission q;
	
//	private XmlTypeFactory<L,D,WST> xfType;
	
//	public XmlPermissionFactory(QueryWf query) {this(query.getLocaleCode(),query.getStage());}
	public XmlPermissionFactory(String localeCode, Permission q)
	{
		this.q=q;
//		if(q.isSetType()) {xfType = new XmlTypeFactory<>(localeCode,q.getType());}
//		if(q.isSetLangs()) {xfLangs = new XmlLangsFactory<>(q.getLangs());}
//		if(q.isSetDescriptions()) {xfDescription = new XmlDescriptionsFactory<>(q.getDescriptions());}
	}
	
	public static Permission build(){return new Permission();}
	
	public Permission build(WSP permission)
	{
		Permission xml = build();
		if(q.isSetId()) {xml.setId(permission.getId());}
		if(q.isSetPosition()) {xml.setPosition(permission.getPosition());}
		
		
		return xml;
	}
}