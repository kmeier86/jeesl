package org.jeesl.util.query.xml;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entity;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.xml.aht.Query;

public class RevisionQuery
{
	public static enum Key {exEntities}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key){return get(key,null);}
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case exEntities: q.setEntity(exEntity());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Entity exEntity()
	{		
		Entity xml = new Entity();
//		xml.setId(0);
		xml.setCode("");
		
		xml.getAttribute().add(exAttribute());
		
		return xml;
	}
	
	public static Attribute exAttribute()
	{		
		Attribute xml = new Attribute();
		xml.setNr(0);
		xml.setCode("");
		xml.setXpath("");
		xml.setJpa("");
		
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
}