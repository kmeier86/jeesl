package org.jeesl.interfaces.model.module.bb.post;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslBbPost<THREAD extends JeeslBbThread<?>,
//								MARKUP extends JeeslIoCmsMarkup<IoCmsMarkupType>,
//								MT extends JeeslIoCmsMarkup<IoCmsMarkupType>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbWithId,
								EjbSaveable
{	
	public enum Attributes{scope,refId}
	
	
	
	
}