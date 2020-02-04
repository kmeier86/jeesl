package org.jeesl.interfaces.model.system.locale.status;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParent;
import org.jeesl.interfaces.model.with.position.EjbWithPositionVisible;

import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithImage;
import net.sf.ahtutils.interfaces.model.with.EjbWithImageAlt;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslStatus<S extends JeeslStatus<S,L,D>, L extends JeeslLang, D extends JeeslDescription>
			extends EjbRemoveable,EjbWithId,EjbWithCode,EjbWithPositionVisible,EjbWithImage,EjbWithImageAlt,
						EjbWithLangDescription<L,D>,EjbWithParent
{	
	enum EjbAttributes{code,parent}
	
	String getStyle();
	void setStyle(String style);
	
	String getSymbol();
	void setSymbol(String symbol);
}