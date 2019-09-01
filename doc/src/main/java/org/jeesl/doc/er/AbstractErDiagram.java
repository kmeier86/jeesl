package org.jeesl.doc.er;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.configuration.Configuration;
import org.jeesl.api.rest.system.io.revision.JeeslRevisionRestExport;
import org.jeesl.api.rest.system.io.revision.JeeslRevisionRestImport;
import org.jeesl.model.xml.system.revision.Entities;
import org.metachart.factory.xml.graph.XmlDotFactory;
import org.metachart.factory.xml.graph.XmlGraphFactory;
import org.metachart.processor.graph.ColorSchemeManager;
import org.metachart.processor.graph.Graph1DotConverter;
import org.metachart.processor.graph.GraphFileWriter;
import org.metachart.xml.graph.Graph;
import org.metachart.xml.graph.Graphs;
import org.metachart.xml.graph.Node;
import org.openfuxml.media.transcode.Svg2PdfTranscoder;
import org.openfuxml.renderer.latex.OfxMultiLangLatexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.interfaces.util.ConfigKey;
import net.sf.exlp.util.xml.JaxbUtil;

public class AbstractErDiagram
{
	final static Logger logger = LoggerFactory.getLogger(AbstractErDiagram.class);
	
	protected File fTmp;
	protected File fSrc,fDot,fSvg;
	protected File dPdf,dAtt;
	
	protected String packages;
	protected String colorScheme;
	
	protected String localeCode;
	private String dotGraph; public String getDotGraph() {return dotGraph;}
	private Entities entities;
	
	private OfxMultiLangLatexWriter ofxWriter;
	private JeeslRevisionRestImport restUpload; public void setRest(JeeslRevisionRestImport restUpload) {this.restUpload = restUpload;}
	private JeeslRevisionRestExport restDownload; public void setRest(JeeslRevisionRestExport restDownload) {this.restDownload = restDownload;}

	public AbstractErDiagram(Configuration config,OfxMultiLangLatexWriter ofxWriter)
	{
		this.ofxWriter=ofxWriter;
		localeCode = "en";
		
		fTmp = new File(config.getString(ConfigKey.dirTmp));
		logger.info("Using Tmp: "+fTmp);
	}
	
	protected void loadEntites(String path)
	{
		try {entities = JaxbUtil.loadJAXB(new File(path).getAbsoluteFile(), Entities.class);}
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	protected void create(String key, boolean upload) throws ClassNotFoundException, IOException, TranscoderException
	{
		List<String> subset = new ArrayList<String>();
		subset.add(key);
		File fPdf = null; if(dPdf!=null){fPdf = new File(dPdf,key+".pdf");}
		buildSvg("dot",subset,new File(fSvg,key+".svg"),fPdf);
		if(upload && restUpload!=null)
		{
			Graph g = XmlGraphFactory.build(key);
			g.setDot(XmlDotFactory.build(dotGraph));
			JaxbUtil.info(g);
			restUpload.importSystemRevisionDiagram(g);
		}
	}
	
	protected void buildSvg(String type, List<String> subset, File fDst, File fPdf) throws ClassNotFoundException, IOException, TranscoderException
	{
//		ErAttributesProcessor eap = new ErAttributesProcessor(ofxWriter,config,fSrc);
//		eap.addPackages(packages);
		
		ErGraphProcessor egp = new ErGraphProcessor(fSrc);
		if(entities!=null) {egp.activateEntities(localeCode,entities);}
		egp.addPackages(packages,subset);
		
		Graph g = egp.create();
//		JaxbUtil.info(g);System.exit(-1);
		
		Node xml = JaxbUtil.loadJAXB(colorScheme, Node.class);
		JaxbUtil.trace(xml);
		
		Graph1DotConverter gdc = new Graph1DotConverter(new ColorSchemeManager(xml),"b");
		gdc.setSubSet(subset);
		
		gdc.setOverlap(false);
		gdc.setRatio(0.7);
		gdc.setRanksep(0.5);
		
		gdc.convert(g);
		dotGraph = gdc.getDot();
	
		gdc.save(fDot);
		
		GraphFileWriter w = new GraphFileWriter(type);
		w.svg(fDot,fDst);
		
		if(fPdf!=null)
		{
			logger.info("SVG-PDF");
			Svg2PdfTranscoder.transcode(fDst,fPdf);
			logger.info("SVG-PDF done");
		}
	}
	
	public void buildDocumentation(String localeCode, boolean upload) throws ClassNotFoundException, IOException, TranscoderException
	{
		if(restDownload!=null)
		{
			Graphs graphs = restDownload.exportSystemRevisionGraphs();
			for(Graph g : graphs.getGraph())
			{
				create(g.getCode(),upload);
			}
		}
	}
}