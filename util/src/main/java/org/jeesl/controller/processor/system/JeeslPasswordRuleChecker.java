package org.jeesl.controller.processor.system;

import java.util.regex.*;

public class JeeslPasswordRuleChecker
{
	private final String regexDigits = "(.*?\\d){%d,}.*?";
	private final String regexLower = "(.*?[a-z]){%d,}.*?";
	private final String regexUpper = "(.*?[A-Z]){%d,}.*?";
	private final String regexSymbols = "(.*?[_.*+:#!?%%{}\\|@\\[\\];=\"&$\\\\/,()-]){%d,}.*?";
	
	public JeeslPasswordRuleChecker()
	{		
		
	}
	
	protected boolean validLength(String pwd, int min)
	{
		return pwd.length()>=min;
	}
	
	protected boolean validDigits(String pwd, int min)
	{
		return Pattern.matches(String.format(regexDigits, min), pwd);
	}
	
	protected boolean validLower(String pwd, int min)
	{
		return Pattern.matches(String.format(regexLower, min), pwd);
	}
	
	protected boolean validUpper(String pwd, int min)
	{
		return Pattern.matches(String.format(regexUpper, min), pwd);
	}
	
	protected boolean validSymbols(String pwd, int min)
	{
		return Pattern.matches(String.format(regexSymbols, min), pwd);
	}
}