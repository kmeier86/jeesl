package net.sf.ahtutils.controller.factory.xml.finance;

import java.util.Date;

import net.sf.ahtutils.controller.factory.utils.PoiRowColNumerator;
import net.sf.ahtutils.exception.processing.UtilsProcessingException;
import net.sf.ahtutils.xml.finance.Time;
import net.sf.exlp.util.DateUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTimeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTimeFactory.class);
	
	public static Time create(String code, Date record)
	{
		Time xml = new Time();
		xml.setCode(code);
		xml.setRecord(DateUtil.getXmlGc4D(record));
		return xml;
	}
	
	public static Time create(Sheet sheet, int row, int col, String code, String label) throws UtilsProcessingException
	{
		Cell cell = sheet.getRow(row).getCell(col);
		if(cell==null)
		{
			throw new UtilsProcessingException("The cell is null. No Date in "+PoiRowColNumerator.create(row, col));
		}
		else if(cell.getCellType()!=0)
		{
			StringBuffer sb = new StringBuffer();
			sb.append(XmlTimeFactory.class.getSimpleName());
			sb.append(": Cell ").append(PoiRowColNumerator.create(row, col));
			sb.append(" has wrong CellType. Expected:0").append(" Actual:").append(cell.getCellType());
			throw new UtilsProcessingException(sb.toString());
		}
		else
		{
			Time t = XmlTimeFactory.create(code, cell.getDateCellValue());
			t.setLabel(label);
			return t;
		}
	}
}