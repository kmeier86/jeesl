package org.jeesl.factory.ejb.module.asset;

import java.util.UUID;

import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.JeeslAomRealm;
import org.jeesl.interfaces.model.module.aom.JeeslAomScope;
import org.jeesl.interfaces.model.module.aom.JeeslAomStatus;
import org.jeesl.interfaces.model.module.aom.JeeslAomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbAssetFactory<REALM extends JeeslAomRealm<?,?,REALM,?>,
							ASSET extends JeeslAomAsset<REALM,ASSET,COMPANY,STATUS,TYPE>,
							COMPANY extends JeeslAomCompany<REALM,SCOPE>,
							SCOPE extends JeeslAomScope<?,?,SCOPE,?>,
							STATUS extends JeeslAomStatus<?,?,STATUS,?>,
							TYPE extends JeeslAomType<?,?,REALM,TYPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetFactory.class);
	
	private final Class<ASSET> cAsset;
	
    public EjbAssetFactory(final Class<ASSET> cAsset)
    {
        this.cAsset = cAsset;
    }
	
	public <RREF extends EjbWithId> ASSET build(REALM realm, RREF ref, ASSET parent, STATUS status, TYPE type1)
	{
		try
		{
			ASSET ejb = cAsset.newInstance();
			ejb.setRealm(realm);
			ejb.setRealmIdentifier(ref.getId());
			ejb.setCode(UUID.randomUUID().toString());
			ejb.setParent(parent);
			ejb.setStatus(status);
			ejb.setType1(type1);
			ejb.setName("");
			ejb.setRemark("");
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void converter(ASSET asset)
	{
		
	}
}