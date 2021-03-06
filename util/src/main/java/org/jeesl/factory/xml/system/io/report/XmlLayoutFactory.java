package org.jeesl.factory.xml.system.io.report;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.factory.xml.system.symbol.XmlColorFactory;
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
import net.sf.ahtutils.xml.report.Layout;
import net.sf.exlp.util.xml.JaxbUtil;

public class XmlLayoutFactory<L extends JeeslLang,D extends JeeslDescription,
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
								STYLE extends JeeslReportStyle<L,D>,CDT extends JeeslStatus<CDT,L,D>,
								CW extends JeeslStatus<CW,L,D>,
								RT extends JeeslStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends JeeslStatus<TLS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlLayoutFactory.class);
	
	private Layout q;
	
	private XmlTypeFactory<L,D,CW> xfType;
	private XmlStylesFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfStyles;
	private XmlFontFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfFont;
	
	public XmlLayoutFactory(String localeCode, Layout q)
	{
		this.q=q;
		if(q.isSetSize()){xfType = new XmlTypeFactory<>(localeCode,q.getSize().get(0).getType());}
		if(q.isSetStyles()){xfStyles = new XmlStylesFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>(localeCode,q.getStyles());}
		if(q.isSetFont()){xfFont = new XmlFontFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>(q.getFont());}
	}
	
	public Layout build(ROW row)
	{
		Layout xml = build();
		if(q.isSetOffset()){xml.setOffset(XmlOffsetFactory.build(row.getOffsetRows(), row.getOffsetColumns()));}
		return xml;
	}
	
	public Layout build(COLUMN column)
	{
		Layout xml = build();
		if(q.isSetSize())
		{
			if(column.getColumWidth()!=null)
			{
				xml.getSize().add(XmlSizeFactory.build(JeeslReportLayout.Code.columnWidth,
						xfType.build(column.getColumWidth()),
						column.getColumSize()));
			}
		}
		if(q.isSetStyles()){xml.setStyles(xfStyles.build(column));}
		return xml;
	}
	
	public Layout build(GROUP group)
	{
		Layout xml = build();
		if(q.isSetStyles()){xml.setStyles(xfStyles.build(group));}
		return xml;
	}
	
	public Layout layout(STYLE style)
	{
		Layout xml = XmlLayoutFactory.build();
		
		xml.getColor().add(XmlColorFactory.build("background", style.getColorBackground()));
		if(q.isSetFont()){xml.setFont(xfFont.build(style));}
		
		return xml;
	}
	
	public static Layout build()
	{
		Layout xml = new Layout();						
		return xml;
	}
}