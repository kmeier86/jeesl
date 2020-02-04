package org.jeesl.interfaces.model.system.io.dashboard;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.EjbWithLangDescription;
import org.jeesl.interfaces.model.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoDashboard <L extends JeeslLang, D extends JeeslDescription>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithLangDescription<L,D>
{	
	
}