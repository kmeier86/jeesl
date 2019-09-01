package org.jeesl.factory.xml.system.revision;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlCategoryFactory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.text.XmlRemarkFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Query;

public class XmlEntityFactory <L extends UtilsLang,D extends UtilsDescription,
								RC extends JeeslRevisionCategory<L,D,RC,?>,	
								REM extends JeeslRevisionEntityMapping<?,?,?>,
								RE extends JeeslRevisionEntity<L,D,RC,REM,RA>,	
								RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
								RER extends UtilsStatus<RER,L,D>,
								RAT extends UtilsStatus<RAT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlEntityFactory.class);
	
	private Entity q;
	
	private XmlCategoryFactory<RC,L,D> xfCategory;
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlAttributeFactory<L,D,RC,REM,RE,RA,RER,RAT> xfAttribute;
	
	public XmlEntityFactory(Query q){this(q.getEntity());}
	public XmlEntityFactory(Entity q)
	{
		this.q=q;
		if(q.isSetCategory()){xfCategory = new XmlCategoryFactory<>(q.getCategory());}
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<>(q.getDescriptions());}
		if(q.isSetAttribute()){xfAttribute = new XmlAttributeFactory<>(q.getAttribute().get(0));}
	}
	
	public static Entity build() {return new Entity();}
	
	public Entity build(RE ejb)
	{
		Entity xml = build();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()&&ejb.getCode()!=""){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		if(q.isSetCategory()){xml.setCategory(xfCategory.build(ejb.getCategory()));}		
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(ejb.getDescription()));}
		if(q.isSetRemark()){xml.setRemark(XmlRemarkFactory.build(ejb.getDeveloperInfo()));}
		
		if(q.isSetAttribute())
		{
			for(RA attribute : ejb.getAttributes())
			{
				xml.getAttribute().add(xfAttribute.build(attribute));
			}
		}
		
		return xml;
	}

}