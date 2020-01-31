package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetCompanyFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetTypeFactory;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetCompany;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetScope;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.asset.JeeslAssetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AssetFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								REALM extends JeeslAssetRealm<L,D,REALM,?>,
								ASSET extends JeeslAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
								COMPANY extends JeeslAssetCompany<REALM,SCOPE>,
								SCOPE extends JeeslAssetScope<L,D,SCOPE,?>,
								STATUS extends JeeslAssetStatus<L,D,STATUS,?>,
								TYPE extends JeeslAssetType<L,D,REALM,TYPE,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(AssetFactoryBuilder.class);
	
	private final Class<REALM> cRealm; public Class<REALM> getClassRealm() {return cRealm;}
	private final Class<ASSET> cAsset; public Class<ASSET> getClassAsset() {return cAsset;}
	private final Class<COMPANY> cCompany; public Class<COMPANY> getClassCompany() {return cCompany;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus() {return cStatus;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType() {return cType;}

	public AssetFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<REALM> cRealm,
								final Class<ASSET> cAsset,
								final Class<COMPANY> cCompany,
								final Class<STATUS> cStatus,
								final Class<TYPE> cType)
	{       
		super(cL,cD);
		this.cRealm=cRealm;
		this.cAsset=cAsset;
		this.cCompany=cCompany;
		this.cStatus=cStatus;
		this.cType=cType;
	}
	
	public EjbAssetCompanyFactory<REALM,COMPANY,SCOPE> ejbManufacturer() {return new EjbAssetCompanyFactory<>(cCompany);}
	public EjbAssetTypeFactory<REALM,TYPE> ejbType() {return new EjbAssetTypeFactory<>(cType);}
	public EjbAssetFactory<REALM,ASSET,COMPANY,SCOPE,STATUS,TYPE> ejbAsset() {return new EjbAssetFactory<>(cAsset);}
}