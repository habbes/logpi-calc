package com.manuscript.logpicalc.lib;

import java.util.List;

public class Parsers {
	public static final NumberParser number = new NumberParser();
	
	public static final ExpressionParser expression = new ExpressionParser();
	
	public static final TimesParser times = new TimesParser();
	
	public static final PlusParser plus = new PlusParser();
	
	public static final MinusParser minus = new MinusParser();
	
	public static ParserResult parse(List<Token> tokens, int pos) throws ParsingException{
		return parse(tokens, pos, Integer.MAX_VALUE);
	}
	
	public static ParserResult parse(List<Token> tokens, int pos, int precedence) throws ParsingException{
		
		Expression exp = null;
		ParserResult res = null;
		
		while(pos < tokens.size()){
			res = number.parse(tokens, pos);
			if(res != null){
				pos = res.getPos();
				exp = ((ParsedExpression) res.getValue()).getExpression();
				continue;
			}
			
			if(! (precedence > 1))
				break;
			
			res = times.parse(tokens, pos);
			if(res != null){
				pos = res.getPos();
				MultExpression texp = (MultExpression) ((ParsedExpression) res.getValue()).getExpression();
				if(exp != null){
					if(texp.getPrecedence() > exp.getPrecedence()){
						texp.setLeft(exp);
					}
					else {
						((OperatorExpression)exp).setLeft(texp);
					}
				}
				exp = texp;
				continue;
			}
			
			if(!(precedence > 4)){
				break;
			}
			
			res = plus.parse(tokens, pos);			
			if(res != null){
				pos = res.getPos();
				AddExpression texp =  (AddExpression) ((ParsedExpression) res.getValue()).getExpression();
				if(exp != null){
					if(texp.getPrecedence() > exp.getPrecedence()){
						texp.setLeft(exp);
					}
					else {
						((OperatorExpression)exp).setLeft(texp);
					}
				}
				exp = texp;
				continue;
			}
			
			/*
			res = times.parse(tokens, pos);
			if(res != null){
				pos = res.getPos();
				MultExpression mexp = (MultExpression) ((ParsedExpression) res.getValue()).getExpression();
				if(exp != null){
					mexp.setLeft(exp);
				}
				exp = mexp;
				continue;
			}
			
			
			
			res = minus.parse(tokens, pos);
			if(res != null){
				pos = res.getPos();
				SubtractExpression subexp = (SubtractExpression) ((ParsedExpression) res.getValue()).getExpression();
				if(exp != null){
					subexp.setLeft(exp);
				}
				exp = subexp;
				continue;
			}*/
			break;
		}
		
		if(res == null){
			return null;
		}
		return new ParserResult(new ParsedExpression(res.getValue().getToken(),exp), pos);
		
	}
}
