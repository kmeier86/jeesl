package org.jeesl.model.xml.system.revision;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.text.TestXmlRemark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlCategory;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlEntity extends AbstractXmlRevisionTest<Entity>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEntity.class);
	
	public TestXmlEntity(){super(Entity.class);}
	public static Entity create(boolean withChildren){return (new TestXmlEntity()).build(withChildren);} 
    
    public Entity build(boolean withChilds)
    {
    	Entity xml = new Entity();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setPosition(2);
    	xml.setVisible(true);
    	
    	if(withChilds)
    	{
    		xml.setCategory(TestXmlCategory.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setRemark(TestXmlRemark.create(false));
    		xml.getAttribute().add(TestXmlAttribute.create(false));
    		xml.getAttribute().add(TestXmlAttribute.create(false));
    	}
    	    	
    	return xml;
    }
    
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();	
		TestXmlEntity test = new TestXmlEntity();
		test.saveReferenceXml();
    }
}