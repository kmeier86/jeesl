package org.jeesl.controller.handler.sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.tree.JeeslTree2Cache;
import org.jeesl.controller.handler.sb.tree.SbTree2Handler;
import org.jeesl.controller.handler.tree.TreeUpdateParameter;
import org.jeesl.interfaces.controller.handler.OutputXpathPattern;
import org.jeesl.interfaces.controller.handler.tree.JeeslTree2Store;
import org.jeesl.interfaces.controller.handler.tree.JeeslTreeSelected;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroDecade;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class SbDecadeHandler<HD extends JeeslHydroDecade, HY extends JeeslHydroYear> extends SbTree2Handler<HD, HY> implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(SbDecadeHandler.class);
	private static final long serialVersionUID = 1L;
	private List<HD> list1;
	public List<HD> getDecades(){return list1;}

	public HY getYear() {return l2;}


	public SbDecadeHandler(JeeslTreeSelected callback, JeeslTree2Cache<HD,HY> cache2, JeeslTree2Store<HD,HY> store2)
	{
		super(callback, cache2,store2);
		list1 =  new ArrayList<HD>();

		xpath1 = OutputXpathPattern.name;
		xpath2 = OutputXpathPattern.name;
	}

	protected void reset(int level)
	{
		if(level==1) {reset2(true,true);}
		if(level==2) {reset2(false,true);}
	}

	public void update(List<HD> decades)
	{
		//reset2(true,true);
		if(debugOnInfo) {logger.info("Global View, populating Decades");}
		list1.addAll(decades);
		selectGlobal();
	}

	private void selectGlobal()
	{
		if(debugOnInfo) {logger.info("Selecting Global");}

		if(getYear()!=null)
		{
			super.cascade2(getYear(), TreeUpdateParameter.build(true, true, true, true, true));
		}
		else
		{
			HD decade = list1.get(0);
			super.cascade1(decade, TreeUpdateParameter.build(false, true, true, true, true));
		}
	}

	@Override protected HD getParentForL2(HY child) {return (HD) child.getDecade();}

}
