package org.jeesl.interfaces.model.module.aom.op;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslAomEvent <COMPANY extends JeeslAomCompany<?,?>,
							ASSET extends JeeslAomAsset<?,ASSET,COMPANY,?,?>>
			extends Serializable,EjbSaveable,
					EjbWithRecord
				
					
{
//	List<ASSET> getAssets();
//	void setAsset(List<ASSET> assets);	
}