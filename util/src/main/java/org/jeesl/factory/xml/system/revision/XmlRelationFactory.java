package org.jeesl.factory.xml.system.revision;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.model.xml.system.revision.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlRelationFactory <L extends UtilsLang,D extends UtilsDescription,
									RA extends JeeslRevisionAttribute<L,D,?,?,RAT>,
									RER extends UtilsStatus<RER,L,D>,
									RAT extends UtilsStatus<RAT,L,D>>
{

	final static Logger logger = LoggerFactory.getLogger(XmlRelationFactory.class);
	
	private Relation q;

	private XmlAttributeFactory<L,D,RA,RER,RAT> xfAttribute;
	private XmlDescriptionsFactory<D> xfDescriptions;

	
	public static Relation build(){return new Relation();}

	public XmlRelationFactory(Relation q)
	{
		this.q=q;
		if(q.isSetAttribute()){xfAttribute = new XmlAttributeFactory<>(q.getAttribute());}
	}
	public Relation build(Relation q) 
	{
		Relation xml = build();
		xml.setType(q.getType());
		xml.setAttribute(q.getAttribute());
		return xml;		
	}	
}