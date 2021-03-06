package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.xml.status.Freeze;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFreezeFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFreezeFactory.class);
		
	private Freeze q;
	
	public XmlFreezeFactory(Freeze q)
	{
		this.q=q;
	}
	
	public <S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription> Freeze build(S ejb)
	{
		Freeze xml = new Freeze();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		return xml;
	}
	
	public static Freeze build(String code){return build(code, null);}
	public static Freeze build(String code, String label)
	{
		Freeze xml = new Freeze();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}