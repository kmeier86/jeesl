package org.jeesl.factory.json.extern.aceld;

import org.jeesl.model.json.ssi.acled.JsonAcledActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonActorFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonActorFactory.class);

	public static JsonAcledActor build() {return new JsonAcledActor();}
		
    public static JsonAcledActor build(String name)
    {
    	JsonAcledActor json = build();
    	json.setName(name);
    	return json;
    }
    
    public static String toSsiCode(JsonAcledActor json)
    {
    	return json.getName();
    }
}