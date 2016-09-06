package org.jeesl.interfaces.rest.system.news;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;

public interface JeeslSystemNewsRestExport
{
	@GET @Path("/system/news/category") @Produces(MediaType.APPLICATION_XML)
	Aht exportSystemNewsCategories();
}