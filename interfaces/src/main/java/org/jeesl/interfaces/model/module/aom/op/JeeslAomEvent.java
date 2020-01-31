package org.jeesl.interfaces.model.module.aom.op;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;

public interface JeeslAomEvent <COMPANY extends JeeslAomCompany<?,?>,
							ASSET extends JeeslAomAsset<?,ASSET,COMPANY,?,?>,
							TYPE extends JeeslAomEventType<?,?,TYPE,?>>
			extends Serializable,EjbSaveable,
					EjbWithParentAttributeResolver
				
					
{
	public enum Attributes{realm,realmIdentifier,parent}
	
	ASSET getParent();
	void setParent(ASSET parent);
	
	
	
	TYPE getType1();
	void setType1(TYPE type1);
	
	List<ASSET> getAssets();
	void setAssets(List<ASSET> assets);
}