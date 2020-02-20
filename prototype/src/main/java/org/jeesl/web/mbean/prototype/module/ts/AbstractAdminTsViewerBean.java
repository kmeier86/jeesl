package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.handler.op.OpEntitySelectionHandler;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.mc.ts.McTsViewerFactory;
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
import org.metachart.xml.chart.Ds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractAdminTsViewerBean <L extends JeeslLang, D extends JeeslDescription,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsViewerBean.class);

	private final OpEntitySelectionHandler<TS> tsh; public OpEntitySelectionHandler<TS> getTsh() {return tsh;}
	private final Map<TS,EjbWithId> mapTsEntity; public Map<TS,EjbWithId> getMapTsEntity() {return mapTsEntity;}
	
	protected final SbSingleHandler<SCOPE> sbhScope; public SbSingleHandler<SCOPE> getSbhScope() {return sbhScope;}
	protected final SbSingleHandler<EC> sbhClass; public SbSingleHandler<EC> getSbhClass() {return sbhClass;}
	protected final SbSingleHandler<INT> sbhInterval; public SbSingleHandler<INT> getSbhInterval() {return sbhInterval;}
	
	public AbstractAdminTsViewerBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs)
	{
		super(fbTs);
		sbhScope = new SbSingleHandler<SCOPE>(fbTs.getClassScope(),this);
		sbhClass = new SbSingleHandler<EC>(fbTs.getClassEntity(),this);
		sbhInterval = new SbSingleHandler<INT>(fbTs.getClassInterval(),this);
		
		tsh = new OpEntitySelectionHandler<TS>(null);
		mapTsEntity = new HashMap<TS,EjbWithId>();
	}
	
	protected void initSuper(JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs)
	{
		super.postConstructTs(bTranslation,bMessage,fTs);
		sbhCategory.toggleAll();
	}
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		super.toggled(c);
		if(fbTs.getClassCategory().isAssignableFrom(c))
		{
			sbhScope.setList(fTs.findScopes(fbTs.getClassScope(), fbTs.getClassCategory(), sbhCategory.getSelected(), uiShowInvisible));
			Collections.sort(sbhScope.getList(), comparatorScope);
			if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbTs.getClassScope(),sbhScope.getList()));}
			sbhScope.silentCallback();
		}
	}
	
	@Override
	public void selectSbSingle(EjbWithId item)
	{
		if(fbTs.getClassScope().isAssignableFrom(item.getClass()))
		{
			sbhClass.clear();sbhInterval.clear();
			
			sbhClass.setList(sbhScope.getSelection().getClasses());
			if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbTs.getClassEntity(),sbhClass.getList()));}
			sbhClass.setDefault();sbhClass.silentCallback();
			
			sbhInterval.setList(sbhScope.getSelection().getIntervals());
			if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbTs.getClassInterval(),sbhInterval.getList()));}
			sbhInterval.setDefault();sbhInterval.silentCallback();
		}
		else if(fbTs.getClassEntity().isAssignableFrom(item.getClass())) {if(sbhClass.isSelected() && sbhInterval.isSelected()) {reloadBridges();}}
		else if(fbTs.getClassInterval().isAssignableFrom(item.getClass())) {if(sbhClass.isSelected() && sbhInterval.isSelected()) {reloadBridges();}}
	}
	
	private void reloadBridges()
	{
		tsh.setTbList(fTs.fTimeSeries(sbhScope.getSelection(), sbhInterval.getSelection(), sbhClass.getSelection()));
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbTs.getClassTs(),tsh.getTbList()));}
		
		try
		{
			mapTsEntity.clear();
			Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(sbhClass.getSelection().getCode()).asSubclass(EjbWithId.class);
			Map<Long,TS> mapBridgeTs = efTs.toMapBridgeRefIdTs(tsh.getTbList());
			for(EjbWithId ejb : fTs.find(c,efTs.toBridgeIds(tsh.getTbList())))
			{
				mapTsEntity.put(mapBridgeTs.get(ejb.getId()),ejb);
			}
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectTimeseries()
	{
		logger.info("Selected: "+tsh.getOpList().size());
		List<DATA> list = fTs.fData(sbhWorkspace.getSelected().get(0), tsh.getOpList().get(0));
		
		McTsViewerFactory<TS,DATA> f = new McTsViewerFactory<TS,DATA>();
		ds=f.build2(list);
		JaxbUtil.info(ds);
	}
	
	Ds ds; public Ds getDs() {return ds;} public void setDs(Ds ds) {this.ds = ds;}
}