package org.jeesl.factory.json.module.survey;

import java.util.ArrayList;

import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.factory.builder.module.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.model.json.survey.Condition;
import org.jeesl.model.json.survey.Option;
import org.jeesl.model.json.survey.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JsonSurveyQuestionFactory<L extends UtilsLang,D extends UtilsDescription,
										SECTION extends JeeslSurveySection<L,D,?,SECTION,QUESTION>,
										QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
										CONDITION extends JeeslSurveyCondition<QUESTION,QE,OPTION>,
										QE extends UtilsStatus<QE,L,D>,
										SCORE extends JeeslSurveyScore<L,D,?,QUESTION>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
										MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
										DATA extends JeeslSurveyData<L,D,?,ANSWER,?>,
										OPTIONS extends JeeslSurveyOptionSet<L,D,?,OPTION>,
										OPTION extends JeeslSurveyOption<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(JsonSurveyQuestionFactory.class);
	
	private JeeslSurveyCoreFacade<L,D,?,?,?,?,?,?,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?> fSurvey;
	private SurveyTemplateFactoryBuilder<L,D,?,?,?,?,?,?,?,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate;
	
	private final String localeCode;
	private Question q;
	
	private JsonSurveyOptionFactory<OPTION> jfOption;
	private JsonSurveyConditionFactory<CONDITION,QE,OPTION> jfCondition;
	
	public JsonSurveyQuestionFactory(Question q){this(null, q,null,null);}
	public JsonSurveyQuestionFactory(String localeCode, Question q){this(localeCode, q,null,null);}
	public JsonSurveyQuestionFactory(String localeCode, Question q,
			SurveyTemplateFactoryBuilder<L,D,?,?,?,?,?,?,?,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate,
			JeeslSurveyCoreFacade<L,D,?,?,?,?,?,?,?,?,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,?> fSurvey)
	{
		this.localeCode=localeCode;
		this.q=q;
		this.fbTemplate=fbTemplate;
		this.fSurvey=fSurvey;
		if(q.getOptions()!=null && !q.getOptions().isEmpty()) {jfOption = new JsonSurveyOptionFactory<OPTION>(localeCode,q.getOptions().get(0));}
		if(q.getConditions()!=null && !q.getConditions().isEmpty()) {jfCondition = new JsonSurveyConditionFactory<CONDITION,QE,OPTION>(localeCode,q.getConditions().get(0));}
	}
	
	public Question build(QUESTION ejb)
	{
		Question json = build();
		
		if(q.getId()!=null) {json.setId(ejb.getId());}
		if(q.getVisible()!=null) {json.setVisible(ejb.isVisible());}
		if(q.getPosition()!=null) {json.setPosition(ejb.getPosition());}
		if(q.isSetCode()){json.setCode(ejb.getCode());}
		if(q.isSetTopic() && ejb.getName().containsKey(localeCode)){json.setTopic(ejb.getName().get(localeCode).getLang());}
		
		if(q.isSetQuestion() && ejb.getText().containsKey(localeCode)){json.setQuestion(ejb.getText().get(localeCode).getLang());}
		if(q.isSetRemark()){json.setRemark(ejb.getRemark());}
		
		if(q.isSetCalculateScore()){json.setCalculateScore(ejb.getCalculateScore());}
		if(q.isSetMinScore()){json.setMinScore(ejb.getMinScore());}
		if(q.isSetMaxScore()){json.setMaxScore(ejb.getMaxScore());}
		
		if(q.isSetShowBoolean()){json.setShowBoolean(ejb.getShowBoolean());}
		if(q.isSetShowInteger()){json.setShowInteger(ejb.getShowInteger());}
		if(q.setShowDouble()){json.setShowDouble(ejb.getShowDouble());}
		if(q.isSetShowText()){json.setShowText(ejb.getShowText());}
		if(q.isSetShowScore()){json.setShowScore(ejb.getShowScore());}
		if(q.isSetShowRemark()){json.setShowRemark(ejb.getShowRemark());}
		if(q.getShowDate()!=null){json.setShowDate(ejb.getShowDate());}
		if(q.isSetShowSelectOne()){json.setShowSelectOne(ejb.getShowSelectOne());}
		if(q.isSetShowSelectMulti()){json.setShowSelectMulti(ejb.getShowSelectMulti());}
		if(q.getShowMatrix()!=null){json.setShowMatrix(ejb.getShowMatrix());}
		
		if(q.getOptions()!=null && !q.getOptions().isEmpty())
		{
			ejb = fSurvey.load(ejb);
			if(!ejb.getOptions().isEmpty()) {json.setOptions(new ArrayList<Option>());}
			for(OPTION option : ejb.getOptions())
			{
				json.getOptions().add(jfOption.build(option));
			}
		}
		
		if(q.getCondition()!=null) {json.setCondition(ejb.getRenderCondition());}
		if(q.getConditions()!=null && !q.getConditions().isEmpty() && fSurvey!=null && fbTemplate!=null)
		{
			json.setConditions(new ArrayList<Condition>());
			for(CONDITION condition : fSurvey.allForParent(fbTemplate.getClassCondition(), ejb))
			{
				json.getConditions().add(jfCondition.build(condition));
			}
		}	
		
		return json;
	}
	
	public static Question build() {return new Question();}
	public static Question id(long id)
	{
		Question json = build();
		json.setId(id);
		return json;
	}
}