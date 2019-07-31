package org.jeesl.factory.ejb.module.bb;

import java.util.Date;

import org.jeesl.interfaces.model.module.bb.post.JeeslBbPost;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbThread;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class EjbBbPostFactory<THREAD extends JeeslBbThread<?>,
								POST extends JeeslBbPost<THREAD,M,MT,USER>,
								M extends JeeslMarkup<MT>,
								MT extends JeeslIoCmsMarkupType<?,?,MT,?>,
								USER extends EjbWithEmail>
{
	final static Logger logger = LoggerFactory.getLogger(EjbBbPostFactory.class);
	
	private final Class<POST> cPost;
	private final Class<M> cMarkup;
	
    public EjbBbPostFactory(final Class<POST> cPost, final Class<M> cMarkup)
    {
        this.cPost = cPost;
        this.cMarkup = cMarkup;
    }
	
	public POST build(String localeCode, THREAD thread, MT markupType, USER user)
	{
		try
		{
			M markup = cMarkup.newInstance();
			markup.setLkey(localeCode);
			markup.setType(markupType);
			
			POST ejb = cPost.newInstance();
			ejb.setMarkup(markup);
			ejb.setThread(thread);
			ejb.setRecord(new Date());
			ejb.setUser(user);
			
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}