package org.jeesl.factory.xml.system.io.revision;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.factory.xml.system.util.text.XmlRemarkFactory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.xml.system.revision.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XmlAttributeFactory <L extends JeeslLang,D extends JeeslDescription,
								RC extends JeeslRevisionCategory<L,D,RC,?>,	
								REM extends JeeslRevisionEntityMapping<?,?,?>,
								RE extends JeeslRevisionEntity<L,D,RC,REM,RA,ERD>,										
								RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
								RER extends JeeslStatus<RER,L,D>,
								RAT extends JeeslStatus<RAT,L,D>,
								ERD extends JeeslRevisionDiagram<L,D,RC>>
								
{
	final static Logger logger = LoggerFactory.getLogger(XmlAttributeFactory.class);
	
	private Attribute q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlTypeFactory<L,D,RAT> xfType;
	private XmlRelationFactory<L,D,RC,REM,RE,RA,RER,RAT,ERD> xfRelation;
	
	public XmlAttributeFactory(Attribute q)
	{
		this.q=q;
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<>(q.getDescriptions());}
		if(q.isSetType()){xfType = new XmlTypeFactory<>(q.getType());}
		if(q.isSetRelation()){xfRelation = new XmlRelationFactory<>(q.getRelation());}
	}
	
	public Attribute build(RA ejb)
	{
		Attribute xml = new Attribute();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetType()){xml.setType(xfType.build(ejb.getType()));}
		
		if(q.isSetXpath()){xml.setXpath(ejb.getXpath());}
//		if(q.isSetJpa()){xml.setJpa(ejb.get);
		
		if(q.isSetWeb()){xml.setWeb(ejb.isShowWeb());}
		if(q.isSetPrint()){xml.setPrint(ejb.isShowPrint());}
		if(q.isSetName()){xml.setName(ejb.isShowName());}
		if(q.isSetEnclosure()){xml.setEnclosure(ejb.isShowEnclosure());}
		if(q.isSetUi())
		{
			if(ejb.getUi()==null){xml.setUi(false);}
			else{xml.setUi(ejb.getUi());}
		}
		if(q.isSetBean())
		{
			if(ejb.getBean()==null){xml.setBean(false);}
			else {xml.setBean(ejb.getBean());}
		}
		if(q.isSetConstruction())
		{
			if(ejb.getConstruction()==null){xml.setConstruction(false);}
			else {xml.setConstruction(ejb.getConstruction());}
		}
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(ejb.getDescription()));}
		if(q.isSetRemark()){xml.setRemark(XmlRemarkFactory.build(ejb.getDeveloperInfo()));}
		
		if(q.isSetRelation() && ejb.getRelation()!=null){xml.setRelation(xfRelation.build(ejb));}
		
		return xml;
	}
}