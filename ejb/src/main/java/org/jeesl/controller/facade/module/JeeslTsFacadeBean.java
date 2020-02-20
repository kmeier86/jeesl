package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslTsFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.ejb.module.ts.EjbTsBridgeFactory;
import org.jeesl.factory.ejb.module.ts.EjbTsFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.json.db.tuple.t1.Json1TuplesFactory;
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
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslTsFacadeBean<L extends JeeslLang, D extends JeeslDescription,
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
					extends JeeslFacadeBean
					implements JeeslTsFacade<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON>
{
	private static final long serialVersionUID = 1L;

	private final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs;
	
	private final EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efTs;
	private EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF> efBridge;
	
	public JeeslTsFacadeBean(EntityManager em, final TsFactoryBuilder<L,D,CAT,SCOPE,ST,UNIT,MP,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,STAT,DATA,POINT,SAMPLE,USER,WS,QAF,CRON> fbTs)
	{
		super(em);
		this.fbTs=fbTs;

		efTs = new EjbTsFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(fbTs.getClassTs());
	}
	
	@Override public List<SCOPE> findScopes(Class<SCOPE> cScope, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleScopes)
	{
		List<ParentPredicate<CAT>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cScope,ppCategory);
	}
	
	@Override public List<EC> findClasses(Class<EC> cClass, Class<CAT> cCategory, List<CAT> categories, boolean showInvisibleScopes)
	{
		List<ParentPredicate<CAT>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cClass,ppCategory);
	}
	
	@Override public <T extends EjbWithId> BRIDGE fcBridge(Class<BRIDGE> cBridge, EC entityClass, T ejb) throws JeeslConstraintViolationException
	{
		try {return fBridge(entityClass, ejb);}
		catch (JeeslNotFoundException ex)
		{
			if(efBridge==null){efBridge = new EjbTsBridgeFactory<L,D,CAT,SCOPE,UNIT,TS,TRANSACTION,SOURCE,BRIDGE,EC,INT,DATA,SAMPLE,USER,WS,QAF>(cBridge);}
			BRIDGE bridge = efBridge.build(entityClass, ejb.getId());
			return this.persist(bridge);
		}
	}
	@Override public <T extends EjbWithId> BRIDGE fBridge(EC ec, T ejb) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<BRIDGE> cQ = cB.createQuery(fbTs.getClassBridge());
		Root<BRIDGE> from = cQ.from(fbTs.getClassBridge());
		
		Path<EC> pClass = from.get(JeeslTsBridge.Attributes.entityClass.toString());
		Path<Long> pRef = from.get(JeeslTsBridge.Attributes.refId.toString());
		
		CriteriaQuery<BRIDGE> select = cQ.select(from);
		select.where(cB.equal(pClass, ec),cB.equal(pRef, ejb.getId()));
		try	{return em.createQuery(select).getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("No "+fbTs.getClassBridge().getName()+" found for entityClass/refId");}
	}
	
	@Override public <T extends EjbWithId> List<BRIDGE> fBridges(EC ec, List<T> ejbs)
	{
		if(ejbs==null || ejbs.isEmpty()) {return new ArrayList<BRIDGE>();}
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<BRIDGE> cQ = cB.createQuery(fbTs.getClassBridge());
		Root<BRIDGE> from = cQ.from(fbTs.getClassBridge());
		
		Path<EC> pClass = from.get(JeeslTsBridge.Attributes.entityClass.toString());
		Path<Long> pRef = from.get(JeeslTsBridge.Attributes.refId.toString());
		
		CriteriaQuery<BRIDGE> select = cQ.select(from);
		select.where(cB.equal(pClass, ec),pRef.in(EjbIdFactory.toLongs(ejbs)));
		return em.createQuery(select).getResultList();
	}
	
	@Override public boolean isTimeSeriesAllowed(SCOPE scope, INT interval, EC c)
	{
		scope = this.find(fbTs.getClassScope(),scope);
		return scope.getIntervals().contains(interval) && scope.getClasses().contains(c);
	}
	
	@Override public List<TS> fTimeSeries(List<BRIDGE> bridges)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TS> cQ = cB.createQuery(fbTs.getClassTs());
		
		Root<TS> ts = cQ.from(fbTs.getClassTs());
		Join<TS,BRIDGE> jBridge = ts.join(JeeslTimeSeries.Attributes.bridge.toString());
		
		predicates.add(jBridge.in(bridges));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(ts);
		return em.createQuery(cQ).getResultList();
	}
	
	@Override public List<TS> fTimeSeries(List<BRIDGE> bridges, List<SCOPE> scopes)
	{
		if(bridges==null || bridges.isEmpty()) {return new ArrayList<TS>();}
		if(scopes==null || scopes.isEmpty()) {return new ArrayList<TS>();}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TS> cQ = cB.createQuery(fbTs.getClassTs());
		
		Root<TS> ts = cQ.from(fbTs.getClassTs());
		Join<TS,BRIDGE> jBridge = ts.join(JeeslTimeSeries.Attributes.bridge.toString());
		Join<TS,SCOPE> jScope = ts.join(JeeslTimeSeries.Attributes.scope.toString());
		
		predicates.add(jBridge.in(bridges));
		predicates.add(jScope.in(scopes));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(ts);
		return em.createQuery(cQ).getResultList();
	}

	@Override public List<TS> fTimeSeries(SCOPE scope, INT interval, EC entityClass)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TS> cQ = cB.createQuery(fbTs.getClassTs());
		
		Root<TS> ts = cQ.from(fbTs.getClassTs());
		Join<TS,BRIDGE> jBridge = ts.join(JeeslTimeSeries.Attributes.bridge.toString());
		
		predicates.add(cB.equal(ts.<SCOPE>get(JeeslTimeSeries.Attributes.scope.toString()), scope));
		predicates.add(cB.equal(ts.<INT>get(JeeslTimeSeries.Attributes.interval.toString()), interval));
		predicates.add(cB.equal(jBridge.<EC>get(JeeslTsBridge.Attributes.entityClass.toString()), entityClass));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(ts);
		return em.createQuery(cQ).getResultList();
	}
	
	@Override public TS fcTimeSeries(SCOPE scope, INT interval, BRIDGE bridge) throws JeeslConstraintViolationException
	{
		if (!isTimeSeriesAllowed(scope, interval, bridge.getEntityClass()))
		{
			StringBuilder sb = new StringBuilder();
			sb.append("The requested time series combintaion of scope, interval and class ist not allowed. ");
			sb.append(" scope:"+scope.getCode());
			sb.append(" interval:"+interval.getCode());
			sb.append(" class:"+bridge.getEntityClass().getCode());
			throw new JeeslConstraintViolationException(sb.toString());
		}
		try {return fTimeSeries(scope, interval, bridge);}
		catch (JeeslNotFoundException e)
		{
			TS ts = efTs.build(scope, interval, bridge);
			return this.persist(ts);
		}
	}
	@Override public TS fTimeSeries(SCOPE scope, INT interval, BRIDGE bridge) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TS> cQ = cB.createQuery(fbTs.getClassTs());
		Root<TS> ts = cQ.from(fbTs.getClassTs());
		
		Path<SCOPE> pScope = ts.get(JeeslTimeSeries.Attributes.scope.toString());
		Path<INT> pInterval = ts.get(JeeslTimeSeries.Attributes.interval.toString());
		Path<BRIDGE> pBridge = ts.get(JeeslTimeSeries.Attributes.bridge.toString());
		
		CriteriaQuery<TS> select = cQ.select(ts);
		select.where(cB.equal(pScope,scope),cB.equal(pInterval,interval),cB.equal(pBridge, bridge));
		
		try	{return em.createQuery(select).getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("No "+fbTs.getClassTs().getName()+" found for scope/interval/bridge");}
	}
	
	@Override
	public List<DATA> fData(TRANSACTION transaction)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(fbTs.getClassData());
		Root<DATA> data = cQ.from(fbTs.getClassData());
		
		predicates.add(cB.equal(data.<TRANSACTION>get(JeeslTsData.Attributes.transaction.toString()), transaction));
		Expression<Date> eRecord = data.get(JeeslTsData.Attributes.record.toString());
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);
		cQ.orderBy(cB.asc(eRecord));
		
		return em.createQuery(cQ).getResultList();
	}
	
	@Override public List<DATA> fData(WS workspace, TS timeSeries){return fData(workspace,timeSeries,null,null);}
	@Override public List<DATA> fData(WS workspace, TS timeSeries, Date from, Date to)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(fbTs.getClassData());
		Root<DATA> data = cQ.from(fbTs.getClassData());
		
		predicates.add(cB.equal(data.<WS>get(JeeslTsData.Attributes.workspace.toString()), workspace));
		predicates.add(cB.equal(data.<TS>get(JeeslTsData.Attributes.timeSeries.toString()), timeSeries));
		
		Expression<Date> eRecord = data.get(JeeslTsData.Attributes.record.toString());
		if(from!=null){predicates.add(cB.greaterThanOrEqualTo(eRecord, from));}
		if(to!=null){predicates.add(cB.lessThan(eRecord,to));}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);
		cQ.orderBy(cB.asc(eRecord));
		
		return em.createQuery(cQ).getResultList();
	}
	
	@Override public List<POINT> fPoints(WS workspace, TS timeSeries, Date from, Date to)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<POINT> cQ = cB.createQuery(fbTs.getClassPoint());
		Root<POINT> point = cQ.from(fbTs.getClassPoint());
		
		Path<DATA> data = point.get(JeeslTsDataPoint.Attributes.data.toString());
		predicates.add(cB.equal(data.<WS>get(JeeslTsData.Attributes.workspace.toString()), workspace));
		predicates.add(cB.equal(data.<TS>get(JeeslTsData.Attributes.timeSeries.toString()), timeSeries));
		
		Expression<Date> eRecord = data.get(JeeslTsData.Attributes.record.toString());
		if(from!=null){predicates.add(cB.greaterThanOrEqualTo(eRecord, from));}
		if(to!=null){predicates.add(cB.lessThan(eRecord,to));}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(point);
		cQ.orderBy(cB.asc(eRecord));
		
		return em.createQuery(cQ).getResultList();
	}
	
	@Override
	public void deleteTransaction(TRANSACTION transaction) throws JeeslConstraintViolationException
	{
		transaction = em.find(fbTs.getClassTransaction(), transaction.getId());
		this.rmProtected(transaction);
	}

	@Override
	public List<TRANSACTION> fTransactions(List<USER> users, Date from, Date to)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TRANSACTION> cQ = cB.createQuery(fbTs.getClassTransaction());
		Root<TRANSACTION> data = cQ.from(fbTs.getClassTransaction());
		
		Expression<Date> eRecord = data.get(JeeslTsTransaction.Attributes.record.toString());
		if(from!=null){predicates.add(cB.greaterThanOrEqualTo(eRecord, from));}
		if(to!=null){predicates.add(cB.lessThan(eRecord,to));}
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);
		cQ.orderBy(cB.asc(eRecord));
		
		return em.createQuery(cQ).getResultList();
	}
	
	@Override public Json1Tuples<TS> tpCountRecordsByTs(List<TS> series)
	{
		Json1TuplesFactory<TS> jtf = new Json1TuplesFactory<TS>(this,fbTs.getClassTs());
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
		Root<DATA> data = cQ.from(fbTs.getClassData());
		
		Expression<Long> eCount = cB.count(data.<Long>get("id"));
		Join<DATA,TS> jTs = data.join(JeeslTsData.Attributes.timeSeries.toString());
//		Path<BRIDGE> pModule = jFr.get(ErpSrsFr.Attributes.module.toString());
//		Path<ErpSrsRelease> pRelease = item.get(ErpSrsImplementation.Attributes.release.toString());
		
		cQ.groupBy(jTs.get("id"));
		cQ.multiselect(jTs.get("id"),eCount);
//
//		cQ.where(cB.and(ErpPredicateBuilder.srsImplementation(cB, query, item)));
//	       
		TypedQuery<Tuple> tQ = em.createQuery(cQ);
        return jtf.buildCount(tQ.getResultList());
	}
}