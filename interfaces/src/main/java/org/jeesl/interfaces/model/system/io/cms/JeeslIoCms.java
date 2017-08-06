package org.jeesl.interfaces.model.system.io.cms;

import java.util.List;

import org.jeesl.interfaces.model.system.with.status.JeeslWithCategory;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoCms<L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								T extends UtilsStatus<T,L,D>,
								C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								M extends UtilsStatus<M,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
		extends EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPosition,EjbWithLang<L>,JeeslWithCategory<L,D,CAT>
{	
	public enum Attributes{category,position}
	
	CAT getCategory();
	void setCategory(CAT category);
	
	S getRoot();
	void setRoot(S section);
	
	List<LOC> getLocales();
	void setLocales(List<LOC> locales);
}