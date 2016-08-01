package com.manuscript.logpicalc.lib;

/**
 * Exception that occurs during parsing.
 * @author Habbes
 *
 */
public class ParseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6482009740448707863L;

	public ParseException(String msg){
		super(msg);
	}
}
