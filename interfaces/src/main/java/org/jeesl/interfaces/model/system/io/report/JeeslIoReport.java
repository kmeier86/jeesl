package org.jeesl.interfaces.model.system.io.report;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoReport<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,WORKBOOK,SHEET,GROUP,COLUMN>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	WORKBOOK getWorkbook();
	void setWorkbook(WORKBOOK workbook);
}