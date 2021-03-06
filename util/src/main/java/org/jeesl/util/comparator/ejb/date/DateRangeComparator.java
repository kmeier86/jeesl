package org.jeesl.util.comparator.ejb.date;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.with.date.EjbWithDateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateRangeComparator<T extends EjbWithDateRange> implements Comparator<T>
{
	final static Logger logger = LoggerFactory.getLogger(DateRangeComparator.class);

	public int compare(T a, T b)
    {
		  CompareToBuilder ctb = new CompareToBuilder();
		  ctb.append(a.getStartDate(),b.getStartDate());
		  ctb.append(a.getId(), b.getId());
		  return ctb.toComparison();
    }
}