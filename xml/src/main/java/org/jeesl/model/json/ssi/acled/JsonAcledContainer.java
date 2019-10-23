package org.jeesl.model.json.ssi.acled;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="incidents")
public class JsonAcledContainer implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("incidents")
	private List<JsonAcledIncident> incidents;
	public List<JsonAcledIncident> getIncidents() {return incidents;}
	public void setIncidents(List<JsonAcledIncident> incidents) {this.incidents = incidents;}
	
	@JsonProperty("countries")
	private List<JsonAcledCountry> countries;
	public List<JsonAcledCountry> getCountries() {return countries;}
	public void setCountries(List<JsonAcledCountry> countries) {this.countries = countries;}
	
	@JsonProperty("actors")
	private List<JsonAcledActor> actors;
	public List<JsonAcledActor> getActors() {return actors;}
	public void setActors(List<JsonAcledActor> actors) {this.actors = actors;}
	
	@JsonProperty("sources")
	private List<JsonAcledSource> sources;
	public List<JsonAcledSource> getSources() {return sources;}
	public void setSources(List<JsonAcledSource> sources) {this.sources = sources;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}