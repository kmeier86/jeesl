package org.jeesl.interfaces.model.system.io.revision.er;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslRevisionDiagram <L extends UtilsLang, D extends UtilsDescription,
										C extends JeeslRevisionCategory<L,D,C,?>>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,EjbWithLang<L>,EjbWithDescription<D>,EjbWithCode,EjbWithPosition
{
	C getCategory();
	void setCategory(C category);

	String getDotGraph();
	void setDotGraph(String value);

	boolean isDocumentation();
	void setDocumentation(boolean documentation);

	boolean isDotManual();
	void setDotManual(boolean dotManual);
}