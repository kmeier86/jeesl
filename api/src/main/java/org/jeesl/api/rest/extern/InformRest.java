package org.jeesl.api.rest.extern;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/API/InformAPI")
public interface InformRest
{				
	@GET @Path("/Workflows/WorkflowGroups")
	@Produces(MediaType.TEXT_PLAIN) String sWorkflows();
	
	@GET @Path("/Countries/Index")
	@Produces(MediaType.TEXT_PLAIN) String sCountries();
	
	@GET @Path("/Workflows/WorkflowGroups")
	@Produces(MediaType.APPLICATION_JSON) List<String> workflows();
}