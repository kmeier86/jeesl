package org.jeesl.web.rest.system;

import org.jeesl.interfaces.facade.JeeslSystemNewsFacade;
import org.jeesl.interfaces.model.system.news.JeeslSystemNews;
import org.jeesl.interfaces.rest.system.news.JeeslSystemNewsRestExport;
import org.jeesl.interfaces.rest.system.news.JeeslSystemNewsRestImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SystemNewsRestService <L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
									USER extends EjbWithId>
					extends AbstractUtilsRest<L,D>
					implements JeeslSystemNewsRestExport,JeeslSystemNewsRestImport
{
	final static Logger logger = LoggerFactory.getLogger(SystemNewsRestService.class);
	
	@SuppressWarnings("unused")
	private JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews;
	
	private final Class<CATEGORY> cCategory;
	
	@SuppressWarnings("unused")
	private final Class<NEWS> cNews;
	
	private SystemNewsRestService(JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews,final String[] localeCodes, final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<NEWS> cNews)
	{
		super(fNews,localeCodes,cL,cD);
		this.fNews=fNews;
		
		this.cCategory=cCategory;
		this.cNews=cNews;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					NEWS extends JeeslSystemNews<L,D,CATEGORY,NEWS,USER>,
					USER extends EjbWithId>
		SystemNewsRestService<L,D,CATEGORY,NEWS,USER>
			factory(JeeslSystemNewsFacade<L,D,CATEGORY,NEWS,USER> fNews, final String[] localeCodes, final Class<L> cL, final Class<D> cD, Class<CATEGORY> cCategory, final Class<NEWS> cNews)
	{
		return new SystemNewsRestService<L,D,CATEGORY,NEWS,USER>(fNews,localeCodes,cL,cD,cCategory,cNews);
	}
	
	@Override public Aht exportSystemNewsCategories() {return super.exportStatus(cCategory);}
	@Override public DataUpdate importSystemNewsCategories(Aht categories){return super.importStatus(cCategory,null,categories);}
}