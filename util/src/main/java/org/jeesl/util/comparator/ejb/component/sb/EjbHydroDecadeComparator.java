package org.jeesl.util.comparator.ejb.component.sb;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.component.JeeslHydroDecade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class EjbHydroDecadeComparator
{
    final static Logger logger = LoggerFactory.getLogger(EjbHydroDecadeComparator.class);

    public enum Type {name};

    public static Comparator<JeeslHydroDecade> factory(Type type)
    {
        Comparator<JeeslHydroDecade> c = null;
        EjbHydroDecadeComparator factory = new EjbHydroDecadeComparator();
        switch (type)
        {
            case name: c = factory.new NameComparator();break;
        }

        return c;
    }

    private class NameComparator implements Comparator<JeeslHydroDecade>
    {
    	public int compare(JeeslHydroDecade a, JeeslHydroDecade b)
        {
            CompareToBuilder ctb = new CompareToBuilder();
            ctb.append(a.getName(),b.getName());
            return ctb.toComparison();
        }
    }
}
