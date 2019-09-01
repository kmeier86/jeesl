package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.system.erdiagram.ErDiagramFactoryBuilder;
import org.jeesl.factory.ejb.system.erdiagram.EjbErDiagramFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
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
											ERD extends JeeslRevisionDiagram<L,D,RC>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminErDiagramBean.class);

	private JeeslIoRevisionFacade<L,D,RC,?,?,?,?,?,?,?,?,?> fRevision;
	private final ErDiagramFactoryBuilder<L,D,RC,ERD> fbErd;

	private EjbErDiagramFactory<L,D,RC,ERD> efErDiagram;

	private final SbMultiHandler<RC> sbhCategory; public SbMultiHandler<RC> getSbhCategory() {return sbhCategory;}
	private final Comparator<ERD> comparatorERDiagram;

	protected List<ERD> erDiagrams; public List<ERD> getErDiagrams() {return erDiagrams;}

	private ERD diagram; public ERD getDiagram() {return diagram;} public void setDiagram(ERD diagram) {this.diagram = diagram;}
	private String dot; public String getDot() {return dot;} public void setDot(String dot) {this.dot = dot;}
	
	public AbstractAdminErDiagramBean(final ErDiagramFactoryBuilder<L,D,RC,ERD> fbErd)
	{
		super(fbErd.getClassL(),fbErd.getClassD());
		this.fbErd = fbErd;
		this.efErDiagram = this.fbErd.erDiagram();
		sbhCategory = new SbMultiHandler<RC>(fbErd.getClassCategory(),this);
		comparatorERDiagram = (new ErDiagramComparator<L,D,RC,ERD>()).factory(ErDiagramComparator.Type.category);
	}

	public void initSuper(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,?,?,?,?,?,?,?,?,?> fRevision)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fRevision=fRevision;

		sbhCategory.setList(fRevision.allOrderedPositionVisible(fbErd.getClassCategory()));
		sbhCategory.selectAll();
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+": "+fbErd.getClassCategory().getSimpleName()+" "+sbhCategory.getSelected().size()+"/"+sbhCategory.getList().size());}
		refreshList();
	}

	private void refreshList()
	{
		erDiagrams = fRevision.all(fbErd.getClassErDiagram());
		Collections.sort(erDiagrams,comparatorERDiagram);
	}

	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+" toggled, but NYI");}
	}

	public void selectDiagram() throws UtilsNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(diagram));}
		reloadDiagram();
		
	}
	
	private void reloadDiagram()
	{
		diagram = fRevision.find(fbErd.getClassErDiagram(), diagram);
		if(diagram.isDotManual()) {dot = diagram.getDotGraph();}
		else
		{
			
		}
	}

	public void saveDiagram() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(diagram));}
		if(diagram.getCategory()!=null){diagram.setCategory(fRevision.find(fbErd.getClassCategory(),diagram.getCategory()));}
		diagram = fRevision.save(diagram);
		refreshList();
		reloadDiagram();
	}

	public void addErDiagram()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbErd.getClassErDiagram()));}
		diagram = efErDiagram.build(null, null);
		diagram.setName(efLang.createEmpty(localeCodes));
		diagram.setDescription(efDescription.createEmpty(localeCodes));
	}
}