package org.jeesl.factory.ejb.system.erdiagram;

import org.jeesl.interfaces.model.system.io.revision.er.JeeslErDiagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbErDiagramFactory<L extends UtilsLang, D extends UtilsDescription,
								C extends UtilsStatus<C,L,D>,
								ERD extends JeeslErDiagram<L,D,C,ERD>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbErDiagramFactory.class);

	final Class<ERD> cErDiagram;

	public EjbErDiagramFactory(final Class<ERD> cErDiagram)
	{
        this.cErDiagram = cErDiagram;
	}

	public static <L extends UtilsLang, D extends UtilsDescription,
					C extends UtilsStatus<C,L,D>,
					ERD extends JeeslErDiagram<L,D,C,ERD>>
			EjbErDiagramFactory<L,D,C,ERD> factory(final Class<ERD> cErDiagram)
	{
		return new EjbErDiagramFactory<L,D,C,ERD>(cErDiagram);
	}

	public ERD build(String code, String dotGraph)
	{
		ERD ejb = null;
		try
		{
			ejb = cErDiagram.newInstance();
			ejb.setCode(code);
			ejb.setDotGraph(dotGraph);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}

}