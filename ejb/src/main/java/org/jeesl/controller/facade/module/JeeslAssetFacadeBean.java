package org.jeesl.controller.facade.module;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslAssetFacadeBean<L extends UtilsLang, D extends UtilsDescription,
										REALM extends JeeslAssetRealm<L,D,REALM,?>,
										ASSET extends JeeslAsset<REALM,ASSET,AS>,
										MANU extends JeeslAssetManufacturer,
										AS extends JeeslAssetStatus<L,D,AS,?>>
					extends UtilsFacadeBean
					implements JeeslAssetFacade<L,D,REALM,ASSET,MANU,AS>
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslAssetFacadeBean.class);
	
	private final AssetFactoryBuilder<L,D,REALM,ASSET,MANU,AS> fbAsset;
	
	public JeeslAssetFacadeBean(EntityManager em, final AssetFactoryBuilder<L,D,REALM,ASSET,MANU,AS> fbAsset)
	{
		super(em);
		this.fbAsset=fbAsset;
	}

	@Override
	public <RREF extends EjbWithId> ASSET fcAssetRoot(REALM realm, RREF realmReference)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<ASSET> cQ = cB.createQuery(fbAsset.getClassAsset());
		Root<ASSET> root = cQ.from(fbAsset.getClassAsset());
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Expression<Long> eRefId = root.get(JeeslAsset.Attributes.realmIdentifier.toString());
		Path<REALM> pRealm = root.get(JeeslAsset.Attributes.realm.toString());
		Path<ASSET> pParent = root.get(JeeslAsset.Attributes.parent.toString());
		
		predicates.add(cB.equal(eRefId,realmReference.getId()));
		predicates.add(cB.equal(pRealm,realm));
		predicates.add(cB.isNull(pParent));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);

		TypedQuery<ASSET> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex)
		{
			ASSET result = null;
			
			return result;
		}
	}
}