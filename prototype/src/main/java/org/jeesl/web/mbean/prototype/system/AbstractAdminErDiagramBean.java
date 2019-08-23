package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.JeeslErDiagramFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.system.erdiagram.ErDiagramFactoryBuilder;
import org.jeesl.factory.ejb.system.erdiagram.EjbErDiagramFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.erdiagram.JeeslErDiagram;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.util.comparator.ejb.system.erdiagram.ErDiagramComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminErDiagramBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											RC extends JeeslRevisionCategory<L,D,RC,?>,
											ERD extends JeeslErDiagram<L,D,RC,ERD>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminErDiagramBean.class);

	private JeeslErDiagramFacade<L,D,RC,ERD> fErDiagram;
	private final ErDiagramFactoryBuilder<L,D,RC,ERD> fbErDiagram;

	private EjbErDiagramFactory<L,D,RC,ERD> efErDiagram;

	private final SbMultiHandler<RC> sbhCategory; public SbMultiHandler<RC> getSbhCategory() {return sbhCategory;}
	private final Comparator<ERD> comparatorERDiagram;

	protected List<ERD> erDiagrams; public List<ERD> getErDiagrams() {return erDiagrams;}

	protected ERD erDiagram;

	public ERD getErDiagram() {
		return erDiagram;
	}

	public void setErDiagram(ERD erDiagram) {
		this.erDiagram = erDiagram;
	}

	public AbstractAdminErDiagramBean(final ErDiagramFactoryBuilder<L,D,RC,ERD> fbERDiagram)
	{
		super(fbERDiagram.getClassL(),fbERDiagram.getClassD());
		this.fbErDiagram = fbERDiagram;
		this.efErDiagram = this.fbErDiagram.erDiagram();
		sbhCategory = new SbMultiHandler<RC>(fbERDiagram.getClassCategory(),this);
		comparatorERDiagram = (new ErDiagramComparator<L,D,RC,ERD>()).factory(ErDiagramComparator.Type.category);
	}


	public void initSuper(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslErDiagramFacade<L,D,RC,ERD> fErDiagram)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fErDiagram=fErDiagram;

		sbhCategory.setList(fErDiagram.allOrderedPositionVisible(fbErDiagram.getClassCategory()));
		sbhCategory.selectAll();
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+": "+fbErDiagram.getClassCategory().getSimpleName()+" "+sbhCategory.getSelected().size()+"/"+sbhCategory.getList().size());}
		refreshList();
	}


	private void refreshList()
	{
		erDiagrams = fErDiagram.all(fbErDiagram.getClassErDiagram());
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
		erDiagram = fErDiagram.find(fbErDiagram.getClassErDiagram(), erDiagram);
	}

	public void saveERDiagram() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(erDiagram));}
		if(erDiagram.getCategory()!=null){erDiagram.setCategory(fErDiagram.find(fbErDiagram.getClassCategory(),erDiagram.getCategory()));}
		erDiagram = fErDiagram.save(erDiagram);
		refreshList();
	}

	public void addErDiagram() {
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbErDiagram.getClassErDiagram()));}
		erDiagram = efErDiagram.build(null, null);
		erDiagram.setName(efLang.createEmpty(localeCodes));
		erDiagram.setDescription(efDescription.createEmpty(localeCodes));
	}
}