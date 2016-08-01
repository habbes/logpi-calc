package com.manuscript.logpicalc.lib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class PrecedenceList {
	
	private LinkedList<Expression> list = new LinkedList<Expression>();
	private PriorityQueue<Expression> precList;
			
	
	public PrecedenceList(List<Token> tokens, int start, int stop) throws ParsingException, EvalException, BadSyntaxException{
		this();
		buildFromTokens(tokens, start, stop);
	}
	
	public PrecedenceList(List<Token> tokens, int start) throws ParsingException, EvalException, BadSyntaxException{
		this();
		buildFromTokens(tokens, start);
	}
	
	public PrecedenceList(List<Token> tokens) throws ParsingException, EvalException, BadSyntaxException{
		this(tokens, 0);
	}
	
	public PrecedenceList(){
		precList = new PriorityQueue<Expression>(10, new Comparator<Expression>(){

			@Override
			public int compare(Expression e1, Expression e2) {
				return (e1.getPrecedence() - e2.getPrecedence());
			}
			
		});
	}
	
	private int findClosingBracket(List<Token> tokens, int pos) throws ParsingException, BadSyntaxException{
		int open = 1;
		int closed = 0;
		while(open > closed && pos < tokens.size()){
			
			if(tokens.get(pos).getName().equals("closedbracket")){
				closed++;
			}
			else if(tokens.get(pos).getName().equals("openbracket")){
				open++;
			}
			else if(tokens.get(pos).getName().equals("bracketnumber") ||
					tokens.get(pos).getName().equals("bracketbracket")){
				closed++;
				
				Token t1, t2;
							
				Token token = tokens.get(pos);
				ArrayList<Token> brackets;
				if(token.getName().equals("bracketnumber")){
					brackets = Lexer.lex(token.getValue(), TokenList.getBracketNum());
				}
				else {
					brackets = Lexer.lex(token.getValue(), TokenList.getBrackets());
				}
				t1 = brackets.get(0);
				t2 = brackets.get(1);
				Token mult = TokenList.getCoefficientToken();
				tokens.remove(pos);
				tokens.add(pos, t2);
				tokens.add(pos, mult);
				tokens.add(pos, t1);
			}
			
			pos++;			
		}
		if(closed != open || pos > tokens.size()){
			throw new ParsingException("You may have forgotten a closing bracket after '" + 
					tokens.get(pos-1).getValue() + "'");
		}
	
		return pos-1;
	}
	
	
	
	
	public ParserResult nextFromTokens(List<Token> tokens, int start, int stop) throws ParsingException, EvalException, BadSyntaxException{
		Token token = tokens.get(start);
		if(token.getName().equals("number")){
			String s = token.getValue().replaceAll("\\s*", "");
			double val = Double.valueOf(s);
			return new ParserResult(					
					new ParsedExpression(token, new NumberExpression(val)), start + 1);
		}
		if(token.getName().equals("times")){
			return new ParserResult(
					new ParsedExpression(token, new MultExpression()), start + 1
					);
		}
		if(token.getName().equals("coefficient")){
			return new ParserResult(
					new ParsedExpression(token, new CoefficientExpression()), start + 1
					);
		}
		if(token.getName().equals("plus")){
			return new ParserResult(
					new ParsedExpression(token, new AddExpression()), start + 1
			);
		}
		if(token.getName().equals("minus")){
			return new ParserResult(
					new ParsedExpression(token, new SubtractExpression()), start + 1
			);
		}
		if(token.getName().equals("divide")){
			return new ParserResult(
					new ParsedExpression(token, new DivideExpression()), start + 1
			);
		}
		if(token.getName().equals("power")){
			return new ParserResult(
					new ParsedExpression(token, new PowerExpression()), start + 1
			);
		}
		if(token.getName().equals("sign")){
			int sign = 1;
			if(token.getValue().equals("-")){
				sign = -1;
			}
			ParserResult res = nextFromTokens(tokens, start + 1, stop);
			Expression e = ((ParsedExpression) res.getValue()).getExpression();
			return new ParserResult(
					new ParsedExpression(token, new SignExpression(sign, e)), res.getPos());
			
		}
		if(token.getName().equals("openbracket")){
			int endpos = findClosingBracket(tokens, start + 1);
			PrecedenceList bracketParser = new PrecedenceList();
			int p = bracketParser.buildFromTokens(tokens, start + 1, endpos);
			if(p > -1) endpos = p;
			Expression e = bracketParser.parse();
			return new ParserResult(
					new ParsedExpression(token, new NumberExpression(e.evaluate())), endpos + 1);
		}
		if(token.getName().equals("comma")){
			return new ParserResult(new ParserResultValue(token), start + 1);
		}
		if(token.getName().equals("identifier")){
			FunctionExpression fe;
			try {
				fe = new FunctionExpression(token.getValue());
			}
			catch (NullPointerException ex){
				throw new ParsingException("The function '" + token.getValue() + "' is unknown");
			}
			int pos = start + 1;
			Token t = (pos < tokens.size())? tokens.get(pos) : null;
			if(((stop >= 0)?pos < stop : true) && (t != null) && (t.getName().equals("openbracket"))){
				pos++;
				int endpos = findClosingBracket(tokens, pos);
				ParserResult res = nextFromTokens(tokens, pos, stop);
				
				t = null;
				Expression param = null;
				PrecedenceList funcParser = null;
				while(res != null){
					t = res.getValue().getToken();
					if(t.getName().equals("closedbracket")||t.getName().equals("bracketnumber")
							|| t.getName().equals("bracketbracket")){
						break;
					}
					if(res.hasSizeChanged()){
						endpos = res.getNewStop();
						
					}
					funcParser = new PrecedenceList();
					
					pos = funcParser.buildTillComma(tokens, pos, endpos);
					
					param = funcParser.parse();
					
					fe.addParam(param);
					res = nextFromTokens(tokens, pos, stop);
					
				}
				
				pos = endpos + 1;
				
			}
			try{
				return new ParserResult(
					new ParsedExpression(token, new NumberExpression(fe.evaluate())), pos);
			}
			catch(IndexOutOfBoundsException ex){
				throw new EvalException(
						String.format("You need to provide more input for function '%s'", fe.getFunction().getName()));
			}
			
		}
		
		if(token.getTag() == TokenExpression.COMPOUND){
			if(token.getName().equals("bracketnumber")||token.getName().equals("bracketbracket")){
				throw new ParsingException("You have a closing bracket that was not opened");
			}
			Token t1, t2;
			
			//turns 2pi into  2 * pi and 2(3+4) into 2 * (3 + 4)
			ArrayList<Token> sepTokens = Lexer.lex(token.getValue(),TokenList.getCompoundItems());
			t1 = sepTokens.get(0);
			t2 = sepTokens.get(1);
			
			
			
			Token mult = TokenList.getCoefficientToken();
			tokens.remove(start);
			tokens.add(start, t2);
			tokens.add(start, mult);
			tokens.add(start, t1);
			
			stop = (stop > -1)? stop + 2 : tokens.size();
			
			ParserResult r =  nextFromTokens(tokens, start, stop);
			r.setSizeChanged(true);
			r.setNewStop(stop);
			return r;
		}
		return null;
	}
	
	private int buildTillComma(List<Token> tokens, int start, int stop) throws ParsingException, EvalException, BadSyntaxException{
		int pos = start;
		ParserResult r;
		Expression e;
		
		while(pos < stop){
			
			r = nextFromTokens(tokens, pos, stop);
			
			if(r == null) break;
			if(r.hasSizeChanged()){
				stop = r.getNewStop();
				
			}
			pos = r.getPos();
			if(r.getValue().getToken().getName().equals("comma")){
				
				break;
			}
			e = ((ParsedExpression) r.getValue()).getExpression();
			list.add(e);
			if(e.getPrecedence() < Expression.MAX_PREC){
				precList.add(e);
			}
		}
		return pos;
	}
	
	public int buildFromTokens(List<Token> tokens, int start, int stop) throws ParsingException, EvalException, BadSyntaxException{
		int pos = start;
		ParserResult r;
		Expression e;
		while((stop > -1)?pos < stop : pos < tokens.size()){
			r = nextFromTokens(tokens, pos, stop);
			if(r == null){
				throw new ParsingException("The character(s) '" + tokens.get(pos).getValue() + "' may be misplaced");
			}
			if(stop > - 1 && r.hasSizeChanged()){
				stop = r.getNewStop();
			}
			try{
				e = ((ParsedExpression) r.getValue()).getExpression();
			}
			catch(ClassCastException ex){
				throw new ParsingException("The character(s) '" + tokens.get(pos).getValue() + "' may be misplaced");
			}
			list.add(e);
			pos = r.getPos();
			if(e.getPrecedence() < Expression.MAX_PREC){
				if(list.indexOf(e) - list.indexOf(precList.peek()) <= 1){
					throw new ParsingException("The operator '" + r.getValue().getToken().getValue() + "' was unexpected");
				}
				precList.add(e);
			}
		}
		return pos;
	}
	
	public void buildFromTokens(List<Token> tokens, int start) throws ParsingException, EvalException, BadSyntaxException{
		buildFromTokens(tokens, start, -1);
	}
	
	public Expression parse() throws ParsingException{
		Expression next = null;
		OperatorExpression exp = null;
		
		if(list.size() == 1){
			return list.get(0);
		}
		
		while(list.size() != 1){
			next = precList.poll();
			if(next == null){
				throw new ParsingException("You may have forgotten an operator or expression");
			}
			exp = (OperatorExpression) next;
			int pos = list.indexOf(next);
			if(pos > 0){
				exp.setLeft(list.get(pos - 1));
				list.remove(pos - 1);
				pos--;
			}
			if(list.size() <= pos + 1){
				throw new ParsingException("You may have forgotten an operand");
			}
			exp.setRight(list.get(pos + 1));
			list.remove(pos + 1);			
		}
		
		return exp;
	}
}
