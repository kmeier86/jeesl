package org.jeesl.interfaces.model.module.bb.post;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslBbPost<THREAD extends JeeslBbThread<?>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,
								EjbSaveable
{	
	public enum Attributes{markup}
	
	
}