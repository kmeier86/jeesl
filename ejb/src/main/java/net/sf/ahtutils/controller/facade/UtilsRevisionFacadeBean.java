package net.sf.ahtutils.controller.facade;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.jeesl.factory.json.system.revision.JsonRevisionFactory;
import org.jeesl.interfaces.model.system.revision.EjbWithRevisionAttributes;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.util.query.sql.SqlRevisionQueries;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.db.sql.SqlNativeQueryHelper;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.json.system.revision.JsonRevision;

public class UtilsRevisionFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,	
									RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RST extends UtilsStatus<RST,L,D>,
									RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
					extends UtilsFacadeBean
					implements UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
{	
	private String revisionPrefix;
	private String revisionTable;

	public UtilsRevisionFacadeBean(EntityManager em)
	{
		this("_at_","auditinfo",em);
	}
	
	public UtilsRevisionFacadeBean(String revisionPrefix, String revisionTable, EntityManager em)
	{
		super(em);
		this.revisionPrefix=revisionPrefix;
		this.revisionTable=revisionTable;
	}
	
	@Override public RV load(Class<RV> cView, RV view)
	{
		view = em.find(cView, view.getId());
		view.getMaps().size();
		return view;
	}
	
	@Override public RS load(Class<RS> cScope, RS scope)
	{
		scope = em.find(cScope, scope.getId());
		scope.getAttributes().size();
		return scope;
	}

	@Override public RE load(Class<RE> cEntity, RE entity)
	{
		entity = em.find(cEntity, entity.getId());
		entity.getAttributes().size();
		entity.getMaps().size();
		return entity;
	}
	
	@Override public List<RS> findScopes(Class<RS> cScope, Class<RC> cCategory, List<RC> categories, boolean showInvisibleScopes)
	{
		List<ParentPredicate<RC>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cScope,ppCategory);
	}
	
	@Override public List<RE> findEntities(Class<RE> cEntity, Class<RC> cCategory, List<RC> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<RC>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cEntity,ppCategory);
	}
	
	@Override public void rm(Class<RVM> cMappingView, RVM mapping) throws UtilsConstraintViolationException
	{
		mapping = em.find(cMappingView, mapping.getId());
		mapping.getView().getMaps().remove(mapping);
		this.rmProtected(mapping);
	}

	@Override public <W extends EjbWithRevisionAttributes<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>>
			RA save(Class<W> cW, W entity, RA attribute) throws UtilsLockingException, UtilsConstraintViolationException
	{
		entity = this.find(cW, entity);
		attribute = this.saveProtected(attribute);
		if(!entity.getAttributes().contains(attribute))
		{
			entity.getAttributes().add(attribute);
			this.saveProtected(entity);
		}
		return attribute;
	}

	@Override public <W extends EjbWithRevisionAttributes<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>>
			void rm(Class<W> cW, W entity, RA attribute) throws UtilsConstraintViolationException, UtilsLockingException
	{
		entity = this.find(cW, entity);
		if(entity.getAttributes().contains(attribute))
		{
			entity.getAttributes().remove(attribute);
			this.saveProtected(entity);
		}
		this.rmProtected(attribute);		
	}
	
	@Override public <T extends EjbWithId> T jpaTree(Class<T> c, String jpa, long id) throws UtilsNotFoundException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c ");
		sb.append(" FROM "+c.getName()+" c");
		sb.append(" WHERE c.").append(jpa);
		sb.append("=:refId");
		
		TypedQuery<T> q = em.createQuery(sb.toString(), c);
		q.setParameter("refId", id);
		
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+c.getSimpleName()+" for jpa="+jpa);}
	}
	
	@Override
	public <T extends EjbWithId> List<T> revisions(Class<T> c, List<Long> ids)
	{
		AuditQuery query = AuditReaderFactory.get(em).createQuery().forRevisionsOfEntity(c, false, true);
		query.add(AuditEntity.revisionNumber().in(ids));
		query.addOrder(AuditEntity.revisionNumber().desc());
//		List<SimpleRevisionEntity<T>> list = SimpleRevisionEntity.build(query.getResultList());
//		for(SimpleRevisionEntity<T> item : list){lazyLoad(item.getEntity());}
//		return list;
		return null;
	}
	
	@Override
	public <T extends EjbWithId> List<Long> ids(Class<T> c, UtilsRevisionFacade.Scope scope)
	{
		List<Long> result = new ArrayList<Long>();
		
		return result;
	}

	@Override
	public <T extends EjbWithId> List<JsonRevision> findCreated(Class<T> c, Date from, Date to)
	{
		List<JsonRevision> revisions = new ArrayList<JsonRevision>();
		Table t = c.getAnnotation(Table.class);
		if(t!=null)
		{			
			for(Object o : em.createNativeQuery(SqlRevisionQueries.revisionsIn(revisionPrefix+t.name(), revisionTable, from, to, SqlRevisionQueries.typesCreateRemove())).getResultList())
			{
				Object[] array = (Object[])o;
				SqlNativeQueryHelper.debugDataTypes(false, "findCreated", array);	 
				revisions.add(JsonRevisionFactory.build(array));
			}
			
		}
		return revisions;
	}
}