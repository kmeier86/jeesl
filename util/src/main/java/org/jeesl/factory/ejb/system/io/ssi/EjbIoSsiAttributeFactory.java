package org.jeesl.factory.ejb.system.io.ssi;

import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;

public class EjbIoSsiAttributeFactory <MAPPING extends JeeslIoSsiMapping<?,ENTITY>,
										ATTRIBUTE extends JeeslIoSsiAttribute<MAPPING,ENTITY>,
										ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>>
{
	private final Class<ATTRIBUTE> cAttriubte;

	public EjbIoSsiAttributeFactory(final Class<ATTRIBUTE> cAttriubte)
	{
        this.cAttriubte = cAttriubte;
	}
	
	public ATTRIBUTE build(MAPPING mapping)
	{
		ATTRIBUTE ejb = null;
		try
		{
			ejb = cAttriubte.newInstance();
			ejb.setMapping(mapping);
	       
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
}