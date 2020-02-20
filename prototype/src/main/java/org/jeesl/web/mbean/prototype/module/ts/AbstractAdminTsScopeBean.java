package org.jeesl.web.mbean.prototype.module.ts;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsInterval;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsUnit;
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
import org.jeesl.interfaces.web.JeeslJsfSecurityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminTsScopeBean <L extends JeeslLang, D extends JeeslDescription,LOC extends JeeslStatus<LOC,L,D>,
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
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTsScopeBean.class);

	protected List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	protected List<UNIT> units; public List<UNIT> getUnits() {return units;}
	protected List<ST> scopeTypes; public List<ST> getScopeTypes() {return scopeTypes;}
	protected List<MP> multiPoints; public List<MP> getMultiPoints() {return multiPoints;}
	protected List<INT> opIntervals; public List<INT> getOpIntervals() {return opIntervals;}
	protected List<EC> opClasses; public List<EC> getOpClasses() {return opClasses;}
	
	protected SCOPE scope; public void setScope(SCOPE scope) {this.scope = scope;} public SCOPE getScope() {return scope;}
	private MP multiPoint; public MP getMultiPoint() {return multiPoint;} public void setMultiPoint(MP multiPoint) {this.multiPoint = multiPoint;}
	protected INT opInterval;public INT getOpInterval(){return opInterval;}public void setOpInterval(INT opInterval){this.opInterval = opInterval;}
	protected INT tbInterval;public INT getTbInterval(){return tbInterval;}public void setTbInterval(INT tbInterval){this.tbInterval = tbInterval;}	
	
	protected EC opClass;public EC getOpClass() {return opClass;}public void setOpClass(EC opClass) {this.opClass = opClass;}
	protected EC tbClass;public EC getTbClass() {return tbClass;}public void setTbClass(EC tbClass) {this.tbClass = tbClass;}
	
	public AbstractAdminTsScopeBean(final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs)
	{
		super(fbTs);
	}
	
	protected void postConstructScope(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fTs, JeeslFacesMessageBean bMessage)
	{
		super.postConstructTs(bTranslation,bMessage,fTs);
		initLists();
		reloadScopes();
	}
	
	private void initLists()
	{
		units = fTs.all(fbTs.getClassUnit());
		scopeTypes = fTs.all(fbTs.getClassScopeType());
		opIntervals = fTs.all(fbTs.getClassInterval());
		opClasses = fTs.all(fbTs.getClassEntity());
	}
	
	@Override public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		super.toggled(c);
		if(fbTs.getClassCategory().isAssignableFrom(c)){reloadScopes();reset(true,true);}
	}
	
	public void cancelScope(){reset(true,true);}
	public void cancelMultiPoint(){reset(false,true);}
	public void reset(boolean rScope, boolean rMultiPoint)
	{
		if(rScope) {scope = null;}
		if(rMultiPoint) {multiPoint = null;}
	}
	
	protected void updatePerformed(){}
	
	public void reloadScopes()
	{
		if(debugOnInfo){logger.info("reloadScopes");}
		scopes = fTs.findScopes(fbTs.getClassScope(),fbTs.getClassCategory(), sbhCategory.getSelected(), uiShowInvisible);
		Collections.sort(scopes, comparatorScope);
	}
	
	public void add() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.addEntity(fbTs.getClassScope()));
		scope = efScope.build(null);
		scope.setName(efLang.createEmpty(localeCodes));
		scope.setDescription(efDescription.createEmpty(localeCodes));
		scope.setType(fTs.fByCode(fbTs.getClassScopeType(), JeeslTsScopeType.Code.ts));
		scope.setUnit(fTs.fByCode(fbTs.getClassUnit(), JeeslTsUnit.Code.event));
	}
	
	public void select() throws JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.selectEntity(scope));
		scope = fTs.find(fbTs.getClassScope(), scope);
		scope = efLang.persistMissingLangs(fTs,localeCodes,scope);
		scope = efDescription.persistMissingLangs(fTs,localeCodes,scope);
		reloadMultiPoints();
	}
	
	public void save() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(scope));
		
		if(scope.getType()!=null) {scope.setType(fTs.find(fbTs.getClassScopeType(), scope.getType()));}
		if(scope.getUnit()!=null) {scope.setUnit(fTs.find(fbTs.getClassUnit(), scope.getUnit()));}
		scope.setCategory(fTs.find(fbTs.getClassCategory(), scope.getCategory()));
		
		scope = fTs.save(scope);
		reloadScopes();
		reloadMultiPoints();
		updatePerformed();
	}
	
	public void changeScopeType() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.selectOneMenuChange(scope.getType()));
		if(scope.getType()!=null) {scope.setType(fTs.find(fbTs.getClassScopeType(), scope.getType()));}
	}
	
	public void rm() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(scope));
		fTs.rm(scope);
		reset(true,true);
		reloadScopes();
	}
	
	//OverlayPanel Interval
	public void opAddInterval() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectOverlayPanel(opInterval));}
		
		if(!scope.getIntervals().contains(opInterval))
		{
			scope.getIntervals().add(opInterval);
			scope = fTs.save(scope);
			opInterval = null;
			select();
		}
	}
	public void opRmInterval() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(tbInterval!=null && scope.getIntervals().contains(tbInterval))
		{
			scope.getIntervals().remove(tbInterval);
			scope = fTs.save(scope);
			tbInterval = null;
			select();
		}
	}
	public void selectTbInterval()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(tbInterval));}
	}
	
	//OverlayPanel Class
	public void opAddClass() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectOverlayPanel(opClass));}
		
		if(!scope.getClasses().contains(opClass))
		{
			scope.getClasses().add(opClass);
			scope = fTs.save(scope);
			opInterval = null;
			select();
		}
	}
	public void opRmClass() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(tbClass!=null && scope.getClasses().contains(tbClass))
		{
			scope.getClasses().remove(tbClass);
			scope = fTs.save(scope);
			tbClass = null;
			select();
		}
	}
	public void selectTbClass()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(tbClass));}
	}
	
	private void reloadMultiPoints()
	{
		multiPoints = fTs.allForParent(fbTs.getClassMp(),scope);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbTs.getClassMp(), multiPoints, scope));}
	}
	
	public void addMultiPoint()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTs.getClassMp()));}
		multiPoint = fbTs.ejbMultiPoint().build(scope, multiPoints);
		multiPoint.setName(efLang.createEmpty(localeCodes));
		multiPoint.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void selectMultiPoint() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(multiPoint));}
		multiPoint = fTs.find(fbTs.getClassMp(), multiPoint);
		multiPoint = efLang.persistMissingLangs(fTs,localeCodes,multiPoint);
		multiPoint = efDescription.persistMissingLangs(fTs,localeCodes,multiPoint);
	}
	
	public void saveMultiPoint() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(multiPoint));}
		multiPoint.setUnit(fTs.find(fbTs.getClassUnit(),multiPoint.getUnit()));
		multiPoint = fTs.save(multiPoint);
		reloadMultiPoints();
	}
	
	@Override protected void updateSecurity2(JeeslJsfSecurityHandler jsfSecurityHandler, String viewCode)
	{
		uiShowInvisible= jsfSecurityHandler.allow(viewCode);

		if(logger.isTraceEnabled())
		{
			logger.info(uiShowInvisible+" showInvisible a:"+viewCode);
		}
	}
	
	public void reorderScopes() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fTs, fbTs.getClassScope(), scopes);Collections.sort(scopes, comparatorScope);}
	public void reorderMultiPoints() throws JeeslConstraintViolationException, JeeslLockingException {PositionListReorderer.reorder(fTs, fbTs.getClassMp(), multiPoints);}

}