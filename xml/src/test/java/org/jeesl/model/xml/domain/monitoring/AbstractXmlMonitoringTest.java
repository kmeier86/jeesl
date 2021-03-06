package org.jeesl.model.xml.domain.monitoring;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlMonitoringTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlMonitoringTest.class);
    
	public AbstractXmlMonitoringTest(Class<T> cXml)
	{
		super(cXml,"monitoring");
	}
}