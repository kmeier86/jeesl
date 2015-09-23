package net.sf.ahtutils.doc.ofx.qa;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.doc.ofx.qa.table.OfxTableQaFrResultFactory;
import net.sf.ahtutils.doc.ofx.qa.test.OfxTableQaTestFactory;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Expected;
import net.sf.ahtutils.xml.qa.Info;
import net.sf.ahtutils.xml.qa.Test;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxSectionQaCategoryFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSectionQaCategoryFactory.class);

	private OfxTableQaTestFactory fOfxTableTest;
	private OfxTableQaFrResultFactory fOfxTableTestResult;
	
	private Aht conditions;
	
	public OfxSectionQaCategoryFactory(Configuration config, String lang, Translations translations)
	{
		this(config,new String[] {lang},translations);
	}
	public OfxSectionQaCategoryFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		fOfxTableTest = new OfxTableQaTestFactory(config,langs,translations);
		fOfxTableTestResult = new OfxTableQaFrResultFactory(config,langs,translations);
	}
	
	public void setTestConditions(Aht conditions){this.conditions=conditions;}
	
	public Section build(Category category) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();

		section.getContent().add(XmlTitleFactory.build(category.getName()));
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
		for(Test test : category.getTest())
		{
			section.getContent().add(buildSectionForTest(test));
		}
		
		return section;
	}
	
	private Section buildSectionForTest(Test test) throws OfxAuthoringException
	{
		Section section = XmlSectionFactory.build();
		section.getContent().add(XmlTitleFactory.build(test.getName()));
		
		if(test.isSetDescription() && test.getDescription().isSetValue())
		{
			Paragraph p = XmlParagraphFactory.build();
			p.getContent().add(test.getDescription().getValue());
			section.getContent().add(p);
		}

		section.getContent().add(fOfxTableTest.buildTableTestDetails(test));
		
		
		if(test.isSetExpected()){section.getContent().addAll(expectedParagraph(test.getExpected()));}
		if(test.isSetInfo()){section.getContent().addAll(infoParagraph(test.getInfo()));}
		
		section.getContent().add(fOfxTableTestResult.buildTestTable(test));
		
		return section;
	}
	
	private List<Paragraph> expectedParagraph(Expected expected)
	{
		List<Paragraph> list = new ArrayList<Paragraph>();
		Paragraph p1 = XmlParagraphFactory.build();
		p1.getContent().add("The expected result is: "+expected.getValue());
		list.add(p1);
		return list;
	}
	
	private List<Paragraph> infoParagraph(Info info)
	{
		String condition = null;
		if(conditions!=null)
		try
		{
			Status status = StatusXpath.getStatus(conditions.getStatus(), info.getStatus().getCode());
			Lang lCondition = StatusXpath.getLang(status.getLangs(),"en");
			condition = lCondition.getTranslation();
		}
		catch (ExlpXpathNotFoundException e) {}
		catch (ExlpXpathNotUniqueException e) {}
		if(conditions==null){condition = info.getStatus().getCode();}
		
		List<Paragraph> list = new ArrayList<Paragraph>();
		Paragraph p1 = XmlParagraphFactory.build();
		p1.getContent().add("The development status of this test is currently: "+condition);
		list.add(p1);
		
		if(info.isSetComment() && info.getComment().isSetValue() && info.getComment().getValue().length()>0)
		{
			Paragraph p2 = XmlParagraphFactory.build();
			p2.getContent().add(info.getComment().getValue());
			list.add(p2);
		}
		return list;
	}
}