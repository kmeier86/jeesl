package org.jeesl.interfaces.model.system.io.dash;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoDashComponent <L extends JeeslLang, D extends JeeslDescription,
DBC extends JeeslIoDashComponent<L,D,DBC>>
extends Serializable,EjbSaveable,EjbRemoveable,
EjbWithParentAttributeResolver,
EjbWithLang<L>,EjbWithDescription<D>,
EjbWithCode,EjbWithPosition
{
public enum Attributes{dashboard}

}