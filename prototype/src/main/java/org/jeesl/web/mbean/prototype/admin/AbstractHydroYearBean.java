package org.jeesl.web.mbean.prototype.admin;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.component.sb.JeeslHydroYearFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.component.HydroYearFactoryBuilder;
import org.jeesl.factory.ejb.system.component.EjbHydroYearFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroDecade;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.util.comparator.ejb.component.sb.HydroYearComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractHydroYearBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											HD extends JeeslHydroDecade<L,D,HD,?>,
											HY extends JeeslHydroYear<L,D,HD,HY>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHydroYearBean.class);

	private JeeslHydroYearFacade<L,D,HD,HY> fHydroYear;
	private final HydroYearFactoryBuilder<L,D,HD,HY> fbHydroYear;

	private EjbHydroYearFactory<L,D,HD,HY> efHydroYear;

	private final SbMultiHandler<HD> sbhDecade; public SbMultiHandler<HD> getSbhDecade() {return sbhDecade;}
	private final Comparator<HY> comparatorHydroYear;

	protected List<HY> hydroYears; public List<HY> getHydroYears() {return hydroYears;}

	protected HY hydroYear;

	public HY getHydroYear() {
		return hydroYear;
	}

	public void setHydroYear(HY hydroYear) {
		this.hydroYear = hydroYear;
	}

	public AbstractHydroYearBean(final HydroYearFactoryBuilder<L,D,HD,HY> fbHydroYear)
	{
		super(fbHydroYear.getClassL(),fbHydroYear.getClassD());
		this.fbHydroYear = fbHydroYear;
		this.efHydroYear = this.fbHydroYear.hydroYear();
		sbhDecade = new SbMultiHandler<HD>(fbHydroYear.getClassDecade(),this);
		comparatorHydroYear = (new HydroYearComparator<L,D,HD,HY>()).factory(HydroYearComparator.Type.code);
	}


	public void initSuper(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslHydroYearFacade<L,D,HD,HY> fHydroYear)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fHydroYear=fHydroYear;

		sbhDecade.setList(fHydroYear.allOrderedPositionVisible(fbHydroYear.getClassDecade()));
		sbhDecade.selectAll();
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+": "+fbHydroYear.getClassDecade().getSimpleName()+" "+sbhDecade.getSelected().size()+"/"+sbhDecade.getList().size());}
		refreshList();
	}


	private void refreshList()
	{
		hydroYears = fHydroYear.all(fbHydroYear.getClassYear());
		Collections.sort(hydroYears,comparatorHydroYear);
	}

	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+" toggled, but NYI");}
	}

	public void selectHydroYear() throws UtilsNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(hydroYear));}
		hydroYear = fHydroYear.find(fbHydroYear.getClassYear(), hydroYear);
	}

	public void saveHydroYear() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(hydroYear));
		if(hydroYear.getDecade()!=null){
			HD decade = fHydroYear.find(fbHydroYear.getClassDecade(),hydroYear.getDecade());
			hydroYear.setDecade(decade);
			}
		hydroYear = fHydroYear.save(hydroYear);
		logger.info("-----" + hydroYear.toString() +"-------");
		refreshList();
	}

	public void addHydroYear() {
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbHydroYear.getClassYear()));}
		hydroYear = efHydroYear.build(null);
		hydroYear.setName(efLang.createEmpty(localeCodes));
		hydroYear.setDescription(efDescription.createEmpty(localeCodes));
	}

	public void deleteHydroYear() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(hydroYear));}
		fHydroYear.rm(hydroYear);
		hydroYear=null;
		//bMessage.growlSuccessRemoved();
		refreshList();
	}

	public void cancelHydroYear()
	{
		hydroYear = null;
	}
}