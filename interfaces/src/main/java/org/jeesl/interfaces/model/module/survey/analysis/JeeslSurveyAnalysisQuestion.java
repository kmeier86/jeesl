package org.jeesl.interfaces.model.module.survey.analysis;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyAnalysisQuestion<L extends UtilsLang, D extends UtilsDescription,
					QUESTION extends JeeslSurveyQuestion<L,D,?,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
					QE extends UtilsStatus<QE,L,D>,
					SCORE extends JeeslSurveyScore<L,D,?,?,?,?,?,?,?,?,QUESTION,QE,SCORE>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					MATRIX extends JeeslSurveyMatrix<L,D,?,?,?,?,?,?,?,?,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					DATA extends JeeslSurveyData<L,D,?,?,?,?,?,?,?,?,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					OPTIONS extends JeeslSurveyOptionSet<L,D,?,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,?,?,?,?,?,?,?,?,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
					DOMAIN extends JeeslSurveyDomain<L,D,?,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					PATH extends JeeslSurveyDomainPath<L,D,?,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
					ANALYSIS extends JeeslSurveyAnalysis<L,D,?>,
					AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
					AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
					ATT extends UtilsStatus<ATT,L,D>
								>
			extends EjbWithId,EjbWithParentAttributeResolver,EjbSaveable,
					EjbWithLang<L>
{
	public enum Attributes{analysis,question}
	
	ANALYSIS getAnalysis();
	void setAnalysis(ANALYSIS analysis);
	
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
}