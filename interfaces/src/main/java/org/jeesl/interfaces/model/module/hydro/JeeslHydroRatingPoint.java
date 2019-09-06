package org.jeesl.interfaces.model.module.hydro;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbCrud;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslHydroRatingPoint <L extends UtilsLang, D extends UtilsDescription,
										UNIT extends JeeslHydroRatingUnit<L,D,UNIT,?>
								>
					extends Serializable,EjbSaveable,EjbRemoveable,
							EjbWithId,EjbCrud,
							EjbWithRecord
							
{

	public enum Attributes{decade}

	UNIT getGaugeUnit();
	void setGaugeUnit(UNIT gaugeUnit);
	
	UNIT getDischargeUnit();
	void setDischargeUnit(UNIT dischargeUnit);
}