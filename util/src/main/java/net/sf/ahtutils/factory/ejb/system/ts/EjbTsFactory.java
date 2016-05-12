package net.sf.ahtutils.factory.ejb.system.ts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTimeSeries;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsData;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsBridge;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsEntityClass;
import net.sf.ahtutils.interfaces.model.system.ts.UtilsTsScope;

public class EjbTsFactory<L extends UtilsLang,D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							SCOPE extends UtilsTsScope<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							TS extends UtilsTimeSeries<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							BRIDGE extends UtilsTsBridge<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							EC extends UtilsTsEntityClass<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends UtilsTsData<L,D,CAT,SCOPE,UNIT,TS,BRIDGE,EC,INT,DATA,WS,QAF>,
							WS extends UtilsStatus<WS,L,D>,
							QAF extends UtilsStatus<QAF,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsFactory.class);
	
	final Class<TS> cTs;
    
	public EjbTsFactory(final Class<TS> cTs)
	{       
        this.cTs=cTs;
	}
	
	public TS build(SCOPE scope, INT interval, BRIDGE bridge)
	{
		TS ejb = null;
		try
		{
			ejb = cTs.newInstance();
			ejb.setScope(scope);
			ejb.setInterval(interval);
			ejb.setBridge(bridge);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}