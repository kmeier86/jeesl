package org.jeesl.factory.graph;

import java.io.FileNotFoundException;

import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.factory.mc.graph.GraphWorkflowFactory;
import org.jeesl.model.xml.module.workflow.Workflow;
import org.metachart.factory.xml.graph.XmlClusterFactory;
import org.metachart.factory.xml.graph.XmlClustersFactory;
import org.metachart.processor.graph.ColorSchemeManager;
import org.metachart.processor.graph.Graph2DotConverter;
import org.metachart.xml.graph.Clusters;
import org.metachart.xml.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWorkflowGraphFactory
{
	final static Logger logger = LoggerFactory.getLogger(TestWorkflowGraphFactory.class);
	
	private GraphWorkflowFactory gfWorkflow;
	private Graph2DotConverter dgf;
	
	public TestWorkflowGraphFactory()
	{
		gfWorkflow = new GraphWorkflowFactory("en");
		dgf = new Graph2DotConverter(new ColorSchemeManager()); 
	}
	
	public void workflow() throws FileNotFoundException
	{
		Workflow workflow = JaxbUtil.loadJAXB("/Volumes/ramdisk/workflow.xml", Workflow.class);
		workflow.getProcesses().getProcess().removeAll(workflow.getProcesses().getProcess().subList(0,3));
		for(org.jeesl.model.xml.module.workflow.Process process : workflow.getProcesses().getProcess())
		{
			Graph g = gfWorkflow.build(process);
			
			
			Clusters clusters = XmlClustersFactory.build();
			clusters.getCluster().add(XmlClusterFactory.build("test", "Simple Test"));
			
			JaxbUtil.info(g);
			
			dgf.build(g);

			System.exit(1);
		}
	}
	
	public static void main(String[] args) throws Exception
    {
		JeeslUtilTestBootstrap.init();
		
		TestWorkflowGraphFactory test = new TestWorkflowGraphFactory();
		test.workflow();
    }
}