package org.jeesl.factory.json.extern.aceld;

import org.jeesl.model.json.ssi.acled.JsonAcledData;
import org.jeesl.model.json.ssi.acled.JsonCountry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonCountryFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonCountryFactory.class);

	public static JsonCountry build() {return new JsonCountry();}
		
    public static JsonCountry build(JsonAcledData data)
    {
    	JsonCountry json = build();
    	json.setId(Long.valueOf(data.getIso()));
    	json.setName(data.getCountry());
    	json.setEvents(data.getCount());
    	json.setIso3(data.getIso3());
    	return json;
    }
    
    public static String toSsiCode(JsonCountry json)
    {
    	return json.getIso3();
    }
}