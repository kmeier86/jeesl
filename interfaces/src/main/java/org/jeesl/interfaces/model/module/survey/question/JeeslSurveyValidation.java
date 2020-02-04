package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslSurveyValidation<L extends JeeslLang, D extends JeeslDescription,
										QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,?,?,?,?>,
										VALGORITHM extends JeeslSurveyValidationAlgorithm<L,D>>
			extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithDescription<D>,
					EjbWithPositionVisible,EjbWithParentAttributeResolver
{
	public enum Attributes{question}
	
	QUESTION getQuestion();
	void setQuestion(QUESTION question);
	
	VALGORITHM getAlgorithm();
	void setAlgorithm(VALGORITHM algorithm);
	
	String getConfig();
	void setConfig(String config);
}