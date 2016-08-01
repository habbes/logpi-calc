package com.manuscript.logpicalc.lib;

/**
 * Part of a ParserResult that contains more info about the parsed token.
 * @author Habbes
 *
 */
public class ParserResultValue {
	private Token token;
	
	public ParserResultValue(Token t){
		token = t;
	}
	
	/**
	 * Gets the parsed Token.
	 */
	public Token getToken(){
		return token;
	}
}
