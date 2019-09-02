package org.jeesl.factory.ejb.system.component;

import org.jeesl.interfaces.model.component.JeeslHydroYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbHydroYearFactory<L extends UtilsLang, D extends UtilsDescription,
								HD extends UtilsStatus<HD,L,D>,
								HY extends JeeslHydroYear<L,D,HD,HY>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbHydroYearFactory.class);

	final Class<HY> cYear;

	public EjbHydroYearFactory(final Class<HY> cYear)
	{
        this.cYear = cYear;
	}

	public static <L extends UtilsLang, D extends UtilsDescription,
					HD extends UtilsStatus<HD,L,D>,
					HY extends JeeslHydroYear<L,D,HD,HY>>
	EjbHydroYearFactory<L,D,HD,HY> factory(final Class<HY> cYear)
	{
		return new EjbHydroYearFactory<L,D,HD,HY>(cYear);
	}

	public HY build(String code)
	{
		HY ejb = null;
		try
		{
			ejb = cYear.newInstance();
			ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}

}