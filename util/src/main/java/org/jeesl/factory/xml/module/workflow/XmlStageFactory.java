package org.jeesl.factory.xml.module.workflow;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStage;
import org.jeesl.interfaces.model.module.workflow.stage.JeeslWorkflowStageType;
import org.jeesl.model.xml.jeesl.QueryWf;
import org.jeesl.model.xml.module.workflow.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class XmlStageFactory<L extends UtilsLang, D extends UtilsDescription,
								WS extends JeeslWorkflowStage<L,D,?,WST,?>,
								WST extends JeeslWorkflowStageType<L,D,WST,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlStageFactory.class);
	
	private final Stage q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescription;
	
	public XmlStageFactory(QueryWf query) {this(query.getLocaleCode(),query.getStage());}
	public XmlStageFactory(String localeCode, Stage q)
	{
		this.q=q;
		if(q.isSetLangs()) {xfLangs = new XmlLangsFactory<>(q.getLangs());}
		if(q.isSetDescriptions()) {xfDescription = new XmlDescriptionsFactory<>(q.getDescriptions());}
	}
	
	public static Stage build(){return new Stage();}
	
	public Stage build(WS stage)
	{
		Stage xml = build();
		if(q.isSetId()) {xml.setId(stage.getId());}
		if(q.isSetLangs()) {xml.setLangs(xfLangs.getUtilsLangs(stage.getName()));}
		if(q.isSetDescriptions()) {xml.setDescriptions(xfDescription.create(stage.getDescription()));}
		return xml;
	}
}