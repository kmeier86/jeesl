package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSystemERDiagramFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.system.ERDiagramFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.erdiagram.JeeslERDiagram;
import org.jeesl.interfaces.model.system.erdiagram.JeeslERDiagramCategory;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.util.comparator.ejb.system.ERDiagramComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSystemERDiagramBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											C extends JeeslERDiagramCategory<L,D,C,?>,
											ERD extends JeeslERDiagram<L,D,C,ERD>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSystemERDiagramBean.class);

	private JeeslSystemERDiagramFacade<L,D,C,ERD> fERdiagram;
	private final ERDiagramFactoryBuilder<L,D,C,ERD> fbERDiagram;

	private final SbMultiHandler<C> sbhCategory; public SbMultiHandler<C> getSbhCategory() {return sbhCategory;}
	private final Comparator<ERD> comparatorERDiagram;

	protected List<ERD> erDiagrams; public List<ERD> getErDiagrams() {return erDiagrams;}

	protected ERD erDiagram;

	public ERD getErDiagram() {
		return erDiagram;
	}

	public void setErDiagram(ERD erDiagram) {
		this.erDiagram = erDiagram;
	}

	public AbstractSystemERDiagramBean(final ERDiagramFactoryBuilder<L,D,C,ERD> fbERDiagram)
	{
		super(fbERDiagram.getClassL(),fbERDiagram.getClassD());
		this.fbERDiagram = fbERDiagram;
		sbhCategory = new SbMultiHandler<C>(fbERDiagram.getClassCategory(),this);
		comparatorERDiagram = (new ERDiagramComparator<L,D,C,ERD>()).factory(ERDiagramComparator.Type.category);
	}

	public void initSuper(JeeslSystemERDiagramFacade<L,D,C,ERD> fERDiagram)
	{
		this.fERdiagram=fERDiagram;

		sbhCategory.setList(fERDiagram.allOrderedPositionVisible(fbERDiagram.getClassCategory()));
		sbhCategory.selectAll();
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+": "+fbERDiagram.getClassCategory().getSimpleName()+" "+sbhCategory.getSelected().size()+"/"+sbhCategory.getList().size());}
		refreshList();
	}

	private void refreshList()
	{
		erDiagrams = fERdiagram.all(fbERDiagram.getClassERDiagram());
		Collections.sort(erDiagrams,comparatorERDiagram);
	}

	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+" toggled, but NYI");}
	}

	public void selectERDiagram() throws UtilsNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(erDiagram));}
		erDiagram = fERdiagram.find(fbERDiagram.getClassERDiagram(), erDiagram);
	}

	public void saveERDiagram() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(erDiagram));}
		if(erDiagram.getCategory()!=null){erDiagram.setCategory(fERdiagram.find(fbERDiagram.getClassCategory(),erDiagram.getCategory()));}
		erDiagram = fERdiagram.save(erDiagram);
		refreshList();
	}
}