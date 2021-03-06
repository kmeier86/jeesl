package org.jeesl.controller.handler.ui.helper;

import java.io.Serializable;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UiHelperIoReport <L extends JeeslLang,D extends JeeslDescription,
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
										CDT extends JeeslStatus<CDT,L,D>,
										CW extends JeeslStatus<CW,L,D>,
										RT extends JeeslStatus<RT,L,D>,
										ENTITY extends EjbWithId,
										ATTRIBUTE extends EjbWithId,
										TL extends JeeslTrafficLight<L,D,TLS>,
										TLS extends JeeslStatus<TLS,L,D>,
										FILLING extends JeeslStatus<FILLING,L,D>,
										TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UiHelperIoReport.class);
		
	private REPORT report;
	private SHEET sheet;
	private GROUP group;
	
	private boolean showPanelReport; public boolean isShowPanelReport() {return showPanelReport;}
	private boolean showPanelSheet; public boolean isShowPanelSheet() {return showPanelSheet;}
	private boolean showPanelGroup; public boolean isShowPanelGroup() {return showPanelGroup;}

	public UiHelperIoReport()
	{
		showPanelReport = false;
		showPanelSheet = false;
		showPanelGroup = false;
	}
	
	public void check(REPORT report)
	{
		if(report!=null && EjbIdFactory.isUnSaved(report)){showPanelReport=true;}
		else if(this.report==null){showPanelReport = false;}
		else if(this.report!=null && report!=null) {showPanelReport = this.report.equals(report);}
		else {showPanelReport = false;}
		this.report=report;
	}
	
	public void check(SHEET sheet)
	{
		if(sheet!=null && EjbIdFactory.isUnSaved(sheet)){showPanelSheet=true;}
		else if(this.sheet==null){showPanelSheet = false;}
		else if(this.sheet!=null && sheet!=null) {showPanelSheet = this.sheet.equals(sheet);}
		else {showPanelSheet = false;}
		this.sheet=sheet;
	}
	
	public void check(GROUP group)
	{
		if(group!=null && EjbIdFactory.isUnSaved(group)){showPanelGroup=true;}
		else if(this.group==null){showPanelGroup = false;}
		else if(this.group!=null && group!=null) {showPanelGroup = this.group.equals(group);}
		else {showPanelGroup = false;}
		this.group=group;
	}
}