package org.jeesl.interfaces.model.module.hydro;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.util.date.EjbWithValidFromUntil;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslHydroYear <L extends UtilsLang, D extends UtilsDescription,
								HD extends UtilsStatus<HD,L,D>,
								HY extends JeeslHydroYear<L,D,HD,HY>>
					extends Serializable,EjbSaveable,EjbRemoveable,
							EjbWithParentAttributeResolver,
							EjbWithLang<L>,EjbWithDescription<D>,
							EjbWithCode,EjbWithPosition,
							EjbWithValidFromUntil
{

	public enum Attributes{decade}

	void setDecade(HD decade);
	HD getDecade();
}