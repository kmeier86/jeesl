package net.sf.ahtutils.factory.ejb.system.revision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;

public class EjbRevisionScopeFactory<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionScopeFactory.class);
	
	final Class<RS> cScope;
    
	public EjbRevisionScopeFactory(final Class<RS> cScope)
	{       
        this.cScope = cScope;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>,
					RAT extends UtilsStatus<RAT,L,D>>
	EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT> factory(final Class<RS> cScope)
	{
		return new EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RE,REM,RA,RAT>(cScope);
	}
    
	public RS build()
	{
		RS ejb = null;
		try
		{
			ejb = cScope.newInstance();
			ejb.setPosition(0);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}