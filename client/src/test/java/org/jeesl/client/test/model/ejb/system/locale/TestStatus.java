package org.jeesl.client.test.model.ejb.system.locale;

import java.util.Random;

import org.jeesl.client.model.ejb.system.locale.Lang;
import org.jeesl.client.model.ejb.system.locale.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStatus
{
	final static Logger logger = LoggerFactory.getLogger(TestStatus.class);
	
	private Random rnd;
	private String code;
	
	@Before
	public void init()
	{
		rnd = new Random();
		code = "code"+rnd.nextInt(1000000000);
	}

	@After
	public void close(){rnd=null;}
	
	@Test
    public void addStatus()
    {
    	Status ejb = create(rnd,code);
 //   	ejb = fUtil.persist(ejb);
 //   	Assert.assertTrue(ejb.getId()>0);
    }
    
    public static Status create(Random rnd, String code)
    {
    	Status ejb = new Status();
    	ejb.setCode(code);
    	ejb.setVisible(true);
    	ejb.getName().put("en", create("en", "en"+rnd.nextInt(10000)));
    	ejb.getName().put("de", create("de", "de"+rnd.nextInt(10000)));
    	return ejb;
    }
    
    public static Lang create(String key, String lang)
    {
    	Lang ejb = new Lang();
    	ejb.setLang(lang);
    	ejb.setLkey(key);
    	return ejb;
    }
}