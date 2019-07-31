package org.jeesl.factory.ejb.module.bb;

import java.util.Date;

import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbBbThreadFactory<BB extends JeeslBbBoard<?,?,?,BB,?,?>,
								THREAD extends JeeslBbThread<BB>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbBbThreadFactory.class);
	
	private final Class<THREAD> cThread;
	
    public EjbBbThreadFactory(final Class<THREAD> cThread)
    {
        this.cThread = cThread;
    }
	
	public THREAD build(BB board)
	{
		try
		{
			THREAD ejb = cThread.newInstance();
			ejb.setBoard(board);
			ejb.setRecord(new Date());
		
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
}