package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.erdiagram.JeeslERDiagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class ERDiagramFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									C extends UtilsStatus<C,L,D>,
									ERD extends JeeslERDiagram<L,D,C,ERD>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ERDiagramFactoryBuilder.class);
	
	private final Class<C> cCategory; public Class<C> getClassCategory() {return cCategory;}
	private final Class<ERD> cErdiagram; public Class<ERD> getClassERDiagram() {return cErdiagram;}
	
	public ERDiagramFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<C> cCategory, final Class<ERD> cErdiagram)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cErdiagram=cErdiagram;
	}
}