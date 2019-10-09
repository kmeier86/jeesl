package org.jeesl.factory.xml.system.io.revision;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.model.xml.system.revision.Relation;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlRelationFactory <L extends UtilsLang,D extends UtilsDescription,
									RC extends JeeslRevisionCategory<L,D,RC,?>,	
									REM extends JeeslRevisionEntityMapping<?,?,?>,
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA,ERD>,										
									RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
									RER extends UtilsStatus<RER,L,D>,
									RAT extends UtilsStatus<RAT,L,D>,
									ERD extends JeeslRevisionDiagram<L,D,RC>>

{

	final static Logger logger = LoggerFactory.getLogger(XmlRelationFactory.class);
	
	private Relation q;

	private XmlEntityFactory<L,D,RC,REM,RE,RA,RER,RAT,ERD> xfEntity;
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
		if(q.isSetDocOptionsTable()) {xml.setDocOptionsTable(BooleanComparator.active(attribute.getStatusTableDoc()));}
		
		if(q.isSetEntity())
		{
			if(attribute.getEntity()==null){xml.setEntity(XmlEntityFactory.build());}
			else {xml.setEntity(xfEntity.build(attribute.getEntity()));}
		}	
		
		if(q.isSetType()){xml.setType(xfType.build(attribute.getRelation()));}
		
		
		
		return xml;
	}


}
