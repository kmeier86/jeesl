package org.jeesl.factory.xml.system.status;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Contexts;
import net.sf.ahtutils.xml.status.Status;

public class XmlContextsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlContextsFactory.class);
		
	public static Contexts build(){return new Contexts();}
	public static Contexts build(List<Status> list)
	{
		Contexts xml = build();
		for(Status status : list)
		{
			xml.getContext().add(XmlContextFactory.build(status));
		}
		return xml;
	}
}