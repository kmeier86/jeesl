package org.jeesl.model.json.ssi.acled;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="response")
public class JsonIncident implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("code")
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	@JsonProperty("mainType")
	private String mainType;
	public String getMainType() {return mainType;}
	public void setMainType(String mainType) {this.mainType = mainType;}
	
	@JsonProperty("subType")
	private String subType;
	public String getSubType() {return subType;}
	public void setSubType(String subType) {this.subType = subType;}
	
	@JsonProperty("location")
	private String location;
	public String getLocation() {return location;}
	public void setLocation(String location) {this.location = location;}
	
	@JsonProperty("description")
	private String description;
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	@JsonProperty("country")
	private JsonCountry country;
	public JsonCountry getCountry() {return country;}
	public void setCountry(JsonCountry country) {this.country = country;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}