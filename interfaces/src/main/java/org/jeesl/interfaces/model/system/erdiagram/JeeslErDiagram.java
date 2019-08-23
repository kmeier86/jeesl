package org.jeesl.interfaces.model.system.erdiagram;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslErDiagram <L extends UtilsLang, D extends UtilsDescription,
								C extends UtilsStatus<C,L,D>,
								ERD extends JeeslErDiagram<L,D,C,ERD>>
		extends Serializable,EjbSaveable,EjbWithLang<L>,EjbWithDescription<D>,EjbWithCode,EjbWithPosition
{
	C getCategory();
	void setCategory(C category);

	String getDotGraph();
	void setDotGraph(String value);

	Boolean getDocumentation();
	void setDocumentation(Boolean documentation);
}