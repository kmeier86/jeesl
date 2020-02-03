package org.jeesl.interfaces.model.module.aom.event;

import java.io.Serializable;
import java.util.List;

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
	public enum Attributes{assets}
	
	List<ASSET> getAssets();
	void setAssets(List<ASSET> assets);
	
	COMPANY getVendor();
	void setVendor(COMPANY vendor);
	
	Double getAmountProcurement();
	void setAmountProcurement(Double amountProcurement);
}