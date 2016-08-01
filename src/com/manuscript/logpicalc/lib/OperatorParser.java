package com.manuscript.logpicalc.lib;

import java.util.List;

public  class  OperatorParser extends Parser {
	
	private OperatorExpression exp;
	private String name;
	
	public OperatorParser(String opname, OperatorExpression e){
		name = opname;
		exp = e;
	}

	@Override
	public ParserResult parse(List<Token> tokens, int pos) throws ParsingException {
		ParserResult res = null;
		ParsedExpression parsedExp = null;
		
		NameParser p = new NameParser(name);
		res = p.parse(tokens, pos);
		if(res == null){
			return null;
		}
		
		pos = res.getPos();
		
		res = Parsers.parse(tokens, pos, exp.getPrecedence());
		
		if(res == null){
			return null;
		}
		
		parsedExp = (ParsedExpression) res.getValue();
		exp.setRight(parsedExp.getExpression());
		
		return new ParserResult(new ParsedExpression(res.getValue().getToken(), exp), res.getPos());
	}

}

