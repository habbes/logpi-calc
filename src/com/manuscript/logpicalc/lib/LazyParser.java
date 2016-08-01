package com.manuscript.logpicalc.lib;

import java.util.List;

public class LazyParser extends Parser {
	
	private ParserFunction func = null;
	private Parser parser = null;
	
	public LazyParser(ParserFunction f){
		func = f;
	}

	@Override
	public ParserResult parse(List<Token> tokens, int pos) throws ParsingException {
		if(parser == null){
			parser = func.call();
		}
		return parser.parse(tokens, pos);
	}

}
