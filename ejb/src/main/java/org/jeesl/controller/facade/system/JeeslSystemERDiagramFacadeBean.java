package org.jeesl.controller.facade.system;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.system.JeeslSystemERDiagramFacade;
import org.jeesl.interfaces.model.system.erdiagram.JeeslERDiagram;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSystemERDiagramFacadeBean<L extends UtilsLang,D extends UtilsDescription,
											C extends UtilsStatus<C,L,D>,
											ERD extends JeeslERDiagram<L,D,C,ERD>>
					extends UtilsFacadeBean
					implements JeeslSystemERDiagramFacade<L,D,C,ERD>
{
	private final Class<ERD> cERDiagram;

	public JeeslSystemERDiagramFacadeBean(EntityManager em, final Class<ERD> cERDiagram)
	{
		super(em);
		this.cERDiagram=cERDiagram;
	}

	@Override
	public Integer valueIntForKey(String key, Integer defaultValue) throws UtilsNotFoundException
	{
		try
		{
			ERD t = valueForKey(key);
			return new Integer(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public Long valueLongForKey(String key, Long defaultValue) throws UtilsNotFoundException
	{
		try
		{
			ERD t = valueForKey(key);
			return new Long(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public Boolean valueBooleanForKey(String key, Boolean defaultValue) throws UtilsNotFoundException
	{
		try
		{
			ERD t = valueForKey(key);
			return new Boolean(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public Date valueDateForKey(String key, Date defaultValue) throws UtilsNotFoundException
	{
		try
		{
			ERD t = valueForKey(key);
			return new Date(new Long(t.getValue()));
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public String valueStringForKey(String key, String defaultValue) throws UtilsNotFoundException
	{
		try
		{
			ERD t = valueForKey(key);
			return t.getValue();
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	private ERD valueForKey(String key) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ERD> criteriaQuery = criteriaBuilder.createQuery(cERDiagram);
        Root<ERD> root = criteriaQuery.from(cERDiagram);
        criteriaQuery = criteriaQuery.where(root.<ERD>get("key").in(key));

		ERD result;
		TypedQuery<ERD> q = em.createQuery(criteriaQuery);
		try	{result= q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+cERDiagram.getSimpleName()+" for key="+key);}
		return result;
	}
}