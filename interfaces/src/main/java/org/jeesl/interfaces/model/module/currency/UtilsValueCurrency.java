package org.jeesl.interfaces.model.module.currency;

import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.util.finance.UtilsFinance;

public interface UtilsValueCurrency<C extends UtilsCurrency<L>, L extends JeeslLang> extends UtilsFinance
{
	C getCurrency();
	void setCurrency(C currency);
}