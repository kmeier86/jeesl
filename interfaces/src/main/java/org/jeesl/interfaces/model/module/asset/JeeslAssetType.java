package org.jeesl.interfaces.model.module.asset;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslAssetType <L extends UtilsLang, D extends UtilsDescription,
							REALM extends JeeslAssetRealm<L,D,REALM,?>,
							TYPE extends JeeslAssetType<L,D,REALM,TYPE>>
			extends Serializable,EjbSaveable,EjbWithCode
					
{
	public enum Attributes{realm,realmIdentifier,parent}
	
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	TYPE getParent();
	void setParent(TYPE parent);
}