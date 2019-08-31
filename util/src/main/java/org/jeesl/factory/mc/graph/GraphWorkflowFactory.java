package org.jeesl.factory.mc.graph;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.model.xml.module.workflow.Stage;
import org.jeesl.model.xml.module.workflow.Transition;
import org.jeesl.util.query.xpath.StatusXpath;
import org.metachart.factory.xml.graph.XmlEdgeFactory;
import org.metachart.xml.graph.Edge;
import org.metachart.xml.graph.Edges;
import org.metachart.xml.graph.Graph;
import org.metachart.xml.graph.Node;
import org.metachart.xml.graph.Nodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class GraphWorkflowFactory
{
	final static Logger logger = LoggerFactory.getLogger(GraphWorkflowFactory.class);
	
	private final String localeCode;
	
	private Map<String,Node> mapNodes;
	private Map<String,Edge> mapEdges;
	private Map<String,Boolean> mapChilds;
	private Graph graph;
	
	public GraphWorkflowFactory(String localeCode)
	{
		this.localeCode=localeCode;
		mapNodes = new Hashtable<String,Node>();
		mapEdges = new Hashtable<String,Edge>();
		mapChilds = new Hashtable<String,Boolean>();
	}
	
	public Graph build(org.jeesl.model.xml.module.workflow.Process process)
	{
		graph = new Graph();
		graph.setNodes(new Nodes());
		graph.setEdges(new Edges());
		
		for(Stage s : process.getStage())
		{
			Node node = new Node();
			node.setId(s.getId());
			node.setCode(s.getId()+"");
			try {
				node.setLabel(StatusXpath.getLang(s.getLangs(),localeCode).getTranslation());
			} catch (ExlpXpathNotFoundException | ExlpXpathNotUniqueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			node.setSizeRelative(true);
			node.setSizeAdjustsColor(true);
			
			graph.getNodes().getNode().add(node);
			
			for(Transition t : s.getTransition())
			{
				Edge e = XmlEdgeFactory.build(s.getId(), t.getStage().getId(), true);
				graph.getEdges().getEdge().add(e);
			}
		}
		
		
		return graph;
	}
}