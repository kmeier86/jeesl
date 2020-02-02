package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.asset.EjbAssetCompanyFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetEventFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetFactory;
import org.jeesl.factory.ejb.module.asset.EjbAssetTypeFactory;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.core.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.op.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.op.JeeslAomEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AssetFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								REALM extends JeeslAomRealm<L,D,REALM,?>,
								COMPANY extends JeeslAomCompany<REALM,SCOPE>,
								SCOPE extends JeeslAomScope<L,D,SCOPE,?>,
								ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,ASTATUS,ATYPE>,
								ASTATUS extends JeeslAomStatus<L,D,ASTATUS,?>,
								ATYPE extends JeeslAomType<L,D,REALM,ATYPE,?>,
								EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE>,
								ETYPE extends JeeslAomEventType<L,D,ETYPE,?>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(AssetFactoryBuilder.class);
	
	private final Class<REALM> cRealm; public Class<REALM> getClassRealm() {return cRealm;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<ASSET> cAsset; public Class<ASSET> getClassAsset() {return cAsset;}
	private final Class<COMPANY> cCompany; public Class<COMPANY> getClassCompany() {return cCompany;}
	private final Class<ASTATUS> cStatus; public Class<ASTATUS> getClassStatus() {return cStatus;}
	private final Class<ATYPE> cAssetType; public Class<ATYPE> getClassAssetType() {return cAssetType;}
	private final Class<EVENT> cEvent; public Class<EVENT> getClassEvent() {return cEvent;}
	private final Class<ETYPE> cEventType; public Class<ETYPE> getClassEventType() {return cEventType;}

	public AssetFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<REALM> cRealm,
								final Class<ASSET> cAsset,
								final Class<COMPANY> cCompany,
								final Class<SCOPE> cScope,
								final Class<ASTATUS> cStatus,
								final Class<ATYPE> cAssetType,
								final Class<EVENT> cEvent,
								final Class<ETYPE> cEventType)
	{       
		super(cL,cD);
		this.cRealm=cRealm;
		this.cCompany=cCompany;
		this.cScope=cScope;
		this.cAsset=cAsset;
		this.cStatus=cStatus;
		this.cAssetType=cAssetType;
		this.cEvent=cEvent;
		this.cEventType=cEventType;
	}
	
	public EjbAssetCompanyFactory<REALM,COMPANY,SCOPE> ejbManufacturer() {return new EjbAssetCompanyFactory<>(cCompany);}
	public EjbAssetTypeFactory<REALM,ATYPE> ejbType() {return new EjbAssetTypeFactory<>(cAssetType);}
	public EjbAssetFactory<REALM,COMPANY,SCOPE,ASSET,ASTATUS,ATYPE> ejbAsset() {return new EjbAssetFactory<>(this);}
	public EjbAssetEventFactory<COMPANY,ASSET,EVENT,ETYPE> ejbEvent() {return new EjbAssetEventFactory<>(this);}
}