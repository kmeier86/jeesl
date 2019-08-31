package org.jeesl.util.query.xml.module;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.system.security.XmlRoleFactory;
import org.jeesl.factory.xml.system.security.XmlRolesFactory;
import org.jeesl.factory.xml.system.status.XmlContextFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.model.xml.jeesl.QueryWf;
import org.jeesl.model.xml.module.workflow.Stage;
import org.jeesl.model.xml.module.workflow.Transition;
import org.jeesl.util.query.xml.XmlStatusQuery;

import net.sf.ahtutils.xml.security.Category;
import net.sf.ahtutils.xml.security.Role;

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
		xml.setPosition(0);
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
		xml.setPosition(0);
		xml.setType(XmlTypeFactory.build("",""));
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.getTransition().add(xTransition());
		xml.setRoles(XmlRolesFactory.build(role()));
		return xml;
	}
	
	private static Role role()
	{
		Category cat = new Category();
		cat.setCode("");
		cat.setLabel("");
		
		Role role = XmlRoleFactory.create("","");
		role.setCategory(cat);
		return role;
	}
	
	private static Transition xTransition()
	{		
		Stage stage = new Stage();
		stage.setId(0);
		
		Transition xml = new Transition();
		xml.setId(0);
		xml.setPosition(0);
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.setStage(stage);
		return xml;
	}
}