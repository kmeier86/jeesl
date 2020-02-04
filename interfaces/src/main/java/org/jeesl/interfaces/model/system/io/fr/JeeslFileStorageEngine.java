package org.jeesl.interfaces.model.system.io.fr;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatusFixedCode;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDescription;
import org.jeesl.interfaces.model.system.option.JeeslOptionRestDownload;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;

public interface JeeslFileStorageEngine <S extends JeeslStatus<S,L,D>,
										L extends JeeslLang,
										D extends JeeslDescription,
										G extends JeeslGraphic<L,D,?,?,?>>
							extends Serializable,EjbPersistable,
									JeeslOptionRestDescription,JeeslOptionRestDownload,
									EjbWithCodeGraphic<G>,JeeslStatusFixedCode,
									JeeslStatus<S,L,D>
{	
	public enum Code{fs,oak,gridFS,amazonS3,postgres}
}