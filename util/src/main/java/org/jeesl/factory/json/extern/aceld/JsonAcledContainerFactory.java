package org.jeesl.factory.json.extern.aceld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.rest.ssi.acled.AcledExternRest;
import org.jeesl.model.json.ssi.acled.JsonAcledActor;
import org.jeesl.model.json.ssi.acled.JsonAcledContainer;
import org.jeesl.model.json.ssi.acled.JsonAcledCountry;
import org.jeesl.model.json.ssi.acled.JsonAcledData;
import org.jeesl.model.json.ssi.acled.JsonAcledIncident;
import org.jeesl.model.json.ssi.acled.JsonAcledResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonAcledContainerFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsonAcledContainerFactory.class);
	
	private AcledExternRest rest;
	
	public JsonAcledContainerFactory(AcledExternRest rest)
	{
		this.rest=rest;
	}
	
	public static JsonAcledContainer build() {return new JsonAcledContainer();}
	public static JsonAcledContainer buildActors(List<JsonAcledActor> actors) {JsonAcledContainer json = build();json.setActors(actors);return json;}
	public static JsonAcledContainer buildIncidents(List<JsonAcledIncident> incidents) {JsonAcledContainer json = build();json.setIncidents(incidents);return json;}
	
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
    
    public JsonAcledContainer incidents(JsonAcledCountry country)
    {
    	Map<String,JsonAcledActor> mapActors = new HashMap<>();
    	
    	JsonAcledContainer container = build();
    	container.setIncidents(new ArrayList<JsonAcledIncident>());
    	
    	boolean hasResults = true;
    	int page=1;
    	
    	while(hasResults)
    	{
    		logger.info("Country:"+country.getName()+" Page "+page);
    		JsonAcledResponse response = rest.readByCountry("accept",100,page,country.getId());
        	for(JsonAcledData data : response.getData())
        	{
        		JsonAcledIncident incident = JsonIncidentFactory.build(data);
        		incident.setCountry(country);
        		if(incident.getActor1()!=null && !mapActors.containsKey(incident.getActor1().getName())) {mapActors.put(incident.getActor1().getName(), JsonActorFactory.build(incident.getActor1().getName()));}
        		if(incident.getActor2()!=null && !mapActors.containsKey(incident.getActor2().getName())) {mapActors.put(incident.getActor2().getName(), JsonActorFactory.build(incident.getActor2().getName()));}
        		container.getIncidents().add(incident);
        	}
        	hasResults = !response.getData().isEmpty();
        	page++;
    	}
    	
    	container.setActors(new ArrayList<>(mapActors.values()));
    	
    	return container;
    }
}