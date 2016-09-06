package org.jeesl.web.mbean.prototype.admin.system.io;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jeesl.factory.ejb.system.io.EjbIoTemplateDefinitionFactory;
import org.jeesl.factory.ejb.system.io.EjbIoTemplateFactory;
import org.jeesl.factory.ejb.system.io.EjbIoTemplateTokenFactory;
import org.jeesl.factory.txt.system.io.TxtIoTemplateFactory;
import org.jeesl.factory.txt.system.io.TxtIoTemplateTokenFactory;
import org.jeesl.interfaces.facade.JeeslIoTemplateFacade;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.jeesl.mail.freemarker.FreemarkerIoTemplateEngine;
import org.jeesl.util.comparator.ejb.system.io.IoTemplateComparator;
import org.jeesl.util.comparator.ejb.system.io.IoTemplateDefinitionComparator;
import org.jeesl.util.comparator.ejb.system.io.IoTemplateTokenComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.TabChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.InvalidReferenceException;
import freemarker.template.TemplateException;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.prototype.controller.handler.ui.SbMultiStatusHandler;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminIoTemplateBean <L extends UtilsLang,D extends UtilsDescription,
											CATEGORY extends UtilsStatus<CATEGORY,L,D>,
											TYPE extends UtilsStatus<TYPE,L,D>,
											TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
											DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
											TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminIoTemplateBean.class);
	
	protected JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> fTemplate;
	
	private Class<TYPE> cType;
	private Class<CATEGORY> cCategory;
	private Class<TEMPLATE> cTemplate;
	private Class<DEFINITION> cDefinition;
	private Class<TOKEN> cToken;
	
	private List<CATEGORY> categories; public List<CATEGORY> getCategories() {return categories;}
	private List<TYPE> types; public List<TYPE> getTypes() {return types;}
	private List<TEMPLATE> templates; public List<TEMPLATE> getTemplates() {return templates;}
	private List<DEFINITION> definitions; public List<DEFINITION> getDefinitions() {return definitions;}
	private List<TOKEN> tokens; public List<TOKEN> getTokens() {return tokens;}
	
	private TEMPLATE template; public TEMPLATE getTemplate() {return template;} public void setTemplate(TEMPLATE template) {this.template = template;}
	private DEFINITION definition; public DEFINITION getDefinition() {return definition;} public void setDefinition(DEFINITION definition) {this.definition = definition;}
	private TOKEN token; public TOKEN getToken() {return token;} public void setToken(TOKEN token) {this.token = token;}
	
	private EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> efTemplate;
	private EjbIoTemplateDefinitionFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> efDefinition;
	private EjbIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> efToken;
	
	private SbMultiStatusHandler<L,D,CATEGORY> sbhCategory; public SbMultiStatusHandler<L,D,CATEGORY> getSbhCategory() {return sbhCategory;}
	private FreemarkerIoTemplateEngine<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> fmEngine;
	
	private Comparator<TEMPLATE> comparatorTemplate;
	private Comparator<TOKEN> comparatorToken;
	private Comparator<DEFINITION> comparatorDefinition;

	private int tabIndex; public int getTabIndex() {return tabIndex;} public void setTabIndex(int tabIndex) {this.tabIndex = tabIndex;}
	private String preview; public String getPreview() {return preview;}
	
	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoTemplateFacade<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> fTemplate, final Class<L> cLang, final Class<D> cDescription,  Class<CATEGORY> cCategory, Class<TYPE> cType, Class<TEMPLATE> cTemplate, Class<DEFINITION> cDefinition, Class<TOKEN> cToken)
	{
		super.initAdmin(langs,cLang,cDescription,bMessage);
		this.fTemplate=fTemplate;
		this.cCategory=cCategory;
		this.cType=cType;
		this.cTemplate=cTemplate;
		this.cDefinition=cDefinition;
		this.cToken=cToken;
		
		efTemplate = EjbIoTemplateFactory.factory(cLang,cDescription,cTemplate);
		efDefinition = EjbIoTemplateDefinitionFactory.factory(cDescription,cDefinition);
		efToken = EjbIoTemplateTokenFactory.factory(cLang,cDescription,cToken);
		
		comparatorTemplate = new IoTemplateComparator<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>().factory(IoTemplateComparator.Type.position);
		comparatorToken = new IoTemplateTokenComparator<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>().factory(IoTemplateTokenComparator.Type.position);
		comparatorDefinition = new IoTemplateDefinitionComparator<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>().factory(IoTemplateDefinitionComparator.Type.position);
		
		fmEngine = new FreemarkerIoTemplateEngine<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>();
		
		types = fTemplate.allOrderedPositionVisible(cType);
		categories = fTemplate.allOrderedPositionVisible(cCategory);
		
		sbhCategory = new SbMultiStatusHandler<L,D,CATEGORY>(cCategory,categories);
		sbhCategory.selectAll();
		reloadTemplates();
	}
	
	public void multiToggle(UtilsStatus<?,L,D> o)
	{
		logger.info(AbstractLogMessage.toggle(o)+" Class: "+o.getClass().getSimpleName());
		sbhCategory.multiToggle(o);
		reloadTemplates();
		cancelTemplate();
	}
	
	//*************************************************************************************
	private void reloadTemplates()
	{
		templates = fTemplate.fTemplates(cTemplate, cCategory, sbhCategory.getSelected(), true);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cTemplate,templates));}
		Collections.sort(templates, comparatorTemplate);
	}
	
	public void addTemplate() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cTemplate));}
		template = efTemplate.build(null);
		template.setName(efLang.createEmpty(langs));
		template.setDescription(efDescription.createEmpty(langs));
		definition=null;
		preview=null;
	}
	
	private void reloadTemplate()
	{
		template = fTemplate.load(cTemplate, template);
		tokens = template.getTokens();
		definitions = template.getDefinitions();
		
		Collections.sort(tokens, comparatorToken);
		Collections.sort(definitions, comparatorDefinition);
	}
	
	public void selectTemplate() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(template));}
		template = fTemplate.find(cTemplate, template);
		template = efLang.persistMissingLangs(fTemplate,langs,template);
		template = efDescription.persistMissingLangs(fTemplate,langs,template);
		reloadTemplate();
		definition=null;
		preview=null;
	}
	
	public void saveTemplate() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(template));}
		if(template.getCategory()!=null){template.setCategory(fTemplate.find(cCategory, template.getCategory()));}
		template = fTemplate.save(template);
		reloadTemplates();
		reloadTemplate();
		bMessage.growlSuccessSaved();
		updatePerformed();
	}
	
	public void rmTemplate() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(template));}
		fTemplate.rm(template);
		template=null;
		bMessage.growlSuccessRemoved();
		reloadTemplates();
		updatePerformed();
	}
	
	public void cancelTemplate()
	{
		template = null;
	}
		
	//*************************************************************************************
	public void addToken() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cToken));}
		token = efToken.build(template);
		token.setName(efLang.createEmpty(langs));
		token.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectToken() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(token));}
		token = fTemplate.find(cToken, token);
	}
	
	public void saveToken() throws UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(token));}
		try
		{
			token = fTemplate.save(token);
			reloadTemplate();
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	
	public void rmToken() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(token));}
		fTemplate.rm(token);
		token=null;
		bMessage.growlSuccessRemoved();
		reloadTemplate();
		updatePerformed();
	}
	
	public void cancelToken()
	{
		token=null;
	}
	
	//*************************************************************************************
	public void addDefinition() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cDefinition));}
		definition = efDefinition.build(template,null);
		definition.setDescription(efDescription.createEmpty(langs));
		preview = null;
	}
	
	public void selectDefinition() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(definition));}
		definition = fTemplate.find(cDefinition, definition);
		renderPreview();
	}
	
	public void saveDefinition() throws UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(definition));}
		try
		{
			definition.setType(fTemplate.find(cType, definition.getType()));
			definition = fTemplate.save(definition);
			renderPreview();
			bMessage.growlSuccessSaved();
			updatePerformed();
		}
		catch (UtilsConstraintViolationException e) {bMessage.errorConstraintViolationDuplicateObject();}
	}
	
    public void definitonTabChange(TabChangeEvent event)
    {
    	if(debugOnInfo){logger.info("Tab Change "+event.getTab().getTitle()+" "+tabIndex);}
    	renderPreview();
    }
    
    private void renderPreview()
    {
    	logger.info("Preview of "+langs[tabIndex]);
    	try
    	{
    		fmEngine.addTemplate(definition);
    		
    		String fmTemplate = TxtIoTemplateFactory.buildCode(template, definition, langs[tabIndex]);
    		Map<String,String> model = TxtIoTemplateTokenFactory.buildModel(template);
    		
    		preview = null;
			preview = fmEngine.process(fmTemplate,model);
		}
    	catch (InvalidReferenceException e) {preview = e.getMessage();}
    	catch (IOException e) {preview = e.getMessage();}
    	catch (TemplateException e) {preview = e.getMessage();}
    }
    
	//*************************************************************************************
	protected void reorderTemplates() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTemplate, cTemplate, templates);Collections.sort(templates, comparatorTemplate);}
	protected void reorderTokens() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTemplate, cToken, tokens);Collections.sort(tokens, comparatorToken);}
	
	protected void updatePerformed(){}	
	
	@Override protected void updateSecurity2(UtilsJsfSecurityHandler jsfSecurityHandler, String actionDeveloper)
	{
		uiAllowSave = jsfSecurityHandler.allow(actionDeveloper);

		if(logger.isTraceEnabled())
		{
			logger.info(uiAllowSave+" allowSave ("+actionDeveloper+")");
		}
	}
}