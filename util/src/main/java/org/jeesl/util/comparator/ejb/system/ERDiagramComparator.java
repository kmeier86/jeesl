package org.jeesl.util.comparator.ejb.system;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.system.erdiagram.JeeslERDiagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class ERDiagramComparator<L extends UtilsLang,D extends UtilsDescription,
								C extends UtilsStatus<C,L,D>,
								ERD extends JeeslERDiagram<L,D,C,ERD>>
{
	final static Logger logger = LoggerFactory.getLogger(ERDiagramComparator.class);

    public enum Type {category};

    public Comparator<ERD> factory(Type type)
    {
        Comparator<ERD> c = null;
        ERDiagramComparator<L,D,C,ERD> factory = new ERDiagramComparator<L,D,C,ERD>();
        switch (type)
        {
            case category: c = factory.new CategoryComparator();break;
        }

        return c;
    }

    private class CategoryComparator implements Comparator<ERD>
    {
        @Override
		public int compare(ERD a, ERD b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  if(a.getCategory()!=null && b.getCategory()!=null){ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());}
			  ctb.append(a.getKey(), b.getKey());
			  return ctb.toComparison();
        }
    }
}