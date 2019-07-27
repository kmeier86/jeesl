package org.jeesl.model.xml.module.ts;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTs extends AbstractXmlTimeseriesTest<Ts>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTs.class);
	
	public TestXmlTs(){super(Ts.class);}
	public static Ts create(boolean withChildren){return (new TestXmlTs()).build(withChildren);}
    
    public Ts build(boolean withChilds)
    {
    	Ts xml = new Ts();
    	
    	if(withChilds)
    	{
    		xml.setEntity(TestXmlEntity.create(false));
    		xml.getData().add(TestXmlData.create(false));
    		xml.getData().add(TestXmlData.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlTs test = new TestXmlTs();
		test.saveReferenceXml();
    }
}