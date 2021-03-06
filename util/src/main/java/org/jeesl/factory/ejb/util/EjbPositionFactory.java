package org.jeesl.factory.ejb.util;

import java.util.List;

import org.jeesl.interfaces.model.with.position.EjbWithPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbPositionFactory
{
	final static Logger logger = LoggerFactory.getLogger(EjbPositionFactory.class);
    
	public static <T extends EjbWithPosition> void next(T ejb, List<T> list)
	{
		if(list==null) {ejb.setPosition(1);}
		else {ejb.setPosition(list.size()+1);}
	}
	
	public static <T extends EjbWithPosition> void calcNext(T ejb, List<T> list)
	{
		if(list==null) {ejb.setPosition(1);}
		else
		{
			int max = 0;
			for(T t : list) {if(t.getPosition()>max){max=t.getPosition();}}
			ejb.setPosition(max+1);
		}
	}
}