package org.jeesl.factory.xml.module.bb;

import java.util.List;

import org.jeesl.model.xml.module.bb.Message;
import org.jeesl.model.xml.module.bb.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMessagesFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMessagesFactory.class);
	
	public static Messages build() {return new Messages();}
	
	public static Messages build(List<Message> list) {Messages xml = build(); xml.getMessage().addAll(list);return xml;}
}