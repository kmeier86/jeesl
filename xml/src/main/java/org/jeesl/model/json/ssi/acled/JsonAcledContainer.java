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
	private List<JsonIncident> incidents;
	public List<JsonIncident> getIncidents() {return incidents;}
	public void setIncidents(List<JsonIncident> incidents) {this.incidents = incidents;}
	
	@JsonProperty("countries")
	private List<JsonCountry> countries;
	public List<JsonCountry> getCountries() {return countries;}
	public void setCountries(List<JsonCountry> countries) {this.countries = countries;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}