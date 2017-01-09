package org.jeesl.factory.json.system.io.report;

import org.jeesl.model.json.JsonFlatFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;

public class JsonFlatFigureFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonFlatFigureFactory.class);
	
	public static JsonFlatFigure build(Double...doubles)
	{
		JsonFlatFigure json = new JsonFlatFigure();
		for(int i=1;i<=doubles.length;i++)
		{
			double value = doubles[i-1];
			if(i==1){json.setD1(value);}
			else if(i==2){json.setD2(value);}
			else if(i==3){json.setD3(value);}
			else if(i==4){json.setD4(value);}
			else {logger.warn("No Handling for double.index="+(i-1));}
		}
		return json;
	}
	
	public static JsonFlatFigure build(Figures figures)
	{
		JsonFlatFigure json = new JsonFlatFigure();
		for(Finance f : figures.getFinance())
		{
			if(f.getNr()==1){json.setD1(f.getValue());}
			else if(f.getNr()==2){json.setD2(f.getValue());}
			else if(f.getNr()==3){json.setD3(f.getValue());}
			else if(f.getNr()==4){json.setD4(f.getValue());}
			else {logger.warn("No Handling for double.index="+f.getNr());}
		}
		return json;
	}
}