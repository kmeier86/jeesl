package org.jeesl.factory.json.system.io.db;

import org.jeesl.model.json.system.io.db.JsonPostgres;

public class JsonPostgresFactory 
{
	public static JsonPostgres build()
	{
		return new JsonPostgres();
	}
}