package org.jeesl.doc.word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.model.xml.system.revision.Entity;
import org.jeesl.util.query.xpath.RevisionXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FindReplaceDirection;
import com.aspose.words.FindReplaceOptions;
import com.aspose.words.SaveFormat;
import com.aspose.words.Table;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.xml.status.Status;


public class EntityWordRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(EntityWordRenderer.class);
	
	final Document entityDoc;
	final Container categories;
	final Container relationTypes;
	final Entities entities;
	UtilsFacade fUtils;

	public EntityWordRenderer(Document templateDoc, Entities entities, Container categories, Container relationTypes)
	{
		this.entityDoc=templateDoc;	
		this.categories=categories;
		this.relationTypes=relationTypes;
		this.entities=entities;
	}
	
	private String categoryForCode(Container c, String code)
	{
		for(Status s:c.getStatus()){if(s.isSetCode()&&s.getCode()!=""&&s.getCode().equals(code)){return s.getLangs().getLang().get(0).getTranslation();}}return "";
	}
	
	private String relationTypeForCode(Container c, String code)
	{
		for(Status s:c.getStatus()){if(s.isSetCode()&&s.getCode()!=""&&s.getCode().equals(code)){return s.getLangs().getLang().get(0).getTranslation();}}return "";
	}
	
	public void render(Entity entity, String savingDirectory) throws Exception 
	{
		DocumentBuilder docBuilder = new DocumentBuilder(entityDoc);
						
		Map<String, String> replacementTags = new HashMap<String, String>();	
		List<String> keys= new ArrayList<>();
		
		keys.add("_ENTITY_");replacementTags.put("_ENTITY_", entity.getLangs().getLang().get(0).getTranslation());
			
		keys.add("_CATEGORY_");replacementTags.put("_CATEGORY_", categoryForCode(categories, entity.getCategory().getCode()));
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
				if (cellHelper==2)
				{
					docBuilder.write(a.getDescriptions().getDescription().get(0).getValue());
					docBuilder.getFont().setItalic(true);
					
					if(a.getRelation()!=null)
					{
						docBuilder.write(relationTypeForCode(relationTypes, a.getRelation().getType().getCode())+": ");
						if (a.getRelation().isSetEntity())
						{
							Entity e = RevisionXpath.getEntity(entities, a.getRelation().getEntity().getCode());
							docBuilder.write(e.getLangs().getLang().get(0).getTranslation());
						}
						docBuilder.getFont().setItalic(false);
				}
				}
				cellHelper++;
			}
			table.appendChild(table.getLastRow().deepClone(true));	
		}		
		table.getLastRow().remove();
			
		for (String s : keys)
		{
			docBuilder.moveToDocumentStart();
			if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(true);/*logger.info("dddd_CLASS2_");*/};
//			logger.info(s + replacementTags.get(s).toString());
			try
			{
//				logger.info("replacementTAG: "+s);
//				logger.info("replacementString: "+replacementTags.get(s).toString());
				entityDoc.getRange().replace(s, replacementTags.get(s).toString(),new FindReplaceOptions(FindReplaceDirection.FORWARD));

			}
				catch (Exception e1) {e1.printStackTrace();
			}
			if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(false);};
		}
		
		entityDoc.save(savingDirectory, SaveFormat.DOCX);
	}
}
