package org.jeesl.factory.json.extern.aceld;

import org.jeesl.model.json.ssi.acled.JsonAcledSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSourceFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonSourceFactory.class);

	public static JsonAcledSource build() {return new JsonAcledSource();}
		
    public static JsonAcledSource build(String name)
    {
    	JsonAcledSource json = build();
    	json.setName(name);
    	return json;
    }
    
    public static String toSsiCode(JsonAcledSource json)
    {
    	return json.getName();
    }
}