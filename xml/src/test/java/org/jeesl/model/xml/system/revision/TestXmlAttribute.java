package org.jeesl.model.xml.system.revision;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.text.TestXmlRemark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.status.TestXmlType;

public class TestXmlAttribute extends AbstractXmlRevisionTest<Attribute>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAttribute.class);
	
	public TestXmlAttribute(){super(Attribute.class);}
	public static Attribute create(boolean withChildren){return (new TestXmlAttribute()).build(withChildren);} 
    
    public Attribute build(boolean withChilds)
    {
    	Attribute xml = new Attribute();
    	xml.setId(123);
    	xml.setPosition(2);
    	xml.setCode("myCode");	
    	xml.setXpath("myXPath");

    	xml.setWeb(true);
    	xml.setPrint(true);
    	xml.setName(true);
    	xml.setEnclosure(true);
    	
    	if(withChilds)
    	{
    		xml.setType(TestXmlType.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setRemark(TestXmlRemark.create(false));
    	}
    	    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlAttribute test = new TestXmlAttribute();
		test.saveReferenceXml();
    }
}