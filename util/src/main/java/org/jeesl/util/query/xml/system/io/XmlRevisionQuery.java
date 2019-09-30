package org.jeesl.util.query.xml.system.io;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.system.io.revision.XmlRelationFactory;
import org.jeesl.factory.xml.system.status.XmlCategoryFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.factory.xml.system.util.text.XmlRemarkFactory;
import org.jeesl.model.xml.jeesl.QueryRevision;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Diagram;
import org.jeesl.model.xml.system.revision.Entity;
import org.jeesl.model.xml.system.revision.Relation;
import org.jeesl.util.query.xml.XmlStatusQuery;

public class XmlRevisionQuery
{
	public static enum Key {xEntity,xDiagram}
	
	private static Map<Key,QueryRevision> mQueries;
	
	public static QueryRevision get(Key key){return get(key,null);}
	public static QueryRevision get(Key key,String localeCode)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,QueryRevision>();}
		if(!mQueries.containsKey(key))
		{
			QueryRevision q = new QueryRevision();
			switch(key)
			{
				case xEntity: q.setEntity(xEntity());break;
				case xDiagram: q.setDiagram(xDiagram());break;
			}
			mQueries.put(key, q);
		}
		QueryRevision q = mQueries.get(key);
		q.setLocaleCode(localeCode);
		return q;
	}
	
	private static Entity xEntity()
	{	
		Entity xml = new Entity();
		xml.setId(0);
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setTimeseries(true);
		xml.setDocumentation(true);
		xml.setCategory(XmlCategoryFactory.create(""));
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.setRemark(XmlRemarkFactory.build(""));
		xml.getAttribute().add(exAttribute());
		
		Diagram diagram = new Diagram();
		diagram.setId(0);
		diagram.setCode("");
		xml.setDiagram(diagram);
		
		return xml;
	}
	
	public static Attribute exAttribute()
	{		
		Attribute xml = new Attribute();
		xml.setCode("");
		xml.setPosition(0);
		xml.setXpath("");
		
		xml.setWeb(true);
		xml.setPrint(true);
		xml.setName(true);
		xml.setEnclosure(true);
		xml.setUi(true);
		xml.setBean(true);
		xml.setConstruction(true);
		
		xml.setType(XmlTypeFactory.create(""));
		
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.setRemark(XmlRemarkFactory.build(""));
		
		Relation relation = XmlRelationFactory.build();
		relation.setOwner(true);		
		relation.setEntity(exRelEntity());		
		relation.setType(XmlTypeFactory.create(""));
		xml.setRelation(relation);

		return xml;
	}
	
	public static Entity exRelEntity()
	{		
		Entity xml = new Entity();
		
		xml.setId(0);
		xml.setCode("");
	
		return xml;
	}
	
	public static Diagram xDiagram()
	{		
		Diagram xml = new Diagram();
		xml.setId(0);
		xml.setCode("");
		xml.setPosition(0);
		xml.setDocumentation(true);
		xml.setCategory(XmlCategoryFactory.create(""));
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		return xml;
	}
}