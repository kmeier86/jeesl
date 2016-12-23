package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Templates;

public class TestXmlTemplates extends AbstractXmlReportTest<Templates>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTemplates.class);
	
	public TestXmlTemplates(){super(Templates.class);}
	public static Templates create(boolean withChildren){return (new TestXmlTemplates()).build(withChildren);} 
    
    public Templates build(boolean withChildren)
    {
    	Templates templates = new Templates();
    	templates.getTemplate().add(TestXmlTemplate.create(false));
    	return templates;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTemplates test = new TestXmlTemplates();
		test.saveReferenceXml();
    }
}