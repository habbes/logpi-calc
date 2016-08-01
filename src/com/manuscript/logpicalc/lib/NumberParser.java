package com.manuscript.logpicalc.lib;

import java.util.List;

public class NumberParser extends Parser {

	@Override
	public ParserResult parse(List<Token> tokens, int pos) {
		Token t = tokens.get(pos);
		if(t.getTag() != TokenExpression.NUMBER){
			return null;
		}
		return new ParserResult(
				new ParsedExpression(t, new NumberExpression(Double.valueOf(t.getValue()))),
				pos + 1);
	}

}
