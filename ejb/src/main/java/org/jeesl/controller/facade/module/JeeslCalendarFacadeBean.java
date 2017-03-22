package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslCalendarFacade;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslCalendarFacadeBean<L extends UtilsLang,
									D extends UtilsDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,CT,ITEM,IT>,
									CT extends UtilsStatus<CT,L,D>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,CT,ITEM,IT>,
									IT extends UtilsStatus<IT,L,D>
									>
					extends UtilsFacadeBean
					implements JeeslCalendarFacade<L,D,CALENDAR,CT,ITEM,IT>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslCalendarFacadeBean.class);
	
	private final Class<ITEM> cItem;
	
	public JeeslCalendarFacadeBean(EntityManager em, final Class<ITEM> cItem)
	{
		super(em);
		this.cItem=cItem;
	}

	//JEESL Calendar
	@Override
	public List<ITEM> fCalendarItems(CALENDAR calendar, Date from, Date to)
	{
		List<CALENDAR> calendars = new ArrayList<CALENDAR>();
		calendars.add(calendar);
		return fCalendarItems(calendars,from,to);
	}
	
	@Override
	public List<ITEM> fCalendarItems(List<CALENDAR> calendars, Date from, Date to)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<ITEM> cQ = cB.createQuery(cItem);
		Root<ITEM> root = cQ.from(cItem);
		
		Expression<Date> dStart = root.get(JeeslCalendarItem.Attributes.startDate.toString());
		Expression<Date> dEnd   = root.get(JeeslCalendarItem.Attributes.endDate.toString());
		
		//After
	    Predicate startAfterFrom = cB.greaterThanOrEqualTo(dStart, from);
	    Predicate endAfterFrom = cB.greaterThanOrEqualTo(dEnd, from);
	    Predicate endAfterTo = cB.greaterThanOrEqualTo(dEnd, to);
	    
	    //Before
	    Predicate startBeforeTo = cB.lessThan(dStart, to);
	    Predicate startBeforeFrom = cB.lessThan(dStart, from);
	    Predicate endBeforeTo = cB.lessThan(dEnd, to);
		
		Predicate pOnlyStartAndStartInRange = cB.and(cB.isNull(dEnd),startAfterFrom,startBeforeTo);
		Predicate pStartAndEndInRange = cB.and(cB.isNotNull(dEnd),startAfterFrom,endBeforeTo);
		Predicate pStartOutsideEndInRange = cB.and(cB.isNotNull(dEnd),startBeforeFrom,endAfterFrom,endBeforeTo);
		Predicate pStartInRangeEndOutside = cB.and(cB.isNotNull(dEnd),startAfterFrom,startBeforeTo,endAfterTo);
		Predicate pStartBeforeRangeEndAfterRange = cB.and(cB.isNotNull(dEnd),startBeforeFrom,endAfterTo);
		predicates.add(cB.or(pOnlyStartAndStartInRange,pStartAndEndInRange,pStartOutsideEndInRange,pStartInRangeEndOutside,pStartBeforeRangeEndAfterRange));
		
		Join<ITEM,CALENDAR> jCalendar = root.join(JeeslCalendarItem.Attributes.calendar.toString());
		predicates.add(cB.isTrue(jCalendar.in(calendars)));
				    
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);
		cQ.orderBy(cB.asc(dStart));
		
		return em.createQuery(cQ).getResultList();
	}
}