package org.jeesl.web.mbean.prototype.system.io.ssi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.controller.handler.tuple.JsonTuple1Handler;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.factory.ejb.system.io.ssi.EjbIoSsiAttributeFactory;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.io.JsonUtil;

public class AbstractSettingsSsiAttributeBean <L extends UtilsLang,D extends UtilsDescription,
										SYSTEM extends JeeslIoSsiSystem,
										MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
										ATTRIBUTE extends JeeslIoSsiAttribute<MAPPING,ENTITY>,
										DATA extends JeeslIoSsiData<MAPPING,LINK>,
										LINK extends UtilsStatus<LINK,L,D>,
										ENTITY extends JeeslRevisionEntity<L,D,?,?,?,?>>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSettingsSsiAttributeBean.class);
	
	private final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fbSsi;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,ENTITY,?,?,?,?,?> fbRevision;
	
	private JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fSsi;
	
	private final List<MAPPING> mappings; public List<MAPPING> getMappings() {return mappings;}
	private final List<ENTITY> entities; public List<ENTITY> getEntities() {return entities;}
	private final List<ATTRIBUTE> attributes; public List<ATTRIBUTE> getAttributes() {return attributes;}
	
	private ATTRIBUTE attribute; public ATTRIBUTE getAttribute() {return attribute;} public void setAttribute(ATTRIBUTE attribute) {this.attribute = attribute;}

	public AbstractSettingsSsiAttributeBean(final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fbSsi,
											final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,ENTITY,?,?,?,?,?> fbRevision)
	{
		this.fbSsi=fbSsi;
		this.fbRevision=fbRevision;
		mappings = new ArrayList<>();
		entities = new ArrayList<>();
		attributes = new ArrayList<>();
	}

	public void postConstructSsiAttribute(JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,ATTRIBUTE,DATA,LINK,ENTITY> fSsi)
	{
		this.fSsi=fSsi;
		mappings.addAll(fSsi.all(fbSsi.getClassMapping()));
		entities.addAll(fSsi.all(fbRevision.getClassEntity()));

		reload();
	}

	private void reload()
	{
		attributes.clear();
		attributes.addAll(fSsi.all(fbSsi.getClassAttribute()));
	}
	
	public void selectAttribute()
	{
		logger.info(AbstractLogMessage.selectEntity(attribute));
	}
	
	public void addAttribute()
	{
		attribute = fbSsi.ejbAttribute().build(null);
	}
	
	public void saveAttribute() throws UtilsConstraintViolationException, UtilsLockingException
	{
		attribute.setMapping(fSsi.find(fbSsi.getClassMapping(),attribute.getMapping()));
		attribute.setEntity(fSsi.find(fbRevision.getClassEntity(),attribute.getEntity()));
		attribute = fSsi.save(attribute);
		reload();
	}
}