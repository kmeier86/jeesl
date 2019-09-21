package org.jeesl.interfaces.model.module.ts.stat;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslTsCron <SCOPE extends JeeslTsScope<?,?,?,?,?,?,INT>,
									INT extends UtilsStatus<INT,?,?>,
									STAT extends JeeslTsStatistic<?,?,STAT,?>>
		extends EjbWithId,Serializable,EjbRemoveable,EjbPersistable,
				EjbWithVisible
{
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	INT getIntervalExec();
	void setIntervalExec(INT intervalExec);

	INT getIntervalSrc();
	void setIntervalSrc(INT intervalSrc);

	INT getIntervalDst();
	void setIntervalDst(INT intervalDst);
	
	STAT getStatistic();
	void setStatistic(STAT statistic);
	
	int getFallbackSteps();
	void setFallbackSteps(int fallbackSteps);
}