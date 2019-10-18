package org.jeesl.api.rest.ssi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.json.ssi.acled.JsonAcledResponse;

public interface AcledRest
{				
    @GET @Path("/acled/read")
	@Produces(MediaType.TEXT_PLAIN)
    String sRead(@QueryParam("terms") String terms);
    
    @GET @Path("/acled/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse read(@QueryParam("terms") String terms, @QueryParam("limit") int limit);
    
    @GET @Path("/acled/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse readByCountry(@QueryParam("terms") String terms, @QueryParam("limit") int limit, @QueryParam("iso") Long iso);
    
    @GET @Path("/country/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse countries(@QueryParam("terms") String terms);
    
    @GET @Path("/country/read")
	@Produces(MediaType.APPLICATION_JSON)
    JsonAcledResponse countries(@QueryParam("terms") String terms, @QueryParam("iso3") String iso3Filter);
}