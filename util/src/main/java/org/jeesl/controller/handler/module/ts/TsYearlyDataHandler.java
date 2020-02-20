package org.jeesl.controller.handler.module.ts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.exception.ejb.JeeslNotFoundException;
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
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsStatistic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.model.json.db.tuple.JsonIdValue;
import org.jeesl.model.json.util.time.JsonYear;
import org.jeesl.model.pojo.map.generic.Nested2Map;
import org.jeesl.util.comparator.json.JsonYearComparator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class TsYearlyDataHandler <L extends JeeslLang, D extends JeeslDescription,
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
								DOMAIN extends EjbWithId>
				implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(TsYearlyDataHandler.class);
	
	private final JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,?> fTs;
	private final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,?> fbTs;
	
	private final Comparator<JsonYear> cpYear;
//	private JeeslComparatorProvider<T> jcpA; public void setComparatorProviderA(JeeslComparatorProvider<A> jcpA) {this.jcpA = jcpA;}
	
	private final Map<Integer,JsonYear> mapYears;
	private final Nested2Map<EjbWithId,JsonYear,JsonIdValue> nestedMap; public Nested2Map<EjbWithId,JsonYear,JsonIdValue> getNestedMap() {return nestedMap;}
	private final List<EjbWithId> domains; public List<EjbWithId> getDomains() {return domains;}
	private final List<JsonYear> years; public List<JsonYear> getYears() {return years;}
	
	private EC entityClass; public EC getEntityClass() {return entityClass;}
	private SCOPE scope; public SCOPE getScope() {return scope;}
	private INT interval;
	private WS workspace;
	
	public TsYearlyDataHandler(JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,?> fTs,
			TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,?> fbTs)
	{
		this.fTs=fTs;
		this.fbTs=fbTs;
		
		nestedMap = new Nested2Map<EjbWithId,JsonYear,JsonIdValue>();
		mapYears = new HashMap<Integer,JsonYear>();
		domains = new ArrayList<EjbWithId>();
		years = new ArrayList<JsonYear>();

		cpYear = new JsonYearComparator();
	}
	
	public <E1 extends Enum<E1>, E2 extends Enum<E2>, E3 extends Enum<E3>> void init(Class<DOMAIN> cDomain, E1 scope, E2 interval, E3 workspace)
	{
		try
		{
			entityClass = fTs.fByCode(fbTs.getClassEntity(), cDomain.getName());
			init(entityClass,scope,interval,workspace);
		}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
		
	}
	
	public <E1 extends Enum<E1>, E2 extends Enum<E2>, E3 extends Enum<E3>> void init(EC entityClass, E1 scope, E2 interval, E3 workspace)
	{
		this.entityClass=entityClass;
		try
		{
			this.scope = fTs.fByCode(fbTs.getClassScope(), scope);
			this.interval = fTs.fByCode(fbTs.getClassInterval(), interval);
			this.workspace = fTs.fByCode(fbTs.getClassWorkspace(), workspace);
			
		}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
	}
	
	public void clear()
	{
		nestedMap.clear();
		domains.clear();
		years.clear();
		mapYears.clear();
	}

	public <E1 extends Enum<E1>, E2 extends Enum<E2>> void reload(List<DOMAIN> domains, Date start, Date end)
	{
		clear();
		this.domains.addAll(domains);
		
		for(DOMAIN t : domains)
		{
			try
			{
				BRIDGE bridge = fTs.fBridge(entityClass, t);
				TS ts = fTs.fTimeSeries(scope,interval,bridge);
				List<DATA> datas = fTs.fData(workspace,ts,start,end);
				process(t,datas);
			}
			catch (JeeslNotFoundException e) {logger.warn(e.getMessage());}
		}
		Collections.sort(years,cpYear);
	}
	public <E1 extends Enum<E1>, E2 extends Enum<E2>> void reloadId(List<EjbWithId> domains, Date start, Date end)
	{
		clear();
		this.domains.addAll(domains);
		
		for(EjbWithId t : domains)
		{
			try
			{
				BRIDGE bridge = fTs.fBridge(entityClass,t);
				TS ts = fTs.fTimeSeries(scope,interval,bridge);
				List<DATA> datas = fTs.fData(workspace,ts,start,end);
				process(t,datas);
			}
			catch (JeeslNotFoundException e) {logger.warn(e.getMessage());}
		}
		Collections.sort(years,cpYear);
	}
	
	private void process(EjbWithId domain, List<DATA> datas)
	{
		for(DATA d : datas)
		{
			DateTime dt = new DateTime(d.getRecord());
			JsonYear year = getYear(dt.getYear());
			
			if(d.getValue()!=null)
			{
				JsonIdValue v = new JsonIdValue();
				v.setD1(d.getValue());
				nestedMap.put(domain,year,v);
			}
		}
	}

	private JsonYear getYear(int year)
	{
		if(mapYears.containsKey(year)) {return mapYears.get(year);}
		else
		{
			JsonYear json = new JsonYear();
			json.setId(mapYears.size()+1);
			json.setYear(year);
			years.add(json);
			mapYears.put(year,json);
			return json;
		}
	}
}