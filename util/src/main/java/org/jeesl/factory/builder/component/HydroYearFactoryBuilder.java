package org.jeesl.factory.builder.component;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.component.EjbHydroYearFactory;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class HydroYearFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									HD extends UtilsStatus<HD,L,D>,
									HY extends JeeslHydroYear<L,D,HD,HY>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(HydroYearFactoryBuilder.class);

	private final Class<HD> cDecade; public Class<HD> getClassDecade() {return cDecade;}
	private final Class<HY> cYear; public Class<HY> getClassYear() {return cYear;}

	public HydroYearFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<HD> cDecade, final Class<HY> cYear)
	{
		super(cL,cD);
		this.cDecade=cDecade;
		this.cYear=cYear;
	}


	public EjbHydroYearFactory<L,D,HD,HY> hydroYear()
	{
		return new EjbHydroYearFactory<L,D,HD,HY>(cYear);
	}

}