package com.manuscript.logpicalc.lib;

/**
 * Exception thrown due to bad syntax.
 * @author Habbes
 *
 */
public class BadSyntaxException extends Exception{

	private static final long serialVersionUID = 1846464637509530665L;
	
	public BadSyntaxException(String msg){
		super(msg);
	}
	
}
