package org.jeesl.web.mbean.prototype.module.ts;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.api.handler.sb.SbDateIntervalSelection;
import org.jeesl.controller.handler.sb.SbDateHandler;
import org.jeesl.controller.handler.ui.helper.CodeConfirmationHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsTransactionBean <L extends JeeslLang, D extends JeeslDescription,
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
					implements Serializable,SbDateIntervalSelection
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsTransactionBean.class);
	
	private SbDateHandler sbDateHandler; public SbDateHandler getSbDateHandler() {return sbDateHandler;}
	
	private Map<EC,Map<Long,EjbWithId>> map; public Map<EC, Map<Long, EjbWithId>> getMap() {return map;}
	private List<TRANSACTION> transactions; public List<TRANSACTION> getTransactions() {return transactions;}
	private List<DATA> datas; public List<DATA> getDatas() {return datas;}
	
	private TRANSACTION transaction; public TRANSACTION getTransaction() {return transaction;} public void setTransaction(TRANSACTION transaction) {this.transaction = transaction;}

	public CodeConfirmationHandler getCch() { return cch; }

	private CodeConfirmationHandler cch;

	public AbstractAdminTsTransactionBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs)
	{
		super(fbTs);
		sbDateHandler = new SbDateHandler(this);
		sbDateHandler.setEnforceStartOfDay(true);
		sbDateHandler.initMonthsToNow(2);
	}
	
	protected void initSuper(JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs)
	{
		super.postConstructTs(bTranslation,bMessage,fTs);
		cch = new CodeConfirmationHandler();
		reloadTransactions();
	}
	
	@Override public void callbackDateChanged()
	{
		reloadTransactions();
	}
	
	private void reloadTransactions()
	{
		transactions = fTs.fTransactions(null,sbDateHandler.getDate1(),sbDateHandler.toDate2Plus1());
	}
	
	public void selectTransaction()
	{
		logger.info(AbstractLogMessage.selectEntity(transaction));
		datas = fTs.fData(transaction);
		
		map = new HashMap<EC,Map<Long,EjbWithId>>();
		Map<EC,List<Long>> x = efBridge.dataToBridgeIds(datas);
		for(EC ec : x.keySet())
		{
			try
			{
				@SuppressWarnings("unchecked")
				Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(ec.getCode()).asSubclass(EjbWithId.class);
				Map<Long,EjbWithId> m = new HashMap<Long,EjbWithId>();
				List<EjbWithId> list = fTs.find(c, x.get(ec));
				for(EjbWithId e : list)
				{
					m.put(e.getId(),e);
				}
				map.put(ec,m);
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
		}
	}
	
	public void deleteTransaction() throws JeeslConstraintViolationException
	{
		if(cch.isCodeConfirmed())
		{
			logger.info(AbstractLogMessage.rmEntity(transaction));
			fTs.deleteTransaction(transaction);
			transaction = null;
			reloadTransactions();
		}
	}
	
	
	public void purgeAllTransactions() throws JeeslConstraintViolationException
	{
		// This is strictly only to be used in DEVELOPMENT ENVIRONMENTS!!!!
		Path token =  Paths.get(System.getProperty("user.home") +File.separator +"devModeActivator.token");
		if(Files.exists(token))
		{
			logger.info("In DEV MODE - PURGING ALL TRANSACTIONs");
			for (TRANSACTION transactionToDelete : fTs.fTransactions(null,null,null))
			{
				logger.info(AbstractLogMessage.rmEntity(transactionToDelete));
				fTs.deleteTransaction(transactionToDelete);
			}
		}
		transaction = null;
		reloadTransactions();
	}
	
}