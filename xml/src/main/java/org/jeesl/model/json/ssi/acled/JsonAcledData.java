package org.jeesl.model.json.ssi.acled;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="data")
public class JsonAcledData implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("event_id_cnty")
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	@JsonProperty("event_type")
	private String mainType;
	public String getMainType() {return mainType;}
	public void setMainType(String mainType) {this.mainType = mainType;}
	
	@JsonProperty("sub_event_type")
	private String subType;
	public String getSubType() {return subType;}
	public void setSubType(String subType) {this.subType = subType;}
	
	@JsonProperty("location")
	private String location;
	public String getLocation() {return location;}
	public void setLocation(String location) {this.location = location;}
	
	@JsonProperty("notes")
	private String notes;
	public String getNotes() {return notes;}
	public void setNotes(String notes) {this.notes = notes;}
	
	@JsonProperty("iso3")
	private String iso3;
	public String getIso3() {return iso3;}
	public void setIso3(String iso3) {this.iso3 = iso3;}
	
	@JsonProperty("iso")
	private String iso;
	public String getIso() {return iso;}
	public void setIso(String iso) {this.iso = iso;}
	
	@JsonProperty("country")
	private String country;
	public String getCountry() {return country;}
	public void setCountry(String country) {this.country = country;}

	@JsonProperty("event_count")
	private int count;
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}