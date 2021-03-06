package org.jeesl.factory.xml.system.io.report;

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
import net.sf.ahtutils.xml.report.DataAssociation;
import static org.hibernate.criterion.Projections.property;
import org.jeesl.api.controller.ImportStrategy;
import org.jeesl.api.controller.ValidationStrategy;


public class XmlDataAssociationFactory <L extends JeeslLang,D extends JeeslDescription,
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
								STYLE extends JeeslReportStyle<L,D>,CDT extends JeeslStatus<CDT,L,D>,CW extends JeeslStatus<CW,L,D>,
								RT extends JeeslStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends JeeslStatus<TLS,L,D>,
								FILLING extends JeeslStatus<FILLING,L,D>,
								TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>
								>
{
    final static Logger logger = LoggerFactory.getLogger(XmlDataAssociationFactory.class);

    /**
    * Add a simple data association to import property from column.
    * @param column The column nr in spreadsheet, starting with 0
    * @param property The property of the target class
    * @return Initialized data association
    */
    public static DataAssociation buildSimpleAssociation(int column, String property)
    {
        DataAssociation association = new DataAssociation();
        association.setColumn("" +column);
        association.setProperty(property);
        return association;
    }

    /**
    * Add a data association for import of property in given column that is handled by the given class.
    * @param column The column nr in spreadsheet, starting with 0
    * @param property The property of the target class
    * @param handler The handler class that implements the ImportStrategy interface
    * @return Initialized data association
    */
    public static DataAssociation buildHandledAssociation(int column, String property, Class<? extends ImportStrategy> handler)
    {
        DataAssociation association = new DataAssociation();
        association.setColumn("" +column);
        association.setProperty(property);
        association.setHandledBy(handler.getName());
        return association;
    }
    
    /**
    * Add a validation by the given class.
    * @param handler The handler class that implements the ValidationStrategy interface
    * @return Data association with validation parameter set
    */
    public static DataAssociation addValidation(DataAssociation association, Class<? extends ValidationStrategy> handler)
    {
	association.setValidatedBy(handler.getName());
        return association;
    }
}