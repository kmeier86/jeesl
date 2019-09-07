package org.jeesl.model.json.system.io.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonPostgres implements Serializable
{
	public static final long serialVersionUID=1;
	
	private List<JsonPostgresConnection> connections;
	public List<JsonPostgresConnection> getConnections() {if(connections==null) {connections = new ArrayList<>();}return connections;}
	public void setConnections(List<JsonPostgresConnection> connections) {this.connections = connections;}
}