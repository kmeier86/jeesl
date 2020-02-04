package org.jeesl.interfaces.model.system.graphic.core;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslGraphicFigure<L extends JeeslLang, D extends JeeslDescription,
								G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<FS,L,D>>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithPositionVisible,EjbWithParentAttributeResolver
{
	public enum Attributes{graphic}
	public static enum Style{circle,square,triangle}
	
	G getGraphic();
	void setGraphic(G graphic);
	
	FS getStyle();
	void setStyle(FS style);
	
	boolean isCss();
	void setCss(boolean css);
	
	double getSize();
	void setSize(double size);
	
	String getColor();
	void setColor(String color);

	double getOffsetX();
	void setOffsetX(double offsetX);
	
	double getOffsetY();
	void setOffsetY(double offsetY);
	
	double getRotation();
	void setRotation(double rotation);
}