package org.jeesl.api.facade.module;

import java.util.List;

import org.jeesl.interfaces.model.module.log.JeeslLogBook;
import org.jeesl.interfaces.model.module.log.JeeslLogImpact;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.jeesl.interfaces.model.module.log.JeeslLogConfidentiality;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslLogFacade <L extends UtilsLang, D extends UtilsDescription,
									LOG extends JeeslLogBook<ITEM>,
									ITEM extends JeeslLogItem<L,D,?,?,LOG,IMPACT,SCOPE,USER>,
									IMPACT extends JeeslLogImpact<L,D,IMPACT,?>,
									SCOPE extends JeeslLogConfidentiality<L,D,SCOPE,?>,
									USER extends EjbWithId
									>
			extends UtilsFacade
{	
	List<ITEM> fLogItems(List<LOG> logs);
}