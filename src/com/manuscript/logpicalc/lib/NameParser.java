package com.manuscript.logpicalc.lib;

import java.util.List;

public class NameParser extends Parser {
	
	private String name;
	
	public NameParser(String n){
		name = n;
	}

	@Override
	public ParserResult parse(List<Token> tokens, int pos) {
		Token t = tokens.get(pos);
		if(!t.getName().equals(name)){
			return null;
		}
		ParserResult res = new ParserResult(new ParserResultValue(t), pos + 1);
		return res;
	}

}
