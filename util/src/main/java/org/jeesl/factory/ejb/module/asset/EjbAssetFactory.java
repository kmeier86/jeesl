package org.jeesl.factory.ejb.module.asset;

import java.util.UUID;

import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.module.asset.JeeslAssetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbAssetFactory<REALM extends JeeslAssetRealm<?,?,REALM,?>,
							ASSET extends JeeslAsset<REALM,ASSET,MANU,STATUS,TYPE>,
							MANU extends JeeslAssetManufacturer<REALM>,
							STATUS extends JeeslAssetStatus<?,?,STATUS,?>,
							TYPE extends JeeslAssetType<?,?,REALM,TYPE,?>>
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