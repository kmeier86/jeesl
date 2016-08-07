package org.jeesl.model.xml.survey;

import org.jeesl.UtilsXmlTestBootstrap;
import org.jeesl.model.xml.text.TestXmlRemark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlUnit;
import net.sf.ahtutils.xml.survey.Question;

public class TestXmlQuestion extends AbstractXmlSurveyTest<Question>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuestion.class);
	
	public TestXmlQuestion(){super(Question.class);}
	public static Question create(boolean withChildren){return (new TestXmlQuestion()).build(withChildren);}   

    public Question build(boolean withChilds)
    {
    	Question xml = new Question();
    	xml.setId(123);
    	xml.setPosition(2);
    	xml.setVisible(true);
    	xml.setCode("myCode");
    	xml.setTopic("myTopic");
    	
    	if(withChilds)
    	{
    		xml.setUnit(TestXmlUnit.create(false));
    		xml.setRemark(TestXmlRemark.create(false));
    		xml.setQuestion(org.jeesl.model.xml.text.TestXmlQuestion.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlQuestion test = new TestXmlQuestion();
		test.saveReferenceXml();
    }
}