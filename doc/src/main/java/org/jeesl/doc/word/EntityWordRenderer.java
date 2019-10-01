package org.jeesl.doc.word;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
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
import com.aspose.words.RowCollection;
import com.aspose.words.Table;

import net.sf.ahtutils.xml.status.Status;

public class EntityWordRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(EntityWordRenderer.class);
	
	private final Document entityDoc;
	private final Container categories;
	private final Container relationTypes;
	private final Entities entities;

	public EntityWordRenderer(Document templateDoc, Entities entities, Container categories, Container relationTypes)
	{
		this.entityDoc=templateDoc;	
		this.categories=categories;
		this.relationTypes=relationTypes;
		this.entities=entities;
	}
	
	private String categoryForCode(Container c, String code)
	{
		for(Status s:c.getStatus()){if(s.isSetCode()&& s.getCode()!=""&&s.getCode().equals(code)){return s.getLangs().getLang().get(0).getTranslation();}}return "";
	}
	
	private String relationTypeForCode(Container c, String code)
	{
		for(Status s:c.getStatus()){if(s.isSetCode()&&s.getCode()!=""&&s.getCode().equals(code)){return s.getSymbol();}}return "";
	}
	
	private String packageShrinker(String original, int numberOfWordsToShrink) 	
	{	
		StringBuilder sb = new StringBuilder();ArrayList<String> splitString = new ArrayList<>();int i=0;
		for (String s:StringUtils.splitPreserveAllTokens(original,".")){if(i<=numberOfWordsToShrink){splitString.add(StringUtils.left(s,1));i++;}else{splitString.add(s);}}
		int helper=1;
		for (String s:splitString){if(helper != splitString.size()){sb.append(s.toString()+".");}else if(helper == splitString.size()){sb.append(s.toString());}helper++;}
		return sb.toString();
	}
	
	public Document render(Entity entity, String savingDirectory) throws Exception 
	{
		DocumentBuilder docBuilder = new DocumentBuilder(entityDoc);
						
		Map<String, String> replacementTags = new HashMap<String, String>();	
		List<String> keys= new ArrayList<>();
		
		keys.add("_ENTITY_");replacementTags.put("_ENTITY_", entity.getLangs().getLang().get(0).getTranslation());
			
		keys.add("_CATEGORY_");replacementTags.put("_CATEGORY_", categoryForCode(categories, entity.getCategory().getCode()));
		keys.add("_CLASS1_");replacementTags.put("_CLASS1_",	packageShrinker(entity.getCode().replace(FilenameUtils.getExtension(entity.getCode()), ""), 5));
		logger.info(packageShrinker(entity.getCode().replace(FilenameUtils.getExtension(entity.getCode()), ""), 5));
		keys.add("_CLASS2_");replacementTags.put("_CLASS2_", FilenameUtils.getExtension(entity.getCode()));
		keys.add("_DESCRIPTION_");replacementTags.put("_DESCRIPTION_", entity.getDescriptions().getDescription().get(0).getValue().toString());
			
		List<Attribute> attrbs = new ArrayList<Attribute>();
		for (Attribute a:entity.getAttribute()){if(a.isBean()){attrbs.add(a);}}			
					
		Table table = entityDoc.getSections().get(0).getBody().getTables().get(0);
		RowCollection rows = table.getRows();
		
		logger.info("rows in collection: " + rows.getCount());
		
		for (int i=0;i<=attrbs.size()-1;i++) 
		{							
			Attribute a = attrbs.get(i);	
			
			int cellHelperRow5=0;
			for (Cell c : rows.get(5).getCells())
			{
				c.getLastParagraph().getRuns().clear();
				docBuilder.moveTo(c.getFirstParagraph());
				
				if (cellHelperRow5==0) 
				{
					docBuilder.write(a.getCode());
				}
			
				if (cellHelperRow5==1)
				{
					if (a.getDescriptions().getDescription().get(0).getValue().toString() != "") 
					{
						docBuilder.write(a.getDescriptions().getDescription().get(0).getValue());
						if (a.getRelation()!=null && a.getRelation().isSetEntity())
						{		
							docBuilder.getFont().setColor(Color.gray);docBuilder.getFont().setItalic(true);
							docBuilder.write("  (");
							Entity e = RevisionXpath.getEntity(entities, a.getRelation().getEntity().getCode());
							docBuilder.write(relationTypeForCode(relationTypes, a.getRelation().getType().getCode())+" = "+e.getLangs().getLang().get(0).getTranslation()+")");
							docBuilder.getFont().setColor(Color.black);docBuilder.getFont().setItalic(false);
						}	
					}
				}
				cellHelperRow5++;
			}
			
			int cellHelperRow6=0;
			for (Cell c : rows.get(6).getCells())
			{
				c.getLastParagraph().getRuns().clear();
				docBuilder.moveTo(c.getFirstParagraph());
				
				if (cellHelperRow6==0) 
				{
					docBuilder.write(a.getLangs().getLang().get(0).getTranslation().toString());
				}				
				cellHelperRow6++;
			}
			table.appendChild(rows.get(5).deepClone(true));	
			table.appendChild(rows.get(6).deepClone(true));	
		}		
		table.getRows().get(5).remove();
		table.getRows().get(5).remove();
		
		for (String s : keys)
		{
			docBuilder.moveToDocumentStart();
			if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(true);}
			try
			{
				entityDoc.getRange().replace(s, replacementTags.get(s).toString(),new FindReplaceOptions(FindReplaceDirection.FORWARD));
			}
				catch (Exception e1) {e1.printStackTrace();
			}
			if (s.equals("_CLASS2_")) {docBuilder.getFont().setBold(false);};
		}
		
		return entityDoc;
	}
}