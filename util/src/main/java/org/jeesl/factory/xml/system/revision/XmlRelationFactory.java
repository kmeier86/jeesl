package org.jeesl.factory.xml.system.revision;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.model.xml.system.revision.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlRelationFactory <L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									REM extends JeeslRevisionEntityMapping<?,?,?>,
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA>,										
									RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
									RER extends UtilsStatus<RER,L,D>,
									RAT extends UtilsStatus<RAT,L,D>>

{

	final static Logger logger = LoggerFactory.getLogger(XmlRelationFactory.class);
	
	private Relation q;

	private XmlEntityFactory<L,D,RC,REM,RE,RA,RER,RAT> xfEntity;
	private XmlTypeFactory<L,D,RER> xfType;
	
	public XmlRelationFactory(Relation q)
	{
		this.q=q;
		if(q.isSetEntity()){xfEntity = new XmlEntityFactory<>(q.getEntity());}
		if(q.isSetType()){xfType = new XmlTypeFactory<>(q.getType());}
	}
	
	public static Relation build() {return new Relation();}	
	
	public Relation build(RA attribute) 
	{
		Relation xml = new Relation();
		
		if(q.isSetEntity())
		{
			if(attribute.getEntity()==null){xml.setEntity(XmlEntityFactory.build());}
			else {xml.setEntity(xfEntity.build(attribute.getEntity()));}
		}	
		
		if (q.isSetType()){xml.setType(xfType.build(attribute.getRelation()));}
		
		return xml;
	}


}
