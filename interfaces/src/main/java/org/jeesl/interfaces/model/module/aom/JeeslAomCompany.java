package org.jeesl.interfaces.model.module.aom;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithNonUniqueCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface JeeslAomCompany <REALM extends JeeslAomRealm<?,?,REALM,?>,
									SCOPE extends JeeslAomScope<?,?,SCOPE,?>>
		extends Serializable,EjbSaveable,EjbWithName,EjbWithNonUniqueCode
{
	REALM getRealm();
	void setRealm(REALM realm);
	
	long getRealmIdentifier();
	void setRealmIdentifier(long realmIdentifier);
	
	String getUrl();
	void setUrl(String url);
	
	List<SCOPE> getScopes();
	void setScopes(List<SCOPE> scopes);
}