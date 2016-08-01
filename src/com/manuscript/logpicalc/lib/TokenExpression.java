package com.manuscript.logpicalc.lib;

public class TokenExpression {
	public static final int NONE = 1;
	public static final int RESERVED = 2;
	public static final int NUMBER = 3;
	public static final int ID = 4;
	public static final int COMPOUND = 5;
	
	public String name;
	public int type;
	public String pattern;
	
	public TokenExpression(String p, int t, String n){
		name = n;
		pattern = p;
		type = t;
	}
	
	public String getName(){
		return name;
	}
	
	public int getTag(){
		return type;
	}
	
	public String getPattern(){
		return pattern;
	}
}
