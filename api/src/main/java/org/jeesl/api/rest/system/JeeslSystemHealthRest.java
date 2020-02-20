package org.jeesl.api.rest.system;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.json.module.ts.JsonTsTimeSeries;

@Path("/rest/jeesl/health")
public interface JeeslSystemHealthRest
{
	@GET @Path("ts/{indicator}")
	@Produces(MediaType.APPLICATION_JSON)
	JsonTsTimeSeries timeseries(@PathParam("indicator") String indicator);
}