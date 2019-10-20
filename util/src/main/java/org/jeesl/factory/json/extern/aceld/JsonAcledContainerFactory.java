package org.jeesl.factory.json.extern.aceld;

import java.util.ArrayList;

import org.jeesl.api.rest.ssi.AcledRest;
import org.jeesl.model.json.ssi.acled.JsonAcledContainer;
import org.jeesl.model.json.ssi.acled.JsonAcledData;
import org.jeesl.model.json.ssi.acled.JsonAcledResponse;
import org.jeesl.model.json.ssi.acled.JsonAcledCountry;
import org.jeesl.model.json.ssi.acled.JsonAcledIncident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonAcledContainerFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonAcledContainerFactory.class);
	
	private AcledRest rest;
	
	public JsonAcledContainerFactory(AcledRest rest)
	{
		this.rest=rest;
	}
	
	public static JsonAcledContainer build() {return new JsonAcledContainer();}
		
    public JsonAcledContainer countries(String filter)
    {
    	JsonAcledContainer container = build();
    	container.setCountries(new ArrayList<JsonAcledCountry>());
    	JsonAcledResponse acled = rest.countries("accept",filter);
    	for(JsonAcledData data : acled.getData())
    	{
    		container.getCountries().add(JsonCountryFactory.build(data));
    	}
    	return container;
    }
    
    public JsonAcledContainer incidents(JsonAcledCountry country, int limit)
    {
    	JsonAcledContainer container = build();
    	container.setIncidents(new ArrayList<JsonAcledIncident>());
    	JsonAcledResponse response = rest.readByCountry("accept",5,country.getId());
    	for(JsonAcledData data : response.getData())
    	{
    		JsonAcledIncident incident = JsonIncidentFactory.build(data);
    		incident.setCountry(country);
    		container.getIncidents().add(incident);
    	}
    	return container;
    }
}