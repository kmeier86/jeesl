package org.jeesl.api.rest.system;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.interfaces.util.qualifier.JeeslRestSecured;

@Path("/rest/jeesl/health")
public interface JeeslSystemHealthRest
{
	@GET @Path("time")
	@Produces(MediaType.TEXT_PLAIN)
	String getTime();
	
}