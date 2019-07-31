package org.jeesl.interfaces.model.module.bb.post;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslBbPost<THREAD extends JeeslBbThread<?>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
								USER extends EjbWithEmail>
						extends Serializable,
								EjbSaveable,EjbWithRecord,EjbWithParentAttributeResolver
{	
	public enum Attributes{thread,markup}
	
	THREAD getThread();
	void setThread(THREAD thread);
	
	M getMarkup();
	void setMarkup(M markup);
	
	USER getUser();
	void setUser(USER user);
}