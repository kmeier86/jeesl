package org.jeesl.interfaces.model.system.io.db;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;
import org.jeesl.interfaces.model.with.status.UtilsWithCategory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslSync<L extends JeeslLang, D extends JeeslDescription,
							STATUS extends JeeslStatus<STATUS,L,D>,
							CATEGORY extends JeeslStatus<CATEGORY,L,D>>
			extends EjbWithId,EjbWithCode,EjbWithRecord,
						JeeslWithStatus<STATUS>,
						UtilsWithCategory<L,D,CATEGORY>
{
	public static enum Code{never,pending,success,fail};
}