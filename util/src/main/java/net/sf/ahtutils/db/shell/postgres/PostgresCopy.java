package net.sf.ahtutils.db.shell.postgres;

import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class PostgresCopy
{
	final static Logger logger = LoggerFactory.getLogger(PostgresCopy.class);
	
	public static String nul = "\\N";
	
	public static void write(BufferedWriter bw, String... data) throws IOException
	{
		bw.write(StringUtils.join(data, "\t"));
		bw.newLine();
	}
	
	public static void update(BufferedWriter bw, String table, String fields, String values, long id) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(table);
		sb.append(" ").append(fields);
		sb.append(" VALUES (").append(values).append(")");
		sb.append(" WHERE id=").append(id);
		bw.write(sb.toString());
		bw.newLine();
	}
	
	public static String string(String s)
	{
		if(s==null || s.isEmpty())
		{
			return nul;
		}
		else
		{
			if(s.contains("\r")) {s = s.replaceAll("\r", "\\r");}
			return s;
		}
	}
	
	public static String bool(Boolean value)
	{
		if(value==null) {return nul;}
		else
		{
			if(value){return "t";}
			else{return "f";}
		}
	}
	
	public static String doubl(Double value)
	{
		if(value==null) {return nul;}
		else
		{
			return value.toString();
		}
	}
	
	public static String integ(Integer value)
	{
		if(value==null) {return nul;}
		else
		{
			return value.toString();
		}
	}
	
	public static String ejb(EjbWithId value)
	{
		if(value==null) {return nul;}
		else
		{
			return Long.valueOf(value.getId()).toString();
		}
	}
	
	public static String update(String s)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("'");
		if(s==null || s.isEmpty()){}
		else
		{
			sb.append(s);
		}
		sb.append("'");
		return sb.toString();
	}
	public static String update(Boolean value)
	{
		if(value==null) {return nul;}
		return value.toString();
	}
	public static String update(Integer value)
	{
		if(value==null) {return nul;}
		return value.toString();
	}
}