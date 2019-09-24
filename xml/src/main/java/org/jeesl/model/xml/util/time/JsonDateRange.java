package org.jeesl.model.xml.util.time;

import java.io.Serializable;
import java.util.Date;

public class JsonDateRange implements Serializable
{
	public static final long serialVersionUID=1;

	private Date startDate;
	public Date getStartDate() {return startDate;}
	public void setStartDate(Date startDate) {this.startDate=startDate;}
	
	private Date endDate;
	public Date getEndDate() {return endDate;}
	public void setEndDate(Date endDate) {this.endDate=endDate;}
}