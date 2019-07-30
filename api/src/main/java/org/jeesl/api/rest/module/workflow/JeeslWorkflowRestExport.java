package org.jeesl.api.rest.module.workflow;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;

public interface JeeslWorkflowRestExport
{
	@GET @Path("/module/workflow/context") @Produces(MediaType.APPLICATION_XML)
	Container exportWorkflowContext();
}