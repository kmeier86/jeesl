package org.jeesl.client.test.factory.word;

import java.io.IOException;

import org.jeesl.doc.word.EntityWordRenderer;
import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestEntityWordRederer extends AbstractWordCli
{
	
	final static Logger logger = LoggerFactory.getLogger(TestEntityWordRederer.class);
	
	private static String xmlEntities = "../util/src/test/resources/data/xml/word/entities.xml";
	private static String savingDirectory = "target/TestEntitiesRender";
	
	Document templateDoc;
	
	public TestEntityWordRederer() throws Exception 
	{
			this.templateDoc = new Document("../util/src/test/resources/data/docx/templates/entity.dotx");
	}
	
	public void renderEntities() throws IOException, Exception
	{
		Entities entities = JaxbUtil.loadJAXB(mrl.searchIs(xmlEntities), Entities.class);
		JaxbUtil.info(entities);
		EntityWordRenderer entityRenderer = new EntityWordRenderer(templateDoc);
		
		int count=0; 
		for (Entity e : entities.getEntity())
		{
			entityRenderer.render(e, savingDirectory+count+".docx");			
			count++;
			
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("jeesl/client/config");
		loggerInit.init();
		TestEntityWordRederer cli = new TestEntityWordRederer();
		cli.renderEntities();	  
	}
}
