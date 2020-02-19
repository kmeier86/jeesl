package org.jeesl.controller.provider;

import org.jeesl.api.bean.JeeslLabelResolver;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacadeTranslationProvider <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									RE extends JeeslRevisionEntity<L,D,?,?,RA,?>, RA extends JeeslRevisionAttribute<L,D,RE,?,?>>
							implements JeeslLabelResolver
{
	final static Logger logger = LoggerFactory.getLogger(FacadeTranslationProvider.class);
	
	private final JeeslIoRevisionFacade<?,?,?,?,?,?,?,RE,?,?,?,?,?> fRevision;
	private final IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?,?> fbRevision;
	
	public FacadeTranslationProvider(IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?,?> fbRevision,
								JeeslIoRevisionFacade<?,?,?,?,?,?,?,RE,?,?,?,?,?> fRevision)
	{
		this.fbRevision=fbRevision;
		this.fRevision=fRevision;
	}

	@Override public <E extends Enum<E>> String xpath(String localeCode, Class<?> c, E code)
	{
		try
		{
			RE entity = fRevision.fByCode(fbRevision.getClassEntity(), c.getName());
			entity = fRevision.load(fbRevision.getClassEntity(), entity);
			logger.info(entity.toString()+" for "+code.toString());
			for(RA ra : entity.getAttributes())
			{
				if(ra.getCode().equals(code.toString()) && ra.getXpath()!=null && ra.getXpath().trim().length()>0)
				{
					return ra.getXpath();
				}
//				logger.info("\t"+ra.toString()+" "+ra.getCode());
			}
		}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
		logger.warn("No XPATH devfined for "+c.getSimpleName()+" and attribute:"+code.toString());
		return "@id";
	}
	
	@Override public String entity(String localeCode, Class<?> c)
	{
		try
		{
			RE entity = fRevision.fByCode(fbRevision.getClassEntity(), c.getName());
			return entity.getName().get(localeCode).getLang();
		}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
		return c.getSimpleName();
	}
}