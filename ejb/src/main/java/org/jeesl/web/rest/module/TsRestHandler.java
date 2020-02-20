package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsInterval;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScopeType;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsTransaction;
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsCron;
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsStatistic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TsRestHandler <L extends JeeslLang, D extends JeeslDescription,
							CAT extends JeeslStatus<CAT,L,D>,
							SCOPE extends JeeslTsScope<L,D,CAT,ST,UNIT,EC,INT>,
							ST extends JeeslTsScopeType<L,D,ST,?>,
							UNIT extends JeeslStatus<UNIT,L,D>,
							MP extends JeeslTsMultiPoint<L,D,SCOPE,UNIT>,
							TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
							TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER,?>,
							SOURCE extends EjbWithLangDescription<L,D>, 
							BRIDGE extends JeeslTsBridge<EC>,
							EC extends JeeslTsEntityClass<L,D,CAT>,
							INT extends JeeslTsInterval<L,D,INT,?>,
							STAT extends JeeslTsStatistic<L,D,STAT,?>,
							DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
							POINT extends JeeslTsDataPoint<DATA,MP>,
							SAMPLE extends JeeslTsSample, 
							USER extends EjbWithId, 
							WS extends JeeslStatus<WS,L,D>,
							QAF extends JeeslStatus<QAF,L,D>,
							CRON extends JeeslTsCron<SCOPE,INT,STAT>>
					extends AbstractJeeslRestService<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(TsRestHandler.class);
	
	private final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs;
	private final JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs;
	
	
	public TsRestHandler(TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs,
							JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs)
	{
		super(fTs,fbTs.getClassL(),fbTs.getClassD());
		this.fbTs=fbTs;
		this.fTs=fTs;
	}
		
}