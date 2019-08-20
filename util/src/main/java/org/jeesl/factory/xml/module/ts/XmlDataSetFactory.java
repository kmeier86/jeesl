package org.jeesl.factory.xml.module.ts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jeesl.model.xml.module.ts.Data;
import org.jeesl.model.xml.module.ts.Ds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDataSetFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlDataSetFactory.class);
	
	public static Ds build(){return new Ds();}
	
	public static Ds build(List<Data> datas)
	{
		Ds xml = build();
		xml.getData().addAll(datas);
		return xml;
	}
	
	public static Ds build(Ds... ds)
	{
		Ds xml = build();
		xml.getDs().addAll(new ArrayList<Ds>(Arrays.asList(ds)));
		return xml;
	}
	
	public static Ds build(Ds ds)
	{
		Ds xml = build();
		xml.getDs().add(ds);
		return xml;
	}
}