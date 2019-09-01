package org.jeesl.interfaces.model.system.io.revision.entity;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;

public interface JeeslRevisionEntityRelation <L extends UtilsLang, D extends UtilsDescription,
												S extends UtilsStatus<S,L,D>,
												G extends JeeslGraphic<L,D,?,?,?>>
		extends Serializable,EjbPersistable,UtilsWithSymbol,
					JeeslOptionRestDownload,EjbWithCodeGraphic<G>,
					UtilsStatusFixedCode,UtilsStatus<S,L,D>
{	
	public enum Code{MtoO,OtoO,OtoM,MtoM}
}