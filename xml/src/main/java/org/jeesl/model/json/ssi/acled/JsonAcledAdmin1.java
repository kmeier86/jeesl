package org.jeesl.model.json.ssi.acled;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="admin1")
public class JsonAcledAdmin1 implements Serializable
{
	public static final long serialVersionUID=1;

//	@JsonProperty("id")
//	private Long id;
//	public Long getId() {return id;}
//	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("country")
	private JsonAcledCountry country;
	public JsonAcledCountry getCountry() {return country;}
	public void setCountry(JsonAcledCountry country) {this.country = country;}

	@JsonProperty("name")
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}