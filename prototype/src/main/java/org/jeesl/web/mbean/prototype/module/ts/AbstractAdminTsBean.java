package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsBridgeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsClassFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsCronFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsDataFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsScopeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsTransactionFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
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
import org.jeesl.util.comparator.ejb.module.ts.TsClassComparator;
import org.jeesl.util.comparator.ejb.module.ts.TsScopeComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractAdminTsBean <L extends JeeslLang, D extends JeeslDescription,
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
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsBean.class);
	
	protected JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs;
	protected final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs;
	
	protected List<CAT> categories; public List<CAT> getCategories() {return categories;}
	
	protected final EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efTs;
	protected EjbTsScopeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efScope;
	protected EjbTsClassFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efClass;
	protected EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efBridge;
	protected EjbTsDataFactory<TS,TRANSACTION,DATA,WS> efData;
	protected EjbTsTransactionFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efTransaction;
	protected EjbTsCronFactory<SCOPE,INT,STAT,CRON> efCron;
	
	protected Comparator<SCOPE> comparatorScope;
	protected Comparator<EC> comparatorClass;

	protected final SbMultiHandler<WS> sbhWorkspace; public SbMultiHandler<WS> getSbhWorkspace() {return sbhWorkspace;}
	protected final SbMultiHandler<CAT> sbhCategory; public SbMultiHandler<CAT> getSbhCategory() {return sbhCategory;}
	
	public AbstractAdminTsBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs)
	{
		super(fbTs.getClassL(),fbTs.getClassD());
		this.fbTs=fbTs;
		
		efTs = fbTs.ts();
		
		sbhCategory = new SbMultiHandler<CAT>(fbTs.getClassCategory(),this);
		sbhWorkspace = new SbMultiHandler<WS>(fbTs.getClassWorkspace(),this);
	}
	
	protected void postConstructTs(JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fTs=fTs;
		
		comparatorScope = (new TsScopeComparator<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>()).factory(TsScopeComparator.Type.position);
		comparatorClass = (new TsClassComparator<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>()).factory(TsClassComparator.Type.position);
		
		efScope = fbTs.scope();
		efTransaction = fbTs.transaction();
		efClass = fbTs.entityClass();
		efData = fbTs.data();
		efBridge = fbTs.bridge();
		efCron = fbTs.ejbCron();
		
		categories = fTs.allOrderedPositionVisible(fbTs.getClassCategory());
		
		sbhCategory.fillAndSelect(fTs.allOrderedPositionVisible(fbTs.getClassCategory()));
		sbhWorkspace.fillAndSelect(fTs.allOrderedPositionVisible(fbTs.getClassWorkspace()));
		
		if(debugOnInfo)
		{
			logger.info(fbTs.getClassCategory().getSimpleName()+" "+sbhCategory.getSelected().size()+"/"+sbhCategory.getList().size());
			logger.info(fbTs.getClassWorkspace().getSimpleName()+" "+sbhWorkspace.getSelected().size()+"/"+sbhWorkspace.getList().size());
		}
	}
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{

	}
}