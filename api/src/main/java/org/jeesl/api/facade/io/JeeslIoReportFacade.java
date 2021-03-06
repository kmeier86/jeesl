package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
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

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoReportFacade <L extends JeeslLang,D extends JeeslDescription,
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
										TLS extends JeeslStatus<TLS,L,D>,
										FILLING extends JeeslStatus<FILLING,L,D>,
										TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>>
			extends JeeslFacade
{	
	REPORT load(REPORT report, boolean recursive);
	WORKBOOK load(WORKBOOK workbook);
	SHEET load(SHEET sheet, boolean recursive);
	GROUP load(GROUP group);
	TEMPLATE load(TEMPLATE template);
	
	SHEET fSheet(WORKBOOK workbook, String code) throws JeeslNotFoundException;
	
	void rmSheet(SHEET sheet) throws JeeslConstraintViolationException;
	void rmGroup(GROUP group) throws JeeslConstraintViolationException;
	void rmColumn(COLUMN column) throws JeeslConstraintViolationException;
	void rmRow(ROW row) throws JeeslConstraintViolationException;
	void rmCell(CELL cell) throws JeeslConstraintViolationException;
	
	List<REPORT> fReports(List<CATEGORY> categories, boolean showInvisibleEntities);
}