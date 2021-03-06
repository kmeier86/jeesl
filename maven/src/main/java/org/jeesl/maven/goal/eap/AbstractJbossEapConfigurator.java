package org.jeesl.maven.goal.eap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jeesl.controller.config.jboss.JbossConfigurator;
import org.jeesl.controller.config.jboss.JbossModuleConfigurator;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;

public abstract class AbstractJbossEapConfigurator extends AbstractMojo
{
	@Parameter(defaultValue = "INFO")
    protected String log;
	
	protected enum DbType {mysql,mariadb,postgres}
	protected final Set<DbType> setFiles;
	
	public AbstractJbossEapConfigurator()
	{
		setFiles = new HashSet<DbType>();
	}
	
    protected Configuration config()
    {
    	try
		{
			String cfn = ExlpCentralConfigPointer.getFile("jeesl","eapConfig").getAbsolutePath();
			ConfigLoader.add(cfn);
			getLog().info("Using config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {getLog().error("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
		
		Configuration config = ConfigLoader.init();					
		return config;
    }
    
    protected void dbDs(String[] keys, Configuration config, JbossConfigurator jbossConfig) throws IOException
    {
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	String ds=null;
        	switch(dbType)
        	{
        		case mariadb: ds = jbossConfig.createMariaDbDatasource(config,key); break;
        		case mysql: ds = jbossConfig.createMysqlDatasource(config,key); break;		
        		case postgres: ds = jbossConfig.createPostgresDatasource(config,key);break;
        	}
        	if(ds!=null) {getLog().info("DS: "+ds);}
    	}
    }
    
    protected void dbDrivers(String[] keys, Configuration config, JbossConfigurator jbossConfig) throws IOException
    {
    	List<String> log = new ArrayList<String>();
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	switch(dbType)
        	{
        		case mariadb: if(!jbossConfig.driverExists("mariadb"))
								{
									jbossConfig.createMariadbDriver();
									log.add("mariadb");
								}
								break;
        		case mysql: if(!jbossConfig.driverExists("mysql"))
        					{
        						jbossConfig.createMysqlDriver();
        						log.add("mysql");
        					}
        					break;
        		case postgres: if(!jbossConfig.driverExists("postgres"))
							{
								jbossConfig.createPostgresDriver();
								log.add("postgres");
							}
							break;
        	}
    	}
    	getLog().info("DB Drivers: "+StringUtils.join(log, ", "));
    }
    
    protected void dbFiles(String[] keys, Configuration config, JbossModuleConfigurator jbossModule) throws IOException
    {
    	List<String> log = new ArrayList<String>();
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	switch(dbType)
        	{
	        	case mariadb: if(!setFiles.contains(dbType))
							{
								jbossModule.mariaDB();
								log.add(dbType.toString());
								setFiles.add(dbType);
							}
							break;
        		case mysql: if(!setFiles.contains(dbType))
        					{
        						jbossModule.mysql();
        						log.add(dbType.toString());
        						setFiles.add(dbType);
        					}
        					break;
        		case postgres: if(!setFiles.contains(dbType))
							{
								jbossModule.postgres();
								jbossModule.hibernate();
								log.add(dbType.toString());
								setFiles.add(dbType);
							}
				break;
        	}
    	}
    	getLog().info("DB Files: "+StringUtils.join(log, ", "));
    }
}