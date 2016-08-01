package com.manuscript.logpicalc.lib;

import java.util.List;

public class ExpressionParser extends Parser {

	@Override
	public ParserResult parse(List<Token> tokens, int pos) throws ParsingException {
		ParserResult res = null;
		
		if(pos == 0){
			res = Parsers.number.parse(tokens, pos);
			if(! res.equals(null)) return res;
			return null;
		}
		
		res = Parsers.plus.parse(tokens, pos);
		if(! res.equals(null)) return res;
		
		res = Parsers.minus.parse(tokens, pos);
		if(!res.equals(null)) return res;
		
		res = Parsers.number.parse(tokens, pos);
		if(! res.equals(null)) return res;
		
		
		
		
		
		
		
		
		
		
		
		return null;
	}

}
