package net.sf.ahtutils.xml.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;

public class TestXmlAggregationMethod extends AbstractXmlStatusTest<AggregationMethod>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAggregationMethod.class);
	
	public TestXmlAggregationMethod(){super(AggregationMethod.class);}
	public static AggregationMethod create(boolean withChildren){return (new TestXmlAggregationMethod()).build(withChildren);}   
    
    public AggregationMethod build(boolean withChildren)
    {
    	AggregationMethod xml = new AggregationMethod();
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChildren)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}

    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlAggregationMethod test = new TestXmlAggregationMethod();
		test.saveReferenceXml();
    }
}