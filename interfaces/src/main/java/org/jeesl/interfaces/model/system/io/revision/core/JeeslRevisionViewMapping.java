package org.jeesl.interfaces.model.system.io.revision.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;

public interface JeeslRevisionViewMapping<RV extends JeeslRevisionView<?,?,?>,
									RE extends JeeslRevisionEntity<?,?,?,REM,?,?>,
									REM extends JeeslRevisionEntityMapping<?,?,RE>>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithPositionVisible
{					
	RV getView();
	void setView(RV view);
	
	RE getEntity();
	void setEntity(RE entity);
	
	REM getEntityMapping();
	void setEntityMapping(REM entityMapping);
}