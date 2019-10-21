package org.jeesl.factory.json.extern.aceld;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jeesl.model.json.ssi.acled.JsonAcledData;
import org.jeesl.model.json.ssi.acled.JsonAcledIncident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonIncidentFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonIncidentFactory.class);

	private static DateFormat dfEvent = new SimpleDateFormat("yyyy-MM-dd");
	
	public static JsonAcledIncident build() {return new JsonAcledIncident();}
		
    public static JsonAcledIncident build(JsonAcledData data)
    {
    	JsonAcledIncident json = build();
    	json.setCode(data.getCode());
    	json.setMainType(data.getMainType());
    	json.setSubType(data.getSubType());
    	json.setLocation(data.getLocation());
    	json.setDescription(data.getNotes());
    	try {json.setDate(dfEvent.parse(data.getDate()));} catch (ParseException e) {e.printStackTrace();}
    	json.setLatitude(data.getLatitude());
    	json.setLongitude(data.getLongitude());
    	
    	if(data.getActor1()!=null && data.getActor1().trim().length()>1) {json.setActor1(JsonActorFactory.build(data.getActor1().trim()));}
    	if(data.getActor2()!=null && data.getActor2().trim().length()>1) {json.setActor2(JsonActorFactory.build(data.getActor2().trim()));}
    	
    	return json;
    }
    
    public static String toSsiCode(JsonAcledIncident json)
    {
    	return json.getCode();
    }
}