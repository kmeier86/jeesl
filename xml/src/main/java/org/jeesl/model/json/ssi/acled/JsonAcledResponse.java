package org.jeesl.model.json.ssi.acled;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="response")
public class JsonAcledResponse implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("status")
	private int status;
	public int getStatus() {return status;}
	public void setStatus(int status) {this.status = status;}

	@JsonProperty("data")
	private List<JsonAcledData> datas;
	public List<JsonAcledData> getData() {return datas;}
	public void setData(List<JsonAcledData> datas) {this.datas = datas;}
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();	
		return sb.toString();
	}
}