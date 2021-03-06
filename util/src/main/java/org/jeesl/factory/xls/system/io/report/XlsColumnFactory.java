package org.jeesl.factory.xls.system.io.report;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportLayout;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class XlsColumnFactory <L extends JeeslLang,D extends JeeslDescription,
							CATEGORY extends JeeslStatus<CATEGORY,L,D>,
							REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
							IMPLEMENTATION extends JeeslStatus<IMPLEMENTATION,L,D>,
							WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
							SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
							GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
							COLUMN extends JeeslReportColumn<L,D,GROUP,STYLE,CDT,CW,TLS>,
							ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
							TEMPLATE extends JeeslReportTemplate<L,D,CELL>,
							CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
							STYLE extends JeeslReportStyle<L,D>,
							CDT extends JeeslStatus<CDT,L,D>,CW extends JeeslStatus<CW,L,D>,
							RT extends JeeslStatus<RT,L,D>,
							ENTITY extends EjbWithId,
							ATTRIBUTE extends EjbWithId,
							TL extends JeeslTrafficLight<L,D,TLS>,
							TLS extends JeeslStatus<TLS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XlsColumnFactory.class);
		
	public XlsColumnFactory()
	{
		
	}
	
	public void trackWidth(Sheet sheet, List<COLUMN> columns)
	{
		if(sheet instanceof SXSSFSheet)
		{
			for(int i=0; i<columns.size(); i++)
	        {
				COLUMN ioColumn = columns.get(i);
				if(ioColumn.getColumWidth()!=null)
				{
					switch(JeeslReportLayout.ColumnWidth.valueOf(ioColumn.getColumWidth().getCode()))
					{
						case none: break;
						case auto: ((SXSSFSheet)sheet).trackColumnForAutoSizing(i);break;
						case min: break;
						default: break;
					}
				}
	        }
		}
	}
	
	public void adjustWidth(Sheet sheet, List<COLUMN> columns)
    {
		for(int i=0; i<columns.size(); i++)
        {
			COLUMN ioColumn = columns.get(i);
			if(ioColumn.getColumWidth()!=null)
			{
				switch(JeeslReportLayout.ColumnWidth.valueOf(ioColumn.getColumWidth().getCode()))
				{
					case none: break;
					case auto: sheet.autoSizeColumn(i);break;
					case min: sheet.setColumnWidth(i, ioColumn.getColumSize());break;
					default: break;
				}
			}
        }
    }
	
	public static int code2Index(String code)
	{
		int result = 0;
		for(int i=0;i<code.length();i++)
		{
			if(Character.isUpperCase(code.charAt(i))){result = result *26 +(code.charAt(i)-('A'-1));}			
			else{result = result *26 +(code.charAt(i)-('a'-1));}
		}
		return result -1;
	}
}
