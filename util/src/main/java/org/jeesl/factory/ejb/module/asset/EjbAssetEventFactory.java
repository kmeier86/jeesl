package org.jeesl.factory.ejb.module.asset;

import java.util.Date;

import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.module.aom.op.JeeslAomEvent;
import org.jeesl.interfaces.model.module.aom.op.JeeslAomEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbAssetEventFactory<COMPANY extends JeeslAomCompany<?,?>,
								ASSET extends JeeslAomAsset<?,ASSET,COMPANY,?,?>,
								EVENT extends JeeslAomEvent<COMPANY,ASSET,ETYPE>,
								ETYPE extends JeeslAomEventType<?,?,ETYPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbAssetEventFactory.class);
	
	private final AssetFactoryBuilder<?,?,?,COMPANY,?,ASSET,?,?,EVENT,ETYPE> fbAsset;
	
    public EjbAssetEventFactory(final AssetFactoryBuilder<?,?,?,COMPANY,?,ASSET,?,?,EVENT,ETYPE> fbAsset)
    {
        this.fbAsset = fbAsset;
    }
	
	public EVENT build(ASSET asset)
	{
		try
		{
			EVENT ejb = fbAsset.getClassEvent().newInstance();
			ejb.setRecord(new Date());
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void converter(JeeslFacade facade, EVENT event)
	{
		event.setType(facade.find(fbAsset.getClassEventType(),event.getType()));
	}
}