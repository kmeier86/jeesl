package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.factory.mc.ts.McTsViewerFactory;
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
import org.jeesl.model.xml.module.ts.Data;
import org.jeesl.model.xml.module.ts.TimeSeries;
import org.jeesl.util.comparator.xml.ts.TsDataComparator;
import org.metachart.xml.chart.Ds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractAdminTsImportManualBean<L extends JeeslLang, D extends JeeslDescription,
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
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsImportManualBean.class);

	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	private List<EC> classes; public List<EC> getClasses() {return classes;}
	private List<INT> intervals; public List<INT> getIntervals() {return intervals;}
	private List<WS> workspaces; public List<WS> getWorkspaces() {return workspaces;}
	private final List<SOURCE> sources; public List<SOURCE> getSources() {return sources;}

	private final List<EjbWithId> entities; public List<EjbWithId> getEntities() {return entities;}
	private Map<EjbWithId,String> mapLabels; public Map<EjbWithId,String> getMapLabels() {return mapLabels;}
	private EjbWithId entity; public EjbWithId getEntity() {return entity;} public void setEntity(EjbWithId entity) {this.entity = entity;}
	private EjbTsDataFactory<TS,TRANSACTION, DATA,WS> efData;

	protected CAT category;public CAT getCategory() {return category;}public void setCategory(CAT category) {this.category = category;}
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private EC clas; public EC getClas() {return clas;} public void setClas(EC clas) {this.clas = clas;}
	private INT interval; public INT getInterval() {return interval;} public void setInterval(INT interval) {this.interval = interval;}
	protected WS workspace; public WS getWorkspace() {return workspace;} public void setWorkspace(WS workspace) {this.workspace = workspace;}
	protected BRIDGE bridge; public BRIDGE getBridge() {return bridge;}
	private TRANSACTION transaction; public TRANSACTION getTransaction() {return transaction;} public void setTransaction(TRANSACTION transaction) {this.transaction = transaction;}
	private TS ts;
	private USER transactionUser;
	
	private TimeSeries timeSeries; public TimeSeries getTimeSeries() {return timeSeries;} public void setTimeSeries(TimeSeries timeSeries) {this.timeSeries = timeSeries;}
	private final Map<TS,EjbWithId> mapTsEntity; public Map<TS,EjbWithId> getMapTsEntity() {return mapTsEntity;}

	private Ds ds; public Ds getDs() {return ds;} public void setDs(Ds ds) {this.ds = ds;}

	private Comparator<Data> cTsData;
	
	private List<DATA> datas; public List<DATA> getDatas() {return datas;}
	private DATA data; public DATA getData() { return data; } public void setData(DATA data) { this.data = data; }

	public AbstractAdminTsImportManualBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs)
	{
		super(fbTs);
		entities = new ArrayList<EjbWithId>();
		mapTsEntity = new HashMap<TS,EjbWithId>();
		efData = fbTs.data();
		sources = new ArrayList<>();
	}

	protected void postConstructTsManual(JeeslTranslationBean<L,D,?> bTranslation,
								JeeslFacesMessageBean bMessage, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs,
								USER transactionUser)
	{
		super.postConstructTs(bTranslation,bMessage,fTs);
		this.transactionUser=transactionUser;
		cTsData = TsDataComparator.factory(TsDataComparator.Type.date);
		sources.addAll(fTs.all(fbTs.getClassSource()));
		
		initTransaction();
	}

	protected void initLists()
	{
		workspaces = fTs.all(fbTs.getClassWorkspace());
		category = null; if(categories.size()>0){category = categories.get(0);}
		changeCategory();
	}
	
	public void cancelData(){reset(false,true);}
	private void reset(boolean rTransaction, boolean rData)
	{
		if(rTransaction) {transaction = null;}
		if(rData) {data = null;}
		
	}

	public void changeCategory()
	{
		scope=null;
		clas=null;
		interval=null;
		if(category!=null)
		{
			category = fTs.find(fbTs.getClassCategory(), category);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(category));}
			scopes = fTs.allOrderedPositionVisibleParent(fbTs.getClassScope(), category);
			if(scopes.size()>0){scope=scopes.get(0);}
			logger.info(category.toString());

			changeScope();
		}
	}

	private void changeScope()
	{
		clas=null;
		interval=null;
		if(scope!=null)
		{
			scope = fTs.find(fbTs.getClassScope(), scope);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(scope));}

			classes = scope.getClasses();
			if(classes.size()>0){clas=classes.get(0);}

			intervals = scope.getIntervals();
			if(intervals.size()>0){interval=intervals.get(0);}
			logger.info(scope.toString());
			changeClass();
			changeInterval();
		}
	}

	private void changeClass()
	{
		if(clas!=null)
		{
			clas = fTs.find(fbTs.getClassEntity(), clas);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(clas));}
			changeInterval();
		}
	}

	public void changeInterval()
	{
		if(interval!=null)
		{
			interval = fTs.find(fbTs.getClassInterval(), interval);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(interval));}
			if(intervals.size()>0){interval=intervals.get(0);}
			reloadBridges();
		}
	}

	private void reloadBridges()
	{
		entities.clear();
		try
		{
			mapTsEntity.clear();
			Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(clas.getCode()).asSubclass(EjbWithId.class);
			entities.addAll(fTs.all(c));

			if(entities.size() > 0) {bridge = fTs.fcBridge(fbTs.getClassBridge(), clas, entities.get(0));}
		}
		catch (ClassNotFoundException e){e.printStackTrace();} catch(JeeslConstraintViolationException e) { e.printStackTrace(); }
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(EjbWithId.class,entities));}
	}
	
	public void initTransaction()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTs.getClassData()));}
		uiAllowAdd=false;
		uiAllowSave=false;
		transaction = fbTs.transaction().build(transactionUser, sources.get(0));
		transaction.setRecord(new Date());
	}
	
	public void saveTransaction() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTs.getClassData()));}
		transaction.setSource(fTs.find(fbTs.getClassSource(),transaction.getSource()));
		transaction = fTs.save(transaction);
		uiAllowAdd=true;
		uiAllowSave=true;
	}

	public void selectEntity() throws JeeslConstraintViolationException
	{
		logger.info("Selected: "+ entity.toString());
		reset(false,true);
		ts = fTs.fcTimeSeries(scope, interval,fTs.fcBridge(fbTs.getClassBridge(), clas, entity));
		reloadData();
	}
	
	private void reloadData()
	{
		datas = fTs.fData(workspace, ts);

		McTsViewerFactory<TS,DATA> f = new McTsViewerFactory<TS,DATA>();
		ds=f.build2(datas);
		JaxbUtil.info(ds);
	}

	public void selectData()
	{
		logger.info(AbstractLogMessage.selectEntity(data));

		date = data.getRecord();
		value = data.getValue();
		logger.info("Existing data found: " + date + " / " + value);
	}

	public void addData()
	{
		reset(false,true);
		data = efData.build(workspace, ts, null, null, null);
	}

	private Date date; public Date getDate() { return date; } public void setDate(Date date) { this.date = date; }
	private Double value; public Double getValue() { return value; } public void setValue(Double value) { this.value = value; }

	public void saveData() throws JeeslConstraintViolationException, JeeslLockingException
	{
		data.setTransaction(transaction);
		data.setRecord(date);
		data.setValue(value);
		logger.info(AbstractLogMessage.saveEntity(data));
		data = fTs.save(data);
		logger.info(AbstractLogMessage.savedEntity(data));
		reloadData();
	}
}