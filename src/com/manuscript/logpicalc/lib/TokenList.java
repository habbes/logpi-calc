package com.manuscript.logpicalc.lib;

import java.util.ArrayList;

public class TokenList {
	
	private static ArrayList<TokenExpression> list = initList();
	private static ArrayList<TokenExpression> compoundList = initCompoundList();
	private static ArrayList<TokenExpression> bracketsList = initBracketsList();
	private static ArrayList<TokenExpression> bracketNumList = initBracketNumList();
	
	public static ArrayList<TokenExpression> getList(){
		return list;
	}
	
	public static ArrayList<TokenExpression> initBracketNumList(){
		ArrayList<TokenExpression> l = new ArrayList<TokenExpression>();
		l.add(new TokenExpression("\\)", TokenExpression.RESERVED, "openbracket"));
		l.add(new TokenExpression("((\\d*\\.\\d+)|(\\d+\\.\\d*)|(\\d+))([Ee][\\+\\-]?\\d+)?", TokenExpression.NUMBER, "number"));
		return l;
	}
	
	public static ArrayList<TokenExpression> initBracketsList(){
		ArrayList<TokenExpression> l = new ArrayList<TokenExpression>();
		l.add(new TokenExpression("\\(", TokenExpression.RESERVED, "openbracket"));
		l.add(new TokenExpression("\\)", TokenExpression.RESERVED, "closedbracket"));
		return l;
	}
	
	public static ArrayList<TokenExpression> getBracketNum(){
		return bracketNumList;
	}
	
	public static ArrayList<TokenExpression> getBrackets(){
		return bracketsList;
	}
	
	public static ArrayList<TokenExpression> getCompoundItems(){
		return compoundList;
	}
	
	public static ArrayList<TokenExpression> initCompoundList(){
		ArrayList<TokenExpression> clist = new ArrayList<TokenExpression>();
		clist.add(new TokenExpression("\\s+", TokenExpression.NONE, "whitespace"));
		clist.add(new TokenExpression("\\(", TokenExpression.RESERVED, "openbracket"));
		clist.add(new TokenExpression("[A-Za-z]+\\w*", TokenExpression.ID, "identifier"));
		clist.add(new TokenExpression("([\\-\\+]\\s*)?((\\d*\\.\\d+)|(\\d+\\.\\d*)|(\\d+))([Ee][\\+\\-]?\\d+)?", TokenExpression.NUMBER, "number"));
		return clist;
	}
	
	public static Token getTimesToken(){
		return new Token("*", new TokenExpression("(?<=[\\)\\w\\d_]\\s{0,10})\\*", TokenExpression.RESERVED, "times"));
	}
	
	public static Token getCoefficientToken(){
		return new Token("*", new TokenExpression("(?<=[\\)\\w\\d_]\\s{0,10})\\*", TokenExpression.RESERVED, "coefficient"));
	}
	
	private static ArrayList<TokenExpression> initList(){
		ArrayList<TokenExpression> list = new ArrayList<TokenExpression>();
		
		list.add(new TokenExpression("\\s+", TokenExpression.NONE, "whitespace"));
		//operator should not be preceded by another operator
		list.add(new TokenExpression("(?<=[\\)\\w\\d_]\\s{0,10})\\+", TokenExpression.RESERVED, "plus"));
		list.add(new TokenExpression("(?<=[\\)\\w\\d_]\\s{0,10})\\-", TokenExpression.RESERVED, "minus"));
		list.add(new TokenExpression("(?<=[\\)\\w\\d_]\\s{0,10})\\*", TokenExpression.RESERVED, "times"));
		
		list.add(new TokenExpression("(?<=[\\)\\w\\d_]\\s{0,10})/", TokenExpression.RESERVED, "divide"));
		list.add(new TokenExpression("(?<=[\\)\\w\\d_]\\s{0,10})\\^", TokenExpression.RESERVED, "power"));
		list.add(new TokenExpression("[\\+\\-](?=\\s*[\\(\\+\\-])", TokenExpression.RESERVED, "sign"));
		
		list.add(new TokenExpression(",", TokenExpression.RESERVED, "comma"));
		list.add(new TokenExpression("\\)\\s*\\(", TokenExpression.COMPOUND, "bracketbracket"));
		list.add(new TokenExpression("\\)\\s*((\\d*\\.\\d+)|(\\d+\\.\\d*)|(\\d+))([Ee][\\+\\-]?\\d+)?", TokenExpression.COMPOUND, "bracketnumber"));
		list.add(new TokenExpression("\\(", TokenExpression.RESERVED, "openbracket"));
		list.add(new TokenExpression("\\)", TokenExpression.RESERVED, "closedbracket"));
		
		list.add(new TokenExpression("([\\-\\+]\\s*)?((\\d*\\.\\d+)|(\\d+\\.\\d*)|(\\d+))([Ee][\\+\\-]?\\d+)?+\\s*[A-Za-z]+\\w*",
				TokenExpression.COMPOUND,"numberexpression"));
		list.add(new TokenExpression("([\\-\\+]\\s*)?((\\d*\\.\\d+)|(\\d+\\.\\d*)|(\\d+))([Ee][\\+\\-]?\\d+)?+\\s*\\(",
				TokenExpression.COMPOUND, "numberbracket"));
		
		
		list.add(new TokenExpression("([\\-\\+]\\s*)?((\\d*\\.\\d+)|(\\d+\\.\\d*)|(\\d+))([Ee][\\+\\-]?\\d+)?", TokenExpression.NUMBER, "number"));
		list.add(new TokenExpression("[A-Za-z]+\\w*", TokenExpression.ID, "identifier"));
		//list.add(new TokenExpression("(?<=[\\)\\d\\.])\\s*(?=[\\(A-Za-z])", TokenExpression.RESERVED, "coefficient"));
		
		return list;
	}
}
