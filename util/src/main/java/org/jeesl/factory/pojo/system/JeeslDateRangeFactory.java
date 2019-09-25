package org.jeesl.factory.pojo.system;

import java.util.Date;
import org.joda.time.DateTime;

public class JeeslDateRangeFactory {

	public static Date[] getStartAndEndOfMonth(Date day)
	{
		Date[] startAndEnd = new Date[2];
		DateTime start = new DateTime(day);
		DateTime end   = new DateTime(day);
		start = start.withDayOfMonth(1).withTimeAtStartOfDay();
		end   = end.withDayOfMonth(end.dayOfMonth().getMaximumValue()).plusDays(1).withTimeAtStartOfDay();
		startAndEnd[0] = start.toDate();
		startAndEnd[1] = end.toDate();
		return startAndEnd;
	}
	
	public static Date[] getStartAndEndOfWeek(Date day)
	{
		Date[] startAndEnd = new Date[2];
		DateTime start = new DateTime(day);
		DateTime end   = new DateTime(day);
		start = start.withDayOfWeek(1).withTimeAtStartOfDay();
		end   = end.withDayOfWeek(7).plusDays(1).withTimeAtStartOfDay();
		startAndEnd[0] = start.toDate();
		startAndEnd[1] = end.toDate();
		return startAndEnd;
	}
	
	public static Date[] getStartAndEndOfDay(Date day)
	{
		Date[] startAndEnd = new Date[2];
		DateTime start = new DateTime(day);
		DateTime end   = new DateTime(day);
		start = start.withTimeAtStartOfDay();
		end   = end.plusDays(1).withTimeAtStartOfDay();
		startAndEnd[0] = start.toDate();
		startAndEnd[1] = end.toDate();
		return startAndEnd;
	}
	
	public static void main(String[] args)
	{
		Date now = new Date();
		Date[] resultMonth = getStartAndEndOfMonth(now);
		Date[] resultWeek  = getStartAndEndOfWeek(now);
		Date[] resultDay   = getStartAndEndOfDay(now);
		
		System.out.println("Month Start at " +resultMonth[0].toString() +" and end at " +resultMonth[1].toString());
		System.out.println("Week  Start at " +resultWeek[0].toString() +" and end at " +resultWeek[1].toString());
		System.out.println("Day   Start at " +resultDay[0].toString() +" and end at " +resultDay[1].toString());
	}
	
}
