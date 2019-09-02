package org.jeesl.interfaces.model.component;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.status.JeeslWithCategory;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslHydroYear <L extends UtilsLang, D extends UtilsDescription,
HD extends UtilsStatus<HD,L,D>,
HY extends JeeslHydroYear<L,D,HD,HY>>
extends Serializable,EjbSaveable,EjbRemoveable,EjbWithLang<L>,EjbWithDescription<D>,EjbWithCode,EjbWithPosition,JeeslWithCategory<HD>
{


	@Override
	void setPosition(int position);

	@Override
	int getPosition();

	@Override
	void setCode(String code);

	@Override
	String getCode();

	void setValidUntil(Date validUntill);

	Date getValidUntil();

	void setValidFrom(Date validFrom);

	Date getValidFrom();

	void setDecade(HD decade);

	HD getDecade();
	@Override
	HD getCategory();
	@Override
	void setCategory(HD category);


}