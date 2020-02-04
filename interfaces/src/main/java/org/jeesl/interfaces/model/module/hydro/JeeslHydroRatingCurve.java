package org.jeesl.interfaces.model.module.hydro;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.util.date.EjbWithDateRange;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbCrud;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslHydroRatingCurve <L extends JeeslLang, D extends JeeslDescription,
										STATION extends EjbWithId,
										UNIT extends JeeslHydroRatingUnit<L,D,UNIT,?>
								>
					extends Serializable,EjbSaveable,EjbCrud,EjbRemoveable,
							EjbWithName,EjbWithDateRange
							
{
	STATION getStation();
	void setStation(STATION station);
	
	UNIT getGaugeUnit();
	void setGaugeUnit(UNIT gaugeUnit);
	
	UNIT getDischargeUnit();
	void setDischargeUnit(UNIT dischargeUnit);
}