package org.jeesl.controller.rewrite;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslSecurityBean;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslIdentity;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.pojo.BooleanComparator;
import org.ocpsoft.rewrite.config.Condition;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.servlet.config.HttpCondition;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageDeniedCondition <L extends JeeslLang, D extends JeeslDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,R,V,U,AT>,
											AT extends JeeslSecurityTemplate<L,D,C>,
											M extends JeeslSecurityMenu<V,M>,
											USER extends JeeslUser<R>>
		extends HttpCondition
		implements Condition,Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(PageDeniedCondition.class);
	
	private boolean debugOnInfo;
	
	private final JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity;
	private final JeeslIdentity<R,V,U,A,USER> identity;
	
	public PageDeniedCondition(boolean debugOnInfo, JeeslSecurityBean<L,D,C,R,V,U,A,AT,M,USER> bSecurity, JeeslIdentity<R,V,U,A,USER> identity)
	{
		this.debugOnInfo=debugOnInfo;
		this.bSecurity=bSecurity;
		this.identity=identity;
	}
	
	@Override public boolean evaluateHttp(HttpServletRewrite event, EvaluationContext context)
    {           	 
		String url = AbstractRewriteProvider.getUrlMapping(event.getContextPath(), event.getAddress().toString());
		V view = bSecurity.findViewByUrlMapping(url);

		if(debugOnInfo)
	 	{
	    	 	logger.info(event.getContextPath());
	    	 	logger.info(event.getAddress().toString());
	    	 	logger.info(event.getInboundAddress().toString());
	    	 	logger.info("pageActive: "+url);
	    		if(view!=null) {logger.info("View: "+view.getCode()+" "+view.isVisible());}
	    		else {logger.warn("View not found");}
	 	}
		
		if(view!=null)
		{
			if(BooleanComparator.active(view.getAccessPublic())) {return false;}
			else if(BooleanComparator.active(view.getAccessLogin())){return false;}
			else
			{
				if(identity.hasView(view)) {return false;}
				return true;
			}
		}
		else
		{
			logger.warn("Assuming active=false, because view not found for url: "+url);
			return true;
		}
    }
}