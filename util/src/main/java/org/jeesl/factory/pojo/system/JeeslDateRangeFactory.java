package org.jeesl.factory.pojo.system;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	
	public static Date[] getStartAndEndOfMonth8(Date day)
	{
		Date[] startAndEnd = new Date[2];
		LocalDateTime start = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().withDayOfMonth(1).atStartOfDay();
		LocalDateTime end = start.plusMonths(1).minusSeconds(1);
		startAndEnd[0] = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
		startAndEnd[1] = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
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
	
	public static Date[] getStartAndEndOfWeek8(Date day)
	{
		Date[] startAndEnd = new Date[2];
		LocalDateTime start = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().with(DayOfWeek.MONDAY).atStartOfDay();
		LocalDateTime end = start.plusWeeks(1).minusSeconds(1);
		startAndEnd[0] = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
		startAndEnd[1] = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
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
	
	public static Date[] getStartAndEndOfDay8(Date day)
	{
		Date[] startAndEnd = new Date[2];
		LocalDateTime start = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
		LocalDateTime end = start.plusDays(1).minusSeconds(1);
		startAndEnd[0] = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
		startAndEnd[1] = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
		return startAndEnd;
 	}
	
	public static void main(String[] args)
	{
		Date now = new Date();
		Date[] resultMonth = getStartAndEndOfMonth(now);
		Date[] resultWeek  = getStartAndEndOfWeek(now);
		Date[] resultDay   = getStartAndEndOfDay(now);
		Date[] resultMonth8 = getStartAndEndOfMonth8(now);
		Date[] resultWeek8 = getStartAndEndOfWeek8(now);
		Date[] resultDay8 = getStartAndEndOfDay8(now);
		
		System.out.println("Month Start at " +resultMonth[0].toString() +" and end at " +resultMonth[1].toString());
		System.out.println("Week  Start at " +resultWeek[0].toString() +" and end at " +resultWeek[1].toString());
		System.out.println("Day   Start at " +resultDay[0].toString() +" and end at " +resultDay[1].toString());
		System.out.println("Month Start at " +resultMonth8[0].toString() +" and end at " +resultMonth8[1].toString());
		System.out.println("Week  Start at " +resultWeek8[0].toString() +" and end at " +resultWeek8[1].toString());
		System.out.println("Day   Start at " +resultDay8[0].toString() +" and end at " +resultDay8[1].toString());
	}
	
}
