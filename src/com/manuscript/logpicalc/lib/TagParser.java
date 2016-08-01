package com.manuscript.logpicalc.lib;

import java.util.List;

public class TagParser extends Parser {
	
	private int tag;
	
	public TagParser(int t){
		tag = t;
	}

	@Override
	public ParserResult parse(List<Token> tokens, int pos) {
		Token t = tokens.get(pos);
		if(t.getTag() != tag){
			return null;
		}
		
		return new ParserResult(new ParserResultValue(t), pos + 1);
	}

}
