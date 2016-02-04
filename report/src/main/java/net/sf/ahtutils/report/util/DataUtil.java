/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.ahtutils.report.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataUtil {
    
    final static Logger logger = LoggerFactory.getLogger(DataUtil.class);

    public static String getStringValue(Object object)
    	{
		// Since a simple "toString()" is not sufficient because the value can also be null, 
		// a more complex method has been chosen here to get a String representation of a cell value 
		String value = "";
		if (object.getClass().equals(String.class))
		{
			value = (String) object;
		}
		else if (object.getClass().equals(Date.class))
		{
			Date date = (Date) object;
			DateFormat df = SimpleDateFormat.getDateInstance();
			value = df.format(date);
		}
		else if (object.getClass().equals(Double.class))
		{
			Double doubleValue = (Double) object;
			value = doubleValue.toString();
		}
		else
		{
			value = "CELL IS NULL";
		}
		return value;
	}
    
    public static Object getCellValue(Cell cell)
	{
		Object value = new Object();
		
		// Prevent a NullPointerException
		if (cell != null)
		{
			if (cell.getHyperlink()!=null)
						{
                                                        Workbook workbook = new XSSFWorkbook();
							FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
							Hyperlink link = cell.getHyperlink();
							String address = link.getAddress();
							if(logger.isTraceEnabled()){logger.trace("Found a Hyperlink to " +cell.getHyperlink().getAddress() +" in cell " +cell.getRowIndex() +"," +cell.getColumnIndex());}
							cell = evaluator.evaluateInCell(cell);
						}
			// Depending on the cell type, the value is read using Apache POI methods
			switch (cell.getCellType()) {
			
				// String are easy to handle
				case Cell.CELL_TYPE_STRING : 
					logger.trace("Found string " +cell.getStringCellValue());
					value = cell.getStringCellValue();
					break;
					
				// Since date formatted cells are also of the numeric type, this needs to be processed
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell))
					{
						Date date = cell.getDateCellValue();
						DateFormat df = SimpleDateFormat.getDateInstance();
						logger.trace("Found date " +df.format(date));
						value = date;
					}
					else
					{
						logger.trace("Found general number " +cell.getNumericCellValue());
						value = cell.getNumericCellValue();
					}
					break;
			}
		}
		else
		{
			logger.trace("Found cell with NULL value");
		}
		return value;
	}
}