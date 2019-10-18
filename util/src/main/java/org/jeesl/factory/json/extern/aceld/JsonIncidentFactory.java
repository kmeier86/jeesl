package org.jeesl.factory.json.extern.aceld;

import org.jeesl.model.json.ssi.acled.JsonAcledData;
import org.jeesl.model.json.ssi.acled.JsonIncident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonIncidentFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonIncidentFactory.class);

	public static JsonIncident build() {return new JsonIncident();}
		
    public static JsonIncident build(JsonAcledData data)
    {
    	JsonIncident json = build();
    	json.setCode(data.getCode());
    	json.setMainType(data.getMainType());
    	json.setSubType(data.getSubType());
    	json.setDescription(data.getNotes());
    	return json;
    }
}