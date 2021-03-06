package org.jeesl.factory.sql;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public class SqlDateFactory
{
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");;
	
	public static <T extends EjbWithRecord> void orderByRecordDesc(StringBuilder sb, Class<T> c, String alias, boolean newLine)
	{
		sb.append("ORDER BY");
		sb.append(" ").append(alias).append(".").append(EjbWithRecord.attributeRecord);
		sb.append(" DESC");
		SqlFactory.newLine(newLine,sb);
	}
	
	public static <T extends EjbWithRecord> void andHalfOpenDate(StringBuilder sb, Class<T> c, String alias, Date from, Date to, boolean newLine)
	{
		sb.append(" AND");
		sb.append(" ( ").append(EjbWithRecord.attributeRecord).append(" >= '").append(sdfDate.format(from)).append("'");
		sb.append(" AND ").append(EjbWithRecord.attributeRecord).append(" < '").append(sdfDate.format(to)).append("'");
		sb.append(" )");
		SqlFactory.newLine(newLine,sb);
	}
}