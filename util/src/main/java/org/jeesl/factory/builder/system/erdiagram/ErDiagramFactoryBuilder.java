package org.jeesl.factory.builder.system.erdiagram;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.erdiagram.EjbErDiagramFactory;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslErDiagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class ErDiagramFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									C extends UtilsStatus<C,L,D>,
									ERD extends JeeslErDiagram<L,D,C,ERD>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ErDiagramFactoryBuilder.class);

	private final Class<C> cCategory; public Class<C> getClassCategory() {return cCategory;}
	private final Class<ERD> cErDiagram; public Class<ERD> getClassErDiagram() {return cErDiagram;}

	public ErDiagramFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<C> cCategory, final Class<ERD> cErDiagram)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cErDiagram=cErDiagram;
	}


	public EjbErDiagramFactory<L,D,C,ERD> erDiagram()
	{
		return new EjbErDiagramFactory<L,D,C,ERD>(cErDiagram);
	}

}