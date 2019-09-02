package org.jeesl.util.comparator.ejb.component.sb;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.component.JeeslHydroYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;




public class HydroYearComparator<L extends UtilsLang,D extends UtilsDescription,
HD extends UtilsStatus<HD,L,D>,
HY extends JeeslHydroYear<L,D,HD,HY>>
{
    final static Logger logger = LoggerFactory.getLogger(HydroYearComparator.class);

    public enum Type {code};

    public Comparator<HY> factory(Type type)
    {
        Comparator<HY> c = null;
        HydroYearComparator factory = new HydroYearComparator();
        switch (type)
        {
            case code: c = factory.new CodeComparator();break;
        }

        return c;
    }

    private class CodeComparator implements Comparator<HY>
    {
    	@Override
		public int compare(HY a, HY b)
        {
            CompareToBuilder ctb = new CompareToBuilder();
            ctb.append(a.getCode(),b.getCode());
            return ctb.toComparison();
        }
    }

}
