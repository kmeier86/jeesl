package net.sf.ahtutils.controller.factory.ejb.status;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbDescriptionFactory<D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(EjbDescriptionFactory.class);
	
    final Class<D> clDescription;
	
    public EjbDescriptionFactory(final Class<D> clDescription)
    {
        this.clDescription = clDescription;
    } 
    
    public static <D extends UtilsDescription> EjbDescriptionFactory<D> createFactory(final Class<D> clDescription)
    {
        return new EjbDescriptionFactory<D>(clDescription);
    }
	
	public D create(Description description) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		if(!description.isSetKey()){throw new UtilsIntegrityException("Key not set: "+JaxbUtil.toString(description, new AhtUtilsNsPrefixMapper()));}
		if(!description.isSetValue()){throw new UtilsIntegrityException("Value not set: "+JaxbUtil.toString(description, new AhtUtilsNsPrefixMapper()));}
    	return create(description.getKey(),description.getValue());
	}
    
	public D create(String key, String value) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		if(key==null){throw new UtilsIntegrityException("Key not set");}
		if(value==null){throw new UtilsIntegrityException("Value not set");}
		D d = clDescription.newInstance();
    	d.setLang(value);
    	d.setLkey(key);
    	return d;
	}
	
	public Map<String,D> create(Descriptions descriptions) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		if(descriptions!=null && descriptions.isSetDescription()){return create(descriptions.getDescription());}
		else{return  new Hashtable<String,D>();}
	}
	
	public Map<String,D> create(List<Description> lDescriptions) throws InstantiationException, IllegalAccessException, UtilsIntegrityException
	{
		Map<String,D> map = new Hashtable<String,D>();
		for(Description desc : lDescriptions)
		{
			D d = create(desc);
			map.put(d.getLkey(), d);
		}
		return map;
	}
}