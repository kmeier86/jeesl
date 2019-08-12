package org.jeesl.doc.word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Body;
import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.NodeType;
import com.aspose.words.Row;
import com.aspose.words.Run;
import com.aspose.words.SaveFormat;
import com.aspose.words.Table;

public class EntityWordRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(EntityWordRenderer.class);
	
	Document entityDoc;
	Entity entity;
	DocumentBuilder docBuilder;
	
	public EntityWordRenderer(Document templateDoc)
	{
		this.entityDoc=templateDoc;	
	}
	
	public void render(Entity entity, String savingDirectory) throws Exception 
	{
		this.entity = entity;

		this.docBuilder = new DocumentBuilder(entityDoc);
			
			
		Map<String, String> replacementTags = new HashMap<String, String>();	
		List<String> keys= new ArrayList<>();
			
		keys.add("_ENTITY_");replacementTags.put("_ENTITY_", entity.getCategory().getCode());	
		keys.add("_CATEGORY_");replacementTags.put("_CATEGORY_", entity.getCode());
		keys.add("_CLASS1_");replacementTags.put("_CLASS1_", entity.getCategory().getClass().getPackage().getName());
		keys.add("_CLASS2_");replacementTags.put("_CLASS2_", entity.getCategory().getClass().getTypeName().replace(entity.getCategory().getClass().getPackage().getName(), ""));
		keys.add("_DESCRIPTION_");replacementTags.put("_DESCRIPTION_", entity.getDescriptions().getDescription().get(0).getValue().toString());
			
		List<Attribute> attrbs = new ArrayList<Attribute>();
		for (Attribute a:entity.getAttribute()){if(a.isBean()){attrbs.add(a);}}			
					
		Table table = entityDoc.getSections().get(0).getBody().getTables().get(0);
		for (int i=0;i<=attrbs.size()-1;i++) 
		{							
			Attribute a = attrbs.get(i);	
			int cellHelper=0;
			for (Cell c : table.getLastRow().getCells())
			{
				c.getLastParagraph().getRuns().clear();
				docBuilder.moveTo(c.getFirstParagraph());
				
				if (cellHelper==0) {docBuilder.write(a.getCode());}					
				if (cellHelper==1) {docBuilder.write(a.getLangs().getLang().get(0).getTranslation().toString());}					
				if (cellHelper==2) {docBuilder.write(a.getDescriptions().getDescription().get(0).getValue());}
				if (cellHelper==3) {docBuilder.write("2do");}

				cellHelper++;
			}
			table.appendChild(table.getLastRow().deepClone(true));	
		}		
		table.getLastRow().remove();
			
		for (String s : keys)
		{
			docBuilder.moveToDocumentStart();
			if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(true);logger.info("dddd_CLASS2_");};
			logger.info(s + replacementTags.get(s).toString());
			try
			{
				
				entityDoc.getRange().replace(s, replacementTags.get(s).toString(),new FindReplaceOptions(FindReplaceDirection.FORWARD));

			}
				catch (Exception e1) {e1.printStackTrace();
			}
			if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(false);};
		}
		
		entityDoc.save(savingDirectory, SaveFormat.DOCX);
	}
}
