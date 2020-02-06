package org.jeesl.controller.processor.system;

import java.util.regex.*;

public class JeeslPasswordRuleChecker
{
	private final String regexDigits = ".*\\d.*";
	private final String regexLower = ".*[a-z].*";
	private final String regexUpper = ".*[A-Z].*";
	private final String regexSymbols = ".*[\\W_].*";
	
	public JeeslPasswordRuleChecker()
	{		
		
	}
	
	protected boolean validLength(String pwd, int min)
	{
		return pwd.length()>=min;
	}
	
	protected boolean validDigits(String pwd)
	{
		return Pattern.matches(regexDigits, pwd);
	}
	
	protected boolean validLower(String pwd)
	{
		return Pattern.matches(regexLower, pwd);
	}
	
	protected boolean validUpper(String pwd)
	{
		return Pattern.matches(regexUpper, pwd);
	}
	
	protected boolean validSymbols(String pwd)
	{
		return Pattern.matches(regexSymbols, pwd);
	}
}