package org.jeesl.doc.ofx.cms.module.workflow;

import java.util.List;

import org.jeesl.doc.ofx.cms.generic.AbstractJeeslOfxTableFactory;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslLocaleProvider;
import org.jeesl.model.xml.module.workflow.Stage;
import org.jeesl.model.xml.module.workflow.Transition;
import org.jeesl.util.query.xpath.StatusXpath;
import org.jeesl.util.query.xpath.WorkflowXpath;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.list.OfxListItemFactory;
import org.openfuxml.factory.xml.list.XmlListFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxBodyFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.factory.xml.table.OfxColumnsFactory;
import org.openfuxml.factory.xml.table.OfxContentFactory;
import org.openfuxml.factory.xml.table.OfxRowFactory;
import org.openfuxml.factory.xml.text.OfxEmphasisFactory;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxTableWorkflowProcess <L extends UtilsLang, LOC extends JeeslLocale<L,?,LOC,?>> extends AbstractJeeslOfxTableFactory<L,LOC>
{
	final static Logger logger = LoggerFactory.getLogger(OfxTableWorkflowProcess.class);

	public OfxTableWorkflowProcess(OfxTranslationProvider tp)
	{
		super(tp);
	}
	
	public Table build(JeeslLocaleProvider<LOC> lp, org.jeesl.model.xml.module.workflow.Process process)
	{
		tableHeaders.clear();
		super.addHeader("Stage");
		super.addHeader("Role");
		super.addHeader("Transition");

		Table table = toOfx(lp,process);
		table.setId("table.srs.implementation.");
		try
		{
			table.setTitle(XmlTitleFactory.build("Workflow: "+StatusXpath.getLang(process.getLangs(),lp.getPrimaryLocaleCode()).getTranslation()));
		}
		catch (ExlpXpathNotFoundException | ExlpXpathNotUniqueException e) {e.printStackTrace();}
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.fixedId(comment, table.getId());
		OfxCommentBuilder.doNotModify(comment);
		table.setComment(comment);
		
		return table;
	}
	
	private Table toOfx(JeeslLocaleProvider<LOC> lp, org.jeesl.model.xml.module.workflow.Process process)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		table.setContent(createContent(lp,process));
		return table;
	}
	
	private Specification createSpecifications()
	{
		Columns cols = OfxColumnsFactory.build();
		OfxColumnFactory.add(cols,XmlAlignmentFactory.Horizontal.left);
		cols.getColumn().add(OfxColumnFactory.flex(80));
		cols.getColumn().add(OfxColumnFactory.flex(80));
			
		Specification specification = new Specification();
		specification.setLong(true);
		specification.setColumns(cols);
		specification.setFloat(XmlFloatFactory.build(false));
		
		return specification;
	}
	
	private Content createContent(JeeslLocaleProvider<LOC> lp, org.jeesl.model.xml.module.workflow.Process process)
	{
		Body body = OfxBodyFactory.build();
		
		for(Stage stage : process.getStage())
		{
			body.getRow().add(createRow(lp,process,stage));
		}
		
		return OfxContentFactory.build(super.buildTableHeader(lp),body);
	}
	
	private Row createRow(JeeslLocaleProvider<LOC> lp, org.jeesl.model.xml.module.workflow.Process process, Stage stage)
	{		
		Row row = OfxRowFactory.build();
		row.getCell().add(ofxMultiLocale.cell(lp, stage.getLangs()));
		
		row.getCell().add(OfxCellFactory.createParagraphCell("role"));
		
		if(stage.isSetTransition()) {row.getCell().add(OfxCellFactory.list(transitions(lp,process,stage.getTransition())));}
		else {row.getCell().add(OfxCellFactory.createParagraphCell(""));}
		
		return row;
	}
	
	private org.openfuxml.content.list.List transitions(JeeslLocaleProvider<LOC> lp, org.jeesl.model.xml.module.workflow.Process process, List<Transition> transitions)
	{
		org.openfuxml.content.list.List list = XmlListFactory.unordered();
		
		for(Transition t : transitions)
		{
			for(String localeCode : lp.getLocaleCodes())
			{
				try
				{
					Paragraph p = new Paragraph();
					p.getContent().add(StatusXpath.getLang(t.getLangs(),localeCode).getTranslation());
					
					Stage destination = WorkflowXpath.toStage(process, t.getStage().getId());
					p.getContent().add(" (");
					p.getContent().add(OfxEmphasisFactory.bold(StatusXpath.getLang(destination.getLangs(),localeCode).getTranslation()));
					p.getContent().add(")");
					list.getItem().add(OfxListItemFactory.build(localeCode,p));
					p.getContent().add(" ");
					p.getContent().add(OfxEmphasisFactory.bold(StatusXpath.getDescription(destination.getDescriptions(),localeCode).getValue()));
				}
				catch (ExlpXpathNotFoundException | ExlpXpathNotUniqueException e) {e.printStackTrace();}
			}
			list.getItem().addAll(ofxMultiLocale.listItem(lp,t.getLangs()));
		}
		
		return list;
	}
}