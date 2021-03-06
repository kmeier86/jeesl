package org.jeesl.factory.ejb.system.io.report;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.controller.db.updater.JeeslDbDescriptionUpdater;
import org.jeesl.controller.db.updater.JeeslDbLangUpdater;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
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
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportQueryType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.util.query.xpath.ReportXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Row;
import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class EjbIoReportRowFactory<L extends JeeslLang,D extends JeeslDescription,
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
								TLS extends JeeslStatus<TLS,L,D>,
								FILLING extends JeeslStatus<FILLING,L,D>,
								TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoReportRowFactory.class);
	
	final Class<ROW> cRow;
	final Class<TEMPLATE> cTemplate;
	final Class<CDT> cDataType;
	final Class<RT> cRt;
	
	
	private JeeslDbLangUpdater<ROW,L> dbuLang;
	private JeeslDbDescriptionUpdater<ROW,D> dbuDescription;
    
	public EjbIoReportRowFactory(final Class<L> cL,final Class<D> cD,final Class<ROW> cRow, final Class<TEMPLATE> cTemplate, final Class<CDT> cDataType, final Class<RT> cRt)
	{       
        this.cRow = cRow;
        this.cTemplate = cTemplate;
        this.cRt = cRt;
        this.cDataType=cDataType;
        dbuLang = JeeslDbLangUpdater.factory(cRow, cL);
        dbuDescription = JeeslDbDescriptionUpdater.factory(cRow, cD);
	}
	    
	public ROW build(SHEET sheet)
	{
		ROW ejb = null;
		try
		{
			ejb = cRow.newInstance();
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setSheet(sheet);
			ejb.setPosition(1);
			ejb.setVisible(false);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public ROW build(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport, SHEET sheet, Row row) throws JeeslNotFoundException
	{
		ROW ejb = null;
		try
		{
			ejb = cRow.newInstance();
			ejb.setCode(row.getCode());
			ejb.setSheet(sheet);
			ejb = update(fReport,ejb,row);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
		
	public ROW update(JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> fReport, ROW eRow, Row xRow) throws JeeslNotFoundException
	{
		CDT eDataType = null;if(xRow.isSetDataType()){eDataType = fReport.fByCode(cDataType, xRow.getDataType().getCode());}
		TEMPLATE eTemplate = null;if(xRow.isSetTemplate()){eTemplate = fReport.fByCode(cTemplate, xRow.getTemplate().getCode());}
		
		eRow.setType(fReport.fByCode(cRt, xRow.getType().getCode()));
		eRow.setPosition(xRow.getPosition());
		eRow.setVisible(xRow.isVisible());
		eRow.setDataType(eDataType);
		eRow.setTemplate(eTemplate);
		
		if(xRow.isSetQueries())
		{			
			try{eRow.setQueryCell(ReportXpath.getQuery(JeeslReportQueryType.Row.cell.toString(), xRow.getQueries()).getValue());}
			catch (ExlpXpathNotFoundException e) {eRow.setQueryCell(null);}
		}
		if(xRow.isSetLayout())
		{
			if(xRow.getLayout().isSetOffset())
			{
				eRow.setOffsetRows(xRow.getLayout().getOffset().getRows());
				eRow.setOffsetColumns(xRow.getLayout().getOffset().getColumns());
			}
		}
		
		
		return eRow;
	}
		
	public ROW updateLD(JeeslFacade fUtils, ROW eRow, Row xRow) throws JeeslConstraintViolationException, JeeslLockingException
	{
		eRow=dbuLang.handle(fUtils, eRow, xRow.getLangs());
		eRow = fUtils.save(eRow);
		eRow=dbuDescription.handle(fUtils, eRow, xRow.getDescriptions());
		eRow = fUtils.save(eRow);
		return eRow;
	}
	
	public List<ROW> toListVisibleRows(SHEET sheet)
	{
		List<ROW> list = new ArrayList<ROW>();
		for(ROW r : sheet.getRows())
		{
			if(r.isVisible())
			{
				list.add(r);
			}
		}
		return list;
	}

}