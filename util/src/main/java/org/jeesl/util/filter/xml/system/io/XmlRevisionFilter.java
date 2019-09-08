package org.jeesl.util.filter.xml.system.io;

import java.util.Iterator;
import java.util.List;

import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.model.xml.system.revision.Entity;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRevisionFilter
{
	final static Logger logger = LoggerFactory.getLogger(XmlRevisionFilter.class);
	
	public static Entities filterDocumentation(Entities entities){filterDocumentation(entities.getEntity());return entities;}
	public static void filterDocumentation(List<Entity> entities)
	{	
		Iterator<Entity> i = entities.iterator();
		while (i.hasNext())
		{
			Entity entity = i.next();
	        if(BooleanComparator.inactive(entity.isDocumentation()))
	        {
	        	i.remove();
	        }
	    }
	}
}