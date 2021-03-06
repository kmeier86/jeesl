package org.jeesl.factory.xls.system.io.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeesl.factory.builder.system.ReportFactoryBuilder;
import org.jeesl.factory.ejb.system.io.report.EjbIoReportColumnFactory;
import org.jeesl.factory.txt.system.io.report.TxtIoColumnFactory;
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
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class XlsStyleFactory<
								SHEET extends JeeslReportSheet<?,?,?,?,GROUP,ROW>,
								GROUP extends JeeslReportColumnGroup<?,?,SHEET,COLUMN,STYLE>,
								COLUMN extends JeeslReportColumn<?,?,GROUP,STYLE,CDT,CW,TLS>,
								ROW extends JeeslReportRow<?,?,?,?,?,?,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								TEMPLATE extends JeeslReportTemplate<?,?,CELL>,
								CELL extends JeeslReportCell<?,?,?,?,?,?,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								STYLE extends JeeslReportStyle<?,?>,
								CDT extends JeeslStatus<CDT,?,?>,CW extends JeeslStatus<CW,?,?>,
								RT extends JeeslStatus<RT,?,?>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<?,?,TLS>,
								TLS extends JeeslStatus<TLS,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(XlsStyleFactory.class);
	
	private Map<STYLE,CellStyle> mapHeader;
	
	private Map<COLUMN,CellStyle> mapCell;
	private Map<ROW,CellStyle> mapRow;
	
	private Map<COLUMN,JeeslReportLayout.Data> mapCellDataType;
	private Map<ROW,JeeslReportLayout.Data> mapRowDataType;
	
	private CellStyle styleFallback; public CellStyle getStyleFallback() {return styleFallback;}
	
	private CellStyle styleLabelCenter; public CellStyle getStyleLabelCenter() {return styleLabelCenter;}
	private CellStyle styleLabelLeft; public CellStyle getStyleLabelLeft() {return styleLabelLeft;}
	private TxtIoColumnFactory<COLUMN> tfColumn;
	private final EjbIoReportColumnFactory<?,?,?,?,?,?,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,?,?> efColumn;
	
	public XlsStyleFactory(final ReportFactoryBuilder<?,?,?,?,?,?,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,?,ENTITY,ATTRIBUTE,TL,TLS,?,?> fbReport,
							Workbook xlsWorkbook, List<GROUP> ioGroups, List<COLUMN> ioColumns, List<ROW> ioRows)
	{
		efColumn = fbReport.column();
		mapHeader = new HashMap<STYLE,CellStyle>();
		mapCell = new HashMap<COLUMN,CellStyle>();
		mapRow = new HashMap<ROW,CellStyle>();
		mapCellDataType = new HashMap<COLUMN,JeeslReportLayout.Data>();
		mapRowDataType = new HashMap<ROW,JeeslReportLayout.Data>();
		
		tfColumn = new TxtIoColumnFactory<COLUMN>("en");
		
        Font fontItalicBold = xlsWorkbook.createFont();
        fontItalicBold.setItalic(true);
        fontItalicBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		styleFallback = xlsWorkbook.createCellStyle();
//        dateHeaderStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
        styleFallback.setAlignment(CellStyle.ALIGN_LEFT);
        styleFallback.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
 //       dateHeaderStyle.setFont(font);
        
        styleLabelCenter = xlsWorkbook.createCellStyle();
        styleLabelCenter.setAlignment(CellStyle.ALIGN_CENTER);
        styleLabelCenter.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        styleLabelCenter.setFont(fontItalicBold);
        
        styleLabelLeft = xlsWorkbook.createCellStyle();
        styleLabelLeft.setAlignment(CellStyle.ALIGN_LEFT);
        styleLabelLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        styleLabelLeft.setFont(fontItalicBold);
        
		for(ROW r : ioRows)
		{
			mapRow.put(r, buildRow(xlsWorkbook,r));
			mapRowDataType.put(r,toDataTypeEnum(efColumn.toRowDataType(r)));
		}
		for(GROUP g : ioGroups)
		{
			if(!mapHeader.containsKey(g.getStyleHeader())){mapHeader.put(g.getStyleHeader(), buildStyle(xlsWorkbook,g.getStyleHeader()));}
		}
		for(COLUMN c : ioColumns)
		{
			mapCell.put(c, buildCell(xlsWorkbook,c));
			CDT cdt = efColumn.toCellDataType(c);
			logger.trace(tfColumn.position(c));
			mapCellDataType.put(c,toDataTypeEnum(cdt));
		}
	}
	
	private JeeslReportLayout.Data toDataTypeEnum(CDT cdt)
	{
		if(cdt==null){return JeeslReportLayout.Data.string;}
		
		if(cdt.getCode().startsWith("text")){return JeeslReportLayout.Data.string;}
		else if(cdt.getCode().startsWith("numberDouble")){return JeeslReportLayout.Data.dble;}
		else if(cdt.getCode().startsWith("numberInteger")){return JeeslReportLayout.Data.intgr;}
		else if(cdt.getCode().startsWith("numberLong")){return JeeslReportLayout.Data.lng;}
		else if(cdt.getCode().startsWith("date")){return JeeslReportLayout.Data.dte;}
		else if(cdt.getCode().startsWith("bool")){return JeeslReportLayout.Data.bool;}
		else
		{
			return JeeslReportLayout.Data.string;
		}	
	}
	
	private CellStyle buildStyle(Workbook xlsWorkbook, STYLE ioStyle)
	{
        Font font = xlsWorkbook.createFont();
        if(ioStyle.isFontItalic()){font.setItalic(true);}
        if(ioStyle.isFontBold()){font.setBoldweight(Font.BOLDWEIGHT_BOLD);}
        
        CellStyle style = xlsWorkbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(font);
        style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        return styleLabelCenter;
	}
	
	private CellStyle buildCell(Workbook xlsWorkbook, COLUMN column)
	{
        CellStyle style = xlsWorkbook.createCellStyle();
//        style.setAlignment(CellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
       
        CDT dataType = efColumn.toCellDataType(column);
        if(dataType!=null)
        {
        	if(dataType.getCode().startsWith(JeeslRevisionAttribute.Type.text.toString()))
        	{
        		style.setDataFormat(xlsWorkbook.getCreationHelper().createDataFormat().getFormat("text"));
        	}
        	else if(dataType.getCode().startsWith(JeeslRevisionAttribute.Type.number.toString()))
        	{
        		logger.info("Creating Number "+dataType.getSymbol());
        		style.setDataFormat(xlsWorkbook.getCreationHelper().createDataFormat().getFormat(transformJavaToPoiPattern(dataType.getSymbol())));
        	}
        	else if(dataType.getCode().startsWith(JeeslRevisionAttribute.Type.date.toString()))
        	{
        		logger.info("Creating Date "+dataType.getSymbol());
        		style.setDataFormat(xlsWorkbook.getCreationHelper().createDataFormat().getFormat(dataType.getSymbol()));
        	}
        }
        
        return style;
	}
	
	private CellStyle buildRow(Workbook xlsWorkbook, ROW row)
	{
        CellStyle style = xlsWorkbook.createCellStyle();
       
        CDT dataType = efColumn.toRowDataType(row);
        if(dataType!=null)
        {
        	if(dataType.getCode().startsWith(JeeslRevisionAttribute.Type.text.toString()))
        	{
        		style.setDataFormat(xlsWorkbook.getCreationHelper().createDataFormat().getFormat("text"));
        	}
        	else if(dataType.getCode().startsWith(JeeslRevisionAttribute.Type.number.toString()))
        	{
        		logger.info("Creating Number "+dataType.getSymbol());
        		style.setDataFormat(xlsWorkbook.getCreationHelper().createDataFormat().getFormat(transformJavaToPoiPattern(dataType.getSymbol())));
        	}
        	else if(dataType.getCode().startsWith(JeeslRevisionAttribute.Type.date.toString()))
        	{
        		logger.info("Creating Row Date "+dataType.getSymbol());
        		style.setDataFormat(xlsWorkbook.getCreationHelper().createDataFormat().getFormat(dataType.getSymbol()));
        	}
        }
        
        return style;
	}
	
	public CellStyle get(JeeslReportLayout.Style type, GROUP group)
	{
		switch(type)
		{
			case header: return mapHeader.get(group.getStyleHeader());
			case cell: return styleFallback;
			default: return styleFallback;
		}
	}
	
	public JeeslReportLayout.Data getDataType(COLUMN column){return mapCellDataType.get(column);}
	public JeeslReportLayout.Data getDataType(ROW row){return mapRowDataType.get(row);}
	
	public CellStyle get(JeeslReportLayout.Style type, COLUMN column)
	{
		switch(type)
		{
			case header: return styleLabelCenter;
			case cell: return mapCell.get(column);
			default: return styleFallback;
		}
	}
	
	public CellStyle get(COLUMN column)
	{
		return styleFallback;
	}
	
	public CellStyle get(ROW row)
	{
		if(mapRow.containsKey(row))
		{
//			logger.info("Found Style for row:"+row.toString());
			return mapRow.get(row);
		}
		else
		{
//			logger.info("Fallback for row:"+row.toString());
			return styleFallback;
		}
	}
	
	
	public static CellStyle label(Workbook workbook, Font font)
	{
		CellStyle style = workbook.createCellStyle();
//		style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(font);
		return style;
	}
	
	public static CellStyle fallback(Workbook workbook)
	{
		CellStyle style = workbook.createCellStyle();
//		style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		style.setFont(font);
		return style;
	}
	
	public static String transformJavaToPoiPattern(String pattern)
	{
		return pattern.replaceAll("#","0");
	}
}