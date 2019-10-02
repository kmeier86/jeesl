package net.sf.ahtutils.interfaces.facade;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface SearchUtilsFacade extends Serializable {
	<T extends EjbWithId> List<T> callbackSearch(Class<T> type, String searchText);
}
