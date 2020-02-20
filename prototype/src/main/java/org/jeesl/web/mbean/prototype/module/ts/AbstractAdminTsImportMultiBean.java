package org.jeesl.web.mbean.prototype.module.ts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.controller.ImportStrategy;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.mc.ts.McTimeSeriesFactory;
import org.jeesl.factory.xml.module.ts.XmlDataFactory;
import org.jeesl.factory.xml.module.ts.XmlTsFactory;
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
import org.joda.time.DateTime;
import org.metachart.xml.chart.Ds;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.controller.report.UtilsXlsDefinitionResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.report.revert.excel.importers.ExcelSimpleSerializableImporter;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsImportMultiBean <L extends JeeslLang, D extends JeeslDescription,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsImportMultiBean.class);
	
	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	private List<EC> classes; public List<EC> getClasses() {return classes;}
	protected List<INT> intervals; public List<INT> getIntervals() {return intervals;}
	private List<WS> workspaces; public List<WS> getWorkspaces() {return workspaces;}
	private List<SOURCE> sources; public List<SOURCE> getSources() {return sources;}
	
	private List<EjbWithId> entities; public List<EjbWithId> getEntities() {return entities;}
	private Map<EjbWithId,String> mapLabels; public Map<EjbWithId,String> getMapLabels() {return mapLabels;}
	private EjbWithId entity; public EjbWithId getEntity() {return entity;} public void setEntity(EjbWithId entity) {this.entity = entity;}
	
	private CAT category;public CAT getCategory() {return category;}public void setCategory(CAT category) {this.category = category;}
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private EC clas; public EC getClas() {return clas;} public void setClas(EC clas) {this.clas = clas;}
	protected INT interval; public INT getInterval() {return interval;} public void setInterval(INT interval) {this.interval = interval;}
	private WS workspace; public WS getWorkspace() {return workspace;} public void setWorkspace(WS workspace) {this.workspace = workspace;}
	protected USER transactionUser;
	private TRANSACTION transaction; public TRANSACTION getTransaction() {return transaction;} public void setTransaction(TRANSACTION transaction) {this.transaction = transaction;}
	
	private TimeSeries timeSeries; public TimeSeries getTimeSeries() {return timeSeries;} public void setTimeSeries(TimeSeries timeSeries) {this.timeSeries = timeSeries;}
	private Ds chartDs; public Ds getChartDs(){return chartDs;}
	
	protected UtilsXlsDefinitionResolver xlsResolver;
	protected File importRoot;
	
	private Comparator<Data> cTsData;
        
	protected HashMap<SCOPE, Map<DATA, ArrayList<String>>> timeSeriesMap;
	public HashMap<SCOPE, Map<DATA, ArrayList<String>>> getTimeSeriesMap() {return timeSeriesMap;}
	public void setTimeSeriesMap(HashMap<SCOPE, Map<DATA, ArrayList<String>>> timeSeriesMap) {this.timeSeriesMap = timeSeriesMap;}
	
	protected String successMessage; public String getSuccessMessage() {return successMessage;} public void setSuccessMessage(String successMessage) {this.successMessage = successMessage;}
	protected boolean saved; public boolean isSaved() {return saved;} public void setSaved(boolean saved) {this.saved = saved;}
	
	private List<DATA> dataList; public List<DATA> getDataList() {return dataList;} public void setDataList(List<DATA> dataList) {this.dataList = dataList;}
	
	public AbstractAdminTsImportMultiBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs) {super(fbTs);}
	
	protected void initSuper(JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs,
//			String[] langs, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs, JeeslFacesMessageBean bMessage,
			UtilsXlsDefinitionResolver xlsResolver)
	{
		super.postConstructTs(bTranslation,bMessage,fTs);
		this.xlsResolver=xlsResolver;
		
		cTsData = TsDataComparator.factory(TsDataComparator.Type.date);
		sources = fTs.all(fbTs.getClassSource());
	}
	
	protected void initLists()
	{
		workspaces = fTs.all(fbTs.getClassWorkspace());
		category = null; if(categories.size()>0){category = categories.get(0);}
		changeCategory();
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
			changeScope();
		}
	}
	
	public void changeScope()
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
		}
	}
	
	public void changeClass()
	{
		if(clas!=null)
		{
			clas = fTs.find(fbTs.getClassEntity(), clas);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(clas));}
		}
	}
	
	public void changeInterval()
	{
		if(interval!=null)
		{
			interval = fTs.find(fbTs.getClassInterval(), interval);
			if(debugOnInfo){logger.info(AbstractLogMessage.selectOneMenuChange(interval));}
		}
	}
	
	/**
	 * Import Excel time series to XML objects. 
	 * Excel file is stored locally, then loaded into a Apache POI object.
	 * Then AHTUtils ExcelImporter system is configured (what information is to be put where) and used to import Excel data linewise to XML object representation.
	 * @param event The PrimeFaces FileUpload event that contains the uploaded data and meta information
	 * @throws java.io.FileNotFoundException
	 */	
	public void uploadData(FileUploadEvent event) throws FileNotFoundException, IOException, ClassNotFoundException, Exception
	{
		// Store the uploaded data locally (is overwritten without asking for files with the same name) 
		// and save the filename for use in log messages when saving to database
		String filename = event.getFile().getFileName();
		File f = new File(importRoot,filename);
		FileOutputStream out = new FileOutputStream(f);
		IOUtils.copy(event.getFile().getInputstream(), out);
		
		// Create a new TimeSeries
		timeSeries = XmlTsFactory.buildOld();
		
		// Get a new Resolver that gives you the XlsWorkbook by asking the reports.xml registry for the file
		
		// Instantiate and configure the importer
		// ATTENTION: Do not use the primary key option here (would cause bad results)
		ExcelSimpleSerializableImporter<DATA,ImportStrategy> statusImporter = ExcelSimpleSerializableImporter.factory(xlsResolver, "TimeSeries", f.getAbsolutePath());
		statusImporter.selectFirstSheet();
		statusImporter.setFacade(fTs);
		statusImporter.getTempPropertyStore().put("createEntityForUnknown", true);
		statusImporter.getTempPropertyStore().put("lookup", false);
		  
		Map<DATA,ArrayList<String>> data  = statusImporter.execute(true);
		
		if(debugOnInfo){logger.info("Loaded " +data.size() +" time series data entries to be saved in the database.");}
		//timeSeries.getData().addAll(data.keySet());
                dataList = new ArrayList<DATA>();
                
                for (DATA dataItem : data.keySet())
                {
                    dataList.add(dataItem);
                }
		Collections.sort(timeSeries.getData(), cTsData);
		entity=null;
		transaction = efTransaction.build(transactionUser,sources.get(0));
                logger.info("Imported set with " +dataList.size() +" entries.");
		preview();
	}
	
	public void random()
	{
		DateTime dt = new DateTime(new Date());
		Random rnd = new Random();
		
		timeSeries = XmlTsFactory.buildOld();
		for(int i=0;i<5;i++)
		{
			timeSeries.getData().add(XmlDataFactory.build(dt.plusDays(i).toDate(), rnd.nextInt(10)*rnd.nextDouble()));
		}
		
		entity=null;
		preview();
	}
	
	@SuppressWarnings("unchecked")
	private void preview()
	{
		entities = new ArrayList<EjbWithId>();
		mapLabels = new HashMap<EjbWithId,String>();
		
		chartDs = McTimeSeriesFactory.build2(timeSeries);
		try
		{
			Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(clas.getCode()).asSubclass(EjbWithId.class);
			
			for(EjbWithId e : fTs.all(c))
			{
				entities.add(e);
				JXPathContext ctx = JXPathContext.newContext(e);
				mapLabels.put(e, (String)ctx.getValue(clas.getXpath()));
			}
		}
		catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	public void selectEntity()
	{
		logger.info(AbstractLogMessage.selectEntity(entity));
	}
	
	public void importData()
	{
		workspace = fTs.find(fbTs.getClassWorkspace(), workspace);
		logger.info("Import Data to "+workspace);
		
		try
		{
			BRIDGE bridge = fTs.fcBridge(fbTs.getClassBridge(), clas, entity);
			TS ts = fTs.fcTimeSeries(scope, interval, bridge);
			logger.info("Using TS "+ts.toString());
			
			if(transaction.getSource()!=null){transaction.setSource(fTs.find(fbTs.getClassSource(),transaction.getSource()));}
			transaction.setRecord(new Date());
			transaction = fTs.save(transaction);
			
			List<DATA> datas = new ArrayList<DATA>();
			for(Data data : timeSeries.getData())
			{
				datas.add(efData.build(workspace,ts,transaction,data));
			}
			fTs.save(datas);
			
			entities=null;
			entity=null;
			timeSeries=null;
			chartDs=null;
		}
		catch (JeeslConstraintViolationException e) {e.printStackTrace();}
		catch (JeeslLockingException e) {e.printStackTrace();}
	}
        
        public void importDataList()
        {
            workspace = fTs.find(fbTs.getClassWorkspace(), workspace);
            logger.info("Import Data to "+workspace);

            try
            {
                    
                    if(transaction.getSource()!=null){transaction.setSource(fTs.find(fbTs.getClassSource(),transaction.getSource()));}
                    transaction.setRecord(new Date());
                    transaction = fTs.save(transaction);

                    for(DATA data : dataList)
                    {
                        data.setWorkspace(workspace);
                        data.setTransaction(transaction);
                        if (logger.isTraceEnabled()) {logger.warn("Saving data point for reference ID " +data.getTimeSeries().getBridge().getRefId()+ " in transaction source " +transaction.getSource().getName().get("en"));}
                        //logger.info("ID was " +data.getId());
						data.setId(0);
                        fTs.save(data);
						//logger.info("ID is set to " +data.getId());
						
                    }

                    entities=null;
                    entity=null;
                    timeSeries=null;
                    chartDs=null;
                    
                    successMessage = "Imported set with " +getDataList().size() +" entries.";
                    logger.info(successMessage);
                    logger.info("Reseting list");
                    setSaved(true);
                    setDataList(new ArrayList<DATA>());
                    timeSeriesMap.clear();
            }
            catch (JeeslConstraintViolationException e) {e.printStackTrace();}
            catch (JeeslLockingException e) {e.printStackTrace();}
        }
	
	public String getValidationInfo(SCOPE scope, DATA dataPoint)
	{
	    ArrayList<String> validationErrors = timeSeriesMap.get(scope).get(dataPoint);
	    if (validationErrors.size()>0)
	    {
		StringBuilder sb = new StringBuilder();
		sb.append("Please check row ");
		String rowNr = validationErrors.get(0);
		sb.append(rowNr.substring(0, rowNr.indexOf("-")));
		sb.append(" for the following data: ");
		for (String failedDataField : validationErrors)
		{
		    sb.append(failedDataField.substring(failedDataField.indexOf("-")+1));
		}
		return sb.toString();
	    }
	    else
	    {
		return null;
	    }
	}
}