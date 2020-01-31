package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetCompanyFactory;
import org.jeesl.interfaces.model.module.aom.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.JeeslAomScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class OmFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								REALM extends JeeslAomRealm<L,D,REALM,?>,
								COMPANY extends JeeslAomCompany<REALM,SCOPE>,
								SCOPE extends JeeslAomScope<L,D,SCOPE,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(OmFactoryBuilder.class);
	
	private final Class<COMPANY> cCompany; public Class<COMPANY> getClassCompany() {return cCompany;}

	public OmFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<COMPANY> cCompany)
	{       
		super(cL,cD);
		this.cCompany=cCompany;

	}
	
	public EjbAssetCompanyFactory<REALM,COMPANY,SCOPE> ejbCompany(){return new EjbAssetCompanyFactory<>(cCompany);}
}