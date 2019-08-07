package org.jeesl.util.query.xpath;

import org.jeesl.model.xml.module.workflow.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class WorkflowXpath
{
	final static Logger logger = LoggerFactory.getLogger(WorkflowXpath.class);
	
	public static Stage toStage(org.jeesl.model.xml.module.workflow.Process process, long id) throws ExlpXpathNotFoundException
	{
		for(Stage stage : process.getStage())
		{
			if(stage.getId()==id) {return stage;}
		}
		throw new ExlpXpathNotFoundException("No stage for id="+id);
//		JXPathContext context = JXPathContext.newContext(langs);
//		
//		StringBuffer sb = new StringBuffer();
//		sb.append("lang[@key='").append(key).append("']");
//		
//		@SuppressWarnings("unchecked")
//		List<Lang> listResult = (List<Lang>)context.selectNodes(sb.toString());
//		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Lang.class.getSimpleName()+" for key="+key+" in "+JaxbUtil.toString(langs));}
//		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Lang.class.getSimpleName()+" for key="+key);}
//		return listResult.get(0);
	}
	
	
}