package org.jeesl.api.rest.system.io.revision;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.revision.Entities;
import org.metachart.xml.graph.Graph;
import org.metachart.xml.graph.Graphs;

public interface JeeslRevisionRestExport
{
	@GET @Path("/system/io/revision/category") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemRevisionCategories();
	
	@GET @Path("/system/io/revision/attribute/type") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoRevisionAttributeTypes();
	
	@GET @Path("/system/io/revision/scope/type") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemIoRevisionScopeTypes();
	
	@GET @Path("/system/io/revision/relation/type") @Produces(MediaType.APPLICATION_XML)
	Container exportSystemRevisionRelationType();
	
	@GET @Path("/system/revision/entities") @Produces(MediaType.APPLICATION_XML)
	Entities exportSystemRevisionEntities();
	
	@GET @Path("/system/revision/graphs") @Produces(MediaType.APPLICATION_XML)
	Graphs exportSystemRevisionGraphs();
	
	@GET @Path("/system/revision/graph/{code}") @Produces(MediaType.APPLICATION_XML)
	Graph exportSystemRevisionGraph(@PathParam("code") String code);
}