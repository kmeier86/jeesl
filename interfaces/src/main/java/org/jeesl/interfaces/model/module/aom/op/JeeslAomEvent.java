package org.jeesl.interfaces.model.module.aom.op;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.with.status.JeeslWithType;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslAomEvent <COMPANY extends JeeslAomCompany<?,?>,
								ASSET extends JeeslAomAsset<?,ASSET,COMPANY,?,?>,
								ETYPE extends JeeslAomEventType<?,?,ETYPE,?>>
			extends Serializable,EjbSaveable,
					EjbWithRecord,JeeslWithType<ETYPE>
				
					
{
//	List<ASSET> getAssets();
//	void setAsset(List<ASSET> assets);	
}