package org.jeesl.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.jeesl.interfaces.bean.op.OpEntityBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class OpEntitySelectionHandler <T extends EjbWithId> extends AbstractOpSelectionHandler<T> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;

    public OpEntitySelectionHandler(OpEntityBean bean) {this(bean,new ArrayList<T>());}
    public OpEntitySelectionHandler(OpEntityBean bean, List<T> opEntites) {super(bean,opEntites);}
    
    public OpEntitySelectionHandler(OpEntityBean bean, List<T> opEntites, String xPath)
    {
    		super(bean,opEntites);
    		showName=true;
    }
}