package org.jeesl.interfaces.model.system.revision;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRevisionEntityMapping<L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible
{			
	public static enum Type{xpath,jpqlTree}
	
	RE getEntity();
	void setEntity(RE entity);
	
	RS getScope();
	void setScope(RS scope);
	
	RST getType();
	void setType(RST type);
	
	String getXpath();
	void setXpath(String xpath);
	
	String getJpqlTree();
	void setJpqlTree(String jpqlTree);
}