package org.jeesl.factory.mc.ts;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsDataPointFactory;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsInterval;
import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.data.JeeslTsDataPoint;
import org.jeesl.interfaces.model.module.ts.stat.JeeslTsStatistic;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.metachart.factory.xml.chart.XmlChartFactory;
import org.metachart.factory.xml.chart.XmlDataFactory;
import org.metachart.factory.xml.chart.XmlSubtitleFactory;
import org.metachart.factory.xml.chart.XmlTitleFactory;
import org.metachart.xml.chart.Chart;
import org.metachart.xml.chart.Data;
import org.metachart.xml.chart.Ds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.DateUtil;

public class McTimeSeriesFactory <SCOPE extends JeeslTsScope<?,?,?,?,?,EC,INT>,
								MP extends JeeslTsMultiPoint<?,?,SCOPE,?>,
								TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
								BRIDGE extends JeeslTsBridge<EC>,
								EC extends JeeslTsEntityClass<?,?,?>,
								INT extends JeeslTsInterval<?,?,INT,?>,
								STAT extends JeeslTsStatistic<?,?,STAT,?>,
								DATA extends JeeslTsData<TS,?,?,WS>,
								POINT extends JeeslTsDataPoint<DATA,MP>,
								WS extends JeeslStatus<WS,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(McTimeSeriesFactory.class);
	
	private final boolean debugOnInfo = false;
	
	private final JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fTs;
	
	private final TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fbTs;
	private final EjbTsDataPointFactory<MP,DATA,POINT> efPoint;
	
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private EC entityClass; public EC getEntityClass() {return entityClass;} public void setEntityClass(EC entityClass) {this.entityClass = entityClass;}
	private INT interval; public INT getInterval() {return interval;} public void setInterval(INT interval) {this.interval = interval;}
	private WS workspace; public WS getWorkspace() {return workspace;} public void setWorkspace(WS workspace) {this.workspace = workspace;}
	
	public McTimeSeriesFactory(TsFactoryBuilder<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fbTs,
							   JeeslTsFacade<?,?,?,SCOPE,?,?,MP,TS,?,?,BRIDGE,EC,INT,STAT,DATA,POINT,?,?,WS,?,?> fTs)
	{
		this.fbTs=fbTs;
		this.fTs=fTs;
		
		efPoint = fbTs.ejbDataPoint();
	}
	
	public <E extends Enum<E>> void initScope(E scope) throws JeeslNotFoundException {this.scope = fTs.fByCode(fbTs.getClassScope(), scope);}
	public void initEntityClass(Class<?> c) throws JeeslNotFoundException {this.entityClass = fTs.fByCode(fbTs.getClassEntity(), c.getName());}
	public <E extends Enum<E>> void initInterval(E interval) throws JeeslNotFoundException {this.interval = fTs.fByCode(fbTs.getClassInterval(), interval);}
	public <E extends Enum<E>> void initWorkspace(E workspace) throws JeeslNotFoundException {this.workspace = fTs.fByCode(fbTs.getClassWorkspace(), workspace);}

	public Chart build(String localeCode) 
	{
		Chart chart = XmlChartFactory.build();
		chart.setTitle(XmlTitleFactory.build(scope.getName().get(localeCode).getLang()));
		chart.setSubtitle(XmlSubtitleFactory.build("Interval: "+interval.getName().get(localeCode).getLang()));
		return chart;
	}
		
	public Ds build2(List<DATA> datas)
	{
		Ds ds = new Ds();
		
		for(DATA data: datas)
		{
			Data cd = new Data();
			cd.setRecord(DateUtil.toXmlGc(data.getRecord()));
			if(data.getValue()!=null) {cd.setY(data.getValue());}
			ds.getData().add(cd);
		}
		return ds;	
	}
	
	public static Ds build2(TimeSeries timeSeries)
	{
		Ds ds = new Ds();
		
		for(org.jeesl.model.xml.module.ts.Data tsD: timeSeries.getData())
		{
			if (tsD.isSetValue())
			{
				Data cd = new Data();
				cd.setRecord(tsD.getRecord());

				cd.setY(tsD.getValue());
				ds.getData().add(cd);
			}
		}
		return ds;	
	}
	
	public <T extends EjbWithId> Ds singleData(String localeCode, T entity, Date from, Date to) throws JeeslNotFoundException
	{
		BRIDGE bridge = fTs.fBridge(entityClass,entity);
		return singleData(localeCode,bridge,from,to);
	}
	public <T extends EjbWithId> Ds singleData(String localeCode, BRIDGE bridge, Date from, Date to) throws JeeslNotFoundException
	{
		TS ts = fTs.fTimeSeries(scope,interval,bridge);
		List<DATA> datas = fTs.fData(workspace,ts,from,to);
		
		Ds xml = new Ds();
		for(DATA d : datas)
		{
			xml.getData().add(XmlDataFactory.build(d.getValue(),d.getRecord()));
		}
		return xml;
	}
	
	public <T extends EjbWithId> Ds multiPoint(String localeCode, T entity, Date from, Date to) throws JeeslNotFoundException
	{
		BRIDGE bridge = fTs.fBridge(entityClass,entity);
		TS ts = fTs.fTimeSeries(scope,interval,bridge);
		
		List<MP> multiPoints = fTs.allForParent(fbTs.getClassMp(),scope);
		List<DATA> datas = fTs.fData(workspace,ts,from,to);
		List<POINT> points = fTs.fPoints(workspace,ts,from,to);
		Map<MP,List<POINT>> mapMp = efPoint.toMapMultiPoint(points);
		
		if(debugOnInfo)
		{
			logger.info("Datas: "+datas.size());
			logger.info("Points: "+points.size());
		}
		
		Ds xml = new Ds();
		for(MP mp : multiPoints)
		{
			if(mp.getVisible() && mapMp.containsKey(mp))
			{
				if(debugOnInfo) {logger.info("MP: "+mp.getCode());}
				Map<DATA,POINT> mapData = efPoint.toMapDataUnique(mapMp.get(mp));
				if(debugOnInfo) {logger.info("\t mapData.size():"+mapData.size());}
				Ds ds = new Ds();
				ds.setLabel(mp.getName().get(localeCode).getLang());
				for(DATA data : datas)
				{
					Data d = new Data();
					d.setRecord(DateUtil.toXmlGc(data.getRecord()));
					POINT p = mapData.get(data);
					if(debugOnInfo) {logger.info("P: "+(p!=null) + " "+mapData.containsKey(data));}
					
					if(p!=null) {d.setY(p.getValue());}
					ds.getData().add(d);
				}
				xml.getDs().add(ds);
			}
		}
		
		return xml;	
	}
}