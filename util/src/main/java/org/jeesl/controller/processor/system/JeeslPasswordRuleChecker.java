package org.jeesl.controller.processor.system;

public class JeeslPasswordRuleChecker
{
	
	public JeeslPasswordRuleChecker()
	{		
		
	}
	
	protected boolean validLength(String pwd, int min)
	{
		return pwd.length()>=min;
	}
	
	protected boolean validDigits(String pwd, int min)
	{
		return false;
	}
	
	protected boolean validLower(String pwd, int min)
	{
		return false;
	}
	
	protected boolean validUpper(String pwd, int min)
	{
		return false;
	}
	
	protected boolean validSymbols(String pwd, int min)
	{
		return false;
	}
}