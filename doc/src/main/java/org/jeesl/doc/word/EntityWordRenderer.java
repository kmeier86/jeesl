package org.jeesl.doc.word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.Table;

public class EntityWordRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(EntityWordRenderer.class);
	
	final Document entityDoc;

	public EntityWordRenderer(Document templateDoc)
	{
		this.entityDoc=templateDoc;	
	}
	
	public void render(Entity entity, String savingDirectory) throws Exception 
	{
	

		DocumentBuilder docBuilder = new DocumentBuilder(entityDoc);
						
		Map<String, String> replacementTags = new HashMap<String, String>();	
		List<String> keys= new ArrayList<>();
		logger.info(entity.getCode());
		logger.info( FilenameUtils.getExtension(entity.getCode()));
		String input = entity.getCategory().getCode();
		keys.add("_ENTITY_");replacementTags.put("_ENTITY_", FilenameUtils.getExtension(entity.getCode()));	
		keys.add("_CATEGORY_");replacementTags.put("_CATEGORY_", input.substring(0, 1).toUpperCase() + input.substring(1));
		keys.add("_CLASS1_");replacementTags.put("_CLASS1_",entity.getCode().replace(FilenameUtils.getExtension(entity.getCode()), ""));
		keys.add("_CLASS2_");replacementTags.put("_CLASS2_", FilenameUtils.getExtension(entity.getCode()));
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
				if (cellHelper==3) {if (a.isSetRelation()) { docBuilder.write(a.getRelation().toString());}}

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
				logger.info("replacementTAG: "+s);
				logger.info("replacementString: "+replacementTags.get(s).toString());
				entityDoc.getRange().replace(s, replacementTags.get(s).toString(),new FindReplaceOptions(FindReplaceDirection.FORWARD));

			}
				catch (Exception e1) {e1.printStackTrace();
			}
			if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(false);};
		}
		
		entityDoc.save(savingDirectory, SaveFormat.DOCX);
	}
}
