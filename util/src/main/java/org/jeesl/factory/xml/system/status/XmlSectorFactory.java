package org.jeesl.factory.xml.system.status;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Sector;

public class XmlSectorFactory<S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSectorFactory.class);
		
	private String localeCode;
	private Sector q;
	
//	public XmlSectorFactory(Query q){this(q.getLang(),q.getType());}
	public XmlSectorFactory(Sector q){this(null,q);}
	public XmlSectorFactory(String localeCode, Sector q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public Sector build(S ejb){return build(ejb,null);}
	public Sector build(S ejb, String group)
	{
		Sector xml = new Sector();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		xml.setGroup(group);
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		if(q.isSetLabel() && localeCode!=null)
		{
			if(ejb.getName()!=null)
			{
				if(ejb.getName().containsKey(localeCode)){xml.setLabel(ejb.getName().get(localeCode).getLang());}
				else
				{
					String msg = "No translation "+localeCode+" available in "+ejb;
					logger.warn(msg);
					xml.setLabel(msg);
				}
			}
			else
			{
				String msg = "No @name available in "+ejb;
				logger.warn(msg);
				xml.setLabel(msg);
			}
		}
		
		return xml;
	}
	
	public static <E extends Enum<E>> Sector code(E code){return code(code.toString());}
	public static Sector code(String code){return build(null,null,code);}
	public static Sector label(String code,String label){return build(null,code,label);}
	
	public static Sector build(String key, String code, String label)
	{
		Sector xml = new Sector();
		xml.setKey(key);
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}