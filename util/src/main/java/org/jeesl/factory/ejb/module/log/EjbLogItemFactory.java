package org.jeesl.factory.ejb.module.log;

import org.jeesl.interfaces.model.module.log.JeeslLog;
import org.jeesl.interfaces.model.module.log.JeeslLogImpact;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.jeesl.interfaces.model.module.log.JeeslLogScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbLogItemFactory<L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLog<ITEM>,
								ITEM extends JeeslLogItem<L,D,?,?,LOG,IMPACT,SCOPE,USER>,
								IMPACT extends JeeslLogImpact<L,D,IMPACT,?>,
								SCOPE extends JeeslLogScope<L,D,SCOPE,?>,
								USER extends EjbWithId
								>
{
	final static Logger logger = LoggerFactory.getLogger(EjbLogItemFactory.class);
	
	private final Class<ITEM> cItem;
    
	public EjbLogItemFactory(final Class<ITEM> cItem)
	{  
        this.cItem = cItem;
	}
	    
	public ITEM build()
	{
		ITEM ejb = null;
		try
		{
			ejb = cItem.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}