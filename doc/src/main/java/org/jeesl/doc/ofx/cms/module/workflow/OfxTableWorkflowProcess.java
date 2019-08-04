package org.jeesl.doc.ofx.cms.module.workflow;

import java.util.List;

import org.jeesl.doc.ofx.cms.generic.AbstractJeeslOfxTableFactory;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslLocaleProvider;
import org.jeesl.model.xml.module.workflow.Stage;
import org.jeesl.model.xml.module.workflow.Transition;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.list.XmlListFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxBodyFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.factory.xml.table.OfxColumnsFactory;
import org.openfuxml.factory.xml.table.OfxContentFactory;
import org.openfuxml.factory.xml.table.OfxRowFactory;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.status.Context;

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
		super.addHeader("Transition");

		Table table = toOfx(lp,process);
		table.setId("table.srs.implementation.");
		table.setTitle(XmlTitleFactory.build(process.toString()));
		
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
			body.getRow().add(createRow(lp,stage));
		}
		
		return OfxContentFactory.build(super.buildTableHeader(lp),body);
	}
	
	private Row createRow(JeeslLocaleProvider<LOC> lp, Stage stage)
	{		
		Row row = OfxRowFactory.build();
		row.getCell().add(ofxMultiLocale.cell(lp, stage.getLangs()));
		row.getCell().add(OfxCellFactory.list(transitions(lp, stage.getTransition())));
		
		return row;
	}
	
	private org.openfuxml.content.list.List transitions(JeeslLocaleProvider<LOC> lp, List<Transition> transitions)
	{
		org.openfuxml.content.list.List list = XmlListFactory.unordered();
		
		for(Transition t : transitions)
		{
			list.getItem().addAll(ofxMultiLocale.listItem(lp, t.getLangs()));
		}
		
		return list;
	}
}