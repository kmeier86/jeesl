package org.jeesl.interfaces.model.module.asset;

import java.io.Serializable;

import org.jeesl.interfaces.model.with.status.JeeslWithStatus;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslAsset <REALM extends JeeslAssetRealm<?,?,REALM,?>,
							ASSET extends JeeslAsset<REALM,ASSET,AS>,
							AS extends JeeslAssetStatus<?,?,AS,?>>
			extends Serializable,EjbSaveable,
					EjbWithName
					,JeeslWithStatus<AS>
					
{
	public enum Attributes{realm,realmIdentifier,parent}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	ASSET getParent();
	void setParent(ASSET parent);
}