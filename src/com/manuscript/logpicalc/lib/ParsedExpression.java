package com.manuscript.logpicalc.lib;

/**
 * ParserResultValue that also contains the parsed Expression.
 * @author Habbes
 *
 */
public class ParsedExpression extends ParserResultValue {
	Expression exp = null;

	public ParsedExpression(Token t) {
		super(t);
	}
	
	public ParsedExpression(Token t, Expression e){
		this(t);
		exp = e;
	}
	
	/**
	 * Sets the parsed expression.
	 * @param e
	 */
	public void setExpression(Expression e){
		exp = e;
	}
	
	/**
	 * Gets the parsed expression.
	 * @return
	 */
	public Expression getExpression(){
		return exp;
	}

}
