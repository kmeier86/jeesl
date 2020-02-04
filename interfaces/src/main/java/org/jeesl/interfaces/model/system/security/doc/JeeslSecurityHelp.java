package org.jeesl.interfaces.model.system.security.doc;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSecurityHelp<L extends JeeslLang, D extends JeeslDescription,
									V extends JeeslSecurityView<L,D,?,?,?,?>>
			extends Serializable,EjbSaveable,
					EjbWithPositionVisible,
					EjbWithLang<L>,EjbWithDescription<D>
{	
	V getView();
	void setView(V view);
}