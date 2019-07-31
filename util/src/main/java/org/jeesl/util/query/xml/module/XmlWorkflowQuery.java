package org.jeesl.util.query.xml.module;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.system.status.XmlContextFactory;
import org.jeesl.model.xml.jeesl.QueryWf;
import org.jeesl.model.xml.module.workflow.Stage;
import org.jeesl.util.query.xml.XmlStatusQuery;

public class XmlWorkflowQuery
{
	public static enum Key {xProcess}
	
	private static Map<Key,QueryWf> mQueries;
	
	public static QueryWf get(Key key){return get(key,null);}
	public static QueryWf get(Key key,String localeCode)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,QueryWf>();}
		if(!mQueries.containsKey(key))
		{
			QueryWf q = new QueryWf();
			switch(key)
			{
				case xProcess: q.setProcess(xProcess());break;
			}
			mQueries.put(key, q);
		}
		QueryWf q = mQueries.get(key);
		q.setLocaleCode(localeCode);
		return q;
	}
	
	private static org.jeesl.model.xml.module.workflow.Process xProcess()
	{		
		org.jeesl.model.xml.module.workflow.Process xml = new org.jeesl.model.xml.module.workflow.Process();
		xml.setId(0);
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.setContext(XmlContextFactory.build("",""));
		xml.getStage().add(xStage());
		return xml;
	}
	
	private static Stage xStage()
	{		
		Stage xml = new Stage();
		xml.setId(0);
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
	
		return xml;
	}
}