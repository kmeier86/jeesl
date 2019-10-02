package org.jeesl.controller.handler.sb;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.SearchUtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbSearchHandler <T extends EjbWithId> extends SbSingleHandler<T>
{
	final static Logger logger = LoggerFactory.getLogger(SbMultiHandler.class);
	private static final long serialVersionUID = 1L;

	private SearchUtilsFacade fUtils;
	private String searchText;



	public SbSearchHandler(Class<T> c, SbSingleBean bean, SearchUtilsFacade fUtils) {
		super(c, bean);
		logger.trace("creating handler: " + SbSearchHandler.class.getSimpleName());
		this.fUtils =  fUtils;
		this.searchText ="";
	}

	public String getSearchText() {
		return this.searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void handleKeyEvent() {
		logger.trace("handle search event: " + SbSearchHandler.class.getSimpleName());
		this.clear();

		List<T> result = new ArrayList<T>();
		try {
			if(searchText != "") {
				result = fUtils.callbackSearch(this.c, searchText);
			}
			this.update(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelEvent() {
		logger.trace("cancel search event: " + SbSearchHandler.class.getSimpleName());
		this.clear();
		this.searchText ="";
		this.update(new ArrayList<T>());
	}

	@Override
	public String toString() {
		return "handler name: " + this.getClass().getSimpleName() + " for: " +this.c.getSimpleName();
	}


}