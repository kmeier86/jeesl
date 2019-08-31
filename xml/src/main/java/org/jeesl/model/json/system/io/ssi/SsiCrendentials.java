package org.jeesl.model.json.system.io.ssi;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="crendentials")
public class SsiCrendentials implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("url")
	private String url;
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}

	@JsonProperty("url")
	private String user;
	public String getUser() {return user;}
	public void setUser(String user) {this.user = user;}

	@JsonProperty("url")
	private String password;
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
}