package com.manuscript.logpicalc.lib;

import java.util.List;

public abstract class Parser {
	public abstract ParserResult parse(List<Token> tokens, int pos) throws ParsingException;
}
