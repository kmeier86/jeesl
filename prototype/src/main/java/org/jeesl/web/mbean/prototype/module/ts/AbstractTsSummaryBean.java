package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.controller.handler.tuple.JsonTuple1Handler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractTsSummaryBean <L extends JeeslLang, D extends JeeslDescription,LOC extends JeeslStatus<LOC,L,D>,
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
					extends AbstractAdminTsBean<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractTsSummaryBean.class);

	protected final SbSingleHandler<EC> sbhClass; public SbSingleHandler<EC> getSbhClass() {return sbhClass;}
	private final JsonTuple1Handler<TS> th; public JsonTuple1Handler<TS> getTh() {return th;}
	
	protected final Map<Long,EjbWithId> mapBridge; public Map<Long, EjbWithId> getMapBridge() {return mapBridge;}
	protected final Map<BRIDGE,List<TS>> mapTs; public Map<BRIDGE, List<TS>> getMapTs() {return mapTs;}
	
	protected List<BRIDGE> bridges; public List<BRIDGE> getBridges() {return bridges;}
	protected List<TS> series; public List<TS> getSeries() {return series;} public void setSeries(List<TS> series) {this.series = series;}
	
	protected BRIDGE bridge;  public BRIDGE getBridge() {return bridge;} public void setBridge(BRIDGE bridge) {this.bridge = bridge;}
	private TS ts; public TS getTs() {return ts;} public void setTs(TS ts) {this.ts = ts;}
	
	
	public AbstractTsSummaryBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs)
	{
		super(fbTs);
		mapBridge = new HashMap<Long,EjbWithId>();
		mapTs = new HashMap<BRIDGE,List<TS>>();
		sbhClass = new SbSingleHandler<EC>(fbTs.getClassEntity(),this);
		th = new JsonTuple1Handler<TS>(fbTs.getClassTs());
	}
	
	protected void postConstructSummary(JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs)
	{
		super.postConstructTs(bTranslation,bMessage,fTs);
		sbhClass.update(fTs.all(fbTs.getClassEntity()));
		reloadBridges();
	}
	
	@Override
	public void selectSbSingle(EjbWithId item) throws JeeslLockingException, JeeslConstraintViolationException
	{
		reloadBridges();
	}
	
	public void cancelScope(){reset(true,true);}
	public void cancelMultiPoint(){reset(false,true);}
	public void reset(boolean rScope, boolean rMultiPoint)
	{
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void reloadBridges()
	{
		bridges = fTs.allForParent(fbTs.getClassBridge(),sbhClass.getSelection());
		
		mapBridge.clear();
		mapTs.clear();
		
		try
		{
			Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(sbhClass.getSelection().getCode()).asSubclass(EjbWithId.class);
			List<EjbWithId> list = fTs.find(c, efBridge.toRefIds(bridges));
			mapBridge.putAll(EjbIdFactory.toIdMap(list));
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		
		series = fTs.fTimeSeries(bridges);
		mapTs.putAll(efTs.toMapBridgTsList(series));
		th.init(fTs.tpCountRecordsByTs(series));
		if(debugOnInfo){logger.info("reloadBridges Bridges:"+bridges.size()+" TS:"+series.size());}
		
	}
	
	public void select() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(ts));
	}
}