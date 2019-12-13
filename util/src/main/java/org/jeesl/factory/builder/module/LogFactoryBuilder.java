package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.log.EjbLogFactory;
import org.jeesl.factory.ejb.module.log.EjbLogItemFactory;
import org.jeesl.interfaces.model.module.log.JeeslLog;
import org.jeesl.interfaces.model.module.log.JeeslLogImpact;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.jeesl.interfaces.model.module.log.JeeslLogScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class LogFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLog<ITEM>,
								ITEM extends JeeslLogItem<L,D,?,?,LOG,IMPACT,SCOPE,USER>,
								IMPACT extends JeeslLogImpact<L,D,IMPACT,?>,
								SCOPE extends JeeslLogScope<L,D,SCOPE,?>,
								USER extends EjbWithId
								>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LogFactoryBuilder.class);
	
	private final Class<LOG> cLog;
	private final Class<ITEM> cItem;

	public LogFactoryBuilder(final Class<L> cL,final Class<D> cD,final Class<LOG> cLog, final Class<ITEM> cItem)
	{       
		super(cL,cD);
        this.cLog = cLog;
        this.cItem = cItem;
	}
	
	public EjbLogFactory<LOG> log(){return new EjbLogFactory<>(cLog);}
	public EjbLogItemFactory<L,D,LOG,ITEM,IMPACT,SCOPE,USER> item(){return new EjbLogItemFactory<>(cItem);}
}