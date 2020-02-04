package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;

public interface JeeslTsData <TS extends JeeslTimeSeries<?,?,?>,
								TRANSACTION extends JeeslTsTransaction<?,?,?,?>,
								SAMPLE extends JeeslTsSample, 
								WS extends JeeslStatus<WS,?,?>>
		extends JeeslTsValue,EjbSaveable,Serializable,EjbRemoveable,EjbPersistable
{
	enum Attributes{transaction,timeSeries,workspace,record,value}
	
	TS getTimeSeries();
	void setTimeSeries(TS timeSeries);
	
	WS getWorkspace();
	void setWorkspace(WS workspace);
	
	TRANSACTION getTransaction();
	void setTransaction(TRANSACTION transaction);
	
	SAMPLE getSample();
	void setSample(SAMPLE sample);
}