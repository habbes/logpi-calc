package com.manuscript.logpicalc.lib;

public class Token {
	
	private String value;
	private TokenExpression expr;
	
	public Token(String v, TokenExpression e){
		value = v;
		expr = e;
	}
	
	public String getValue(){
		return value;
	}
	
	public int getTag(){
		return expr.getTag();
	}
	
	public String getName(){
		return expr.getName();
	}
	
	public TokenExpression getExpression(){
		return expr;
	}	
	
}
