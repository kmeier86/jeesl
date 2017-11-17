package org.jeesl.controller.facade.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.io.JeeslIoAttributeFacade;
import org.jeesl.factory.builder.io.IoAttributeFactoryBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeData;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeItem;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeOption;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoAttributeFacadeBean<L extends UtilsLang, D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										OPTION extends JeeslAttributeOption<L,D,CRITERIA>,
										SET extends JeeslAttributeSet<L,D,CATEGORY,ITEM>,
										ITEM extends JeeslAttributeItem<CRITERIA,SET>,
										CONTAINER extends JeeslAttributeContainer<SET,DATA>,
										DATA extends JeeslAttributeData<CRITERIA,OPTION,CONTAINER>>
					extends UtilsFacadeBean
					implements JeeslIoAttributeFacade<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoAttributeFacadeBean.class);
	
	private final IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute;
	
	public JeeslIoAttributeFacadeBean(EntityManager em, IoAttributeFactoryBuilder<L,D,CATEGORY,CRITERIA,TYPE,OPTION,SET,ITEM,CONTAINER,DATA> fbAttribute)
	{
		super(em);
		this.fbAttribute=fbAttribute;
	}
	
	@Override
	public List<CRITERIA> fAttributeCriteria(List<CATEGORY> categories, long refId)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<CRITERIA>();}
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<CRITERIA> cQ = cB.createQuery(fbAttribute.getClassCriteria());
		Root<CRITERIA> root = cQ.from(fbAttribute.getClassCriteria());
		
		Join<CRITERIA,CATEGORY> jCategory = root.join(JeeslAttributeCriteria.Attributes.category.toString());
		predicates.add(jCategory.in(categories));
		
		if(refId>0)
		{
			Expression<Long> eRefId = root.get(JeeslAttributeCriteria.Attributes.refId.toString());
			predicates.add(cB.equal(eRefId,refId));
		}
		
		Path<Integer> pPosition = root.get(JeeslAttributeCriteria.Attributes.position.toString());
		Path<Integer> pCategoryPosition = jCategory.get(JeeslAttributeCriteria.Attributes.position.toString());
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.asc(pCategoryPosition),cB.asc(pPosition));
		cQ.select(root);

		return em.createQuery(cQ).getResultList();
	}

	@Override
	public List<DATA> fAttributeData(CONTAINER container)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<DATA> cQ = cB.createQuery(fbAttribute.getClassData());
		Root<DATA> data = cQ.from(fbAttribute.getClassData());
		
		Path<CONTAINER> pContainer = data.join(JeeslAttributeData.Attributes.container.toString());
		predicates.add(cB.equal(pContainer,container));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(data);

		TypedQuery<DATA> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}	
}