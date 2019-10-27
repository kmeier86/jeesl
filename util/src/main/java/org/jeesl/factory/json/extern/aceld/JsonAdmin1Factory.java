package org.jeesl.factory.json.extern.aceld;

import org.jeesl.model.json.ssi.acled.JsonAcledAdmin1;
import org.jeesl.model.json.ssi.acled.JsonAcledCountry;
import org.jeesl.model.json.ssi.acled.JsonAcledData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonAdmin1Factory
{
	final static Logger logger = LoggerFactory.getLogger(JsonAdmin1Factory.class);

	public static JsonAcledAdmin1 build() {return new JsonAcledAdmin1();}
		
    public static JsonAcledAdmin1 build(JsonAcledCountry country, JsonAcledData data)
    {
    	JsonAcledAdmin1 json = build();
    	json.setCountry(country);
    	json.setName(data.getAdmin1());
    	return json;
    }
    
    public static String toSsiCode(JsonAcledAdmin1 json)
    {
    	return json.getCountry().getIso3()+";"+json.getName();
    }
}