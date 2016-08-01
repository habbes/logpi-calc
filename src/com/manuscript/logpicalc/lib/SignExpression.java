package com.manuscript.logpicalc.lib;

public class SignExpression extends Expression {
	
	private Expression exp;
	private int sign = 1;
	
	public SignExpression(int s, Expression e){
		sign = s;
		exp = e;
	}
	public SignExpression(int s){
		this(s, new NumberExpression(1d));
	}
	@Override
	public double evaluate() {
		return sign * exp.evaluate();
	}
	
	public void setExpression(Expression e){
		exp = e;
	}
	
	public int getPrecedence(){
		return Expression.MAX_PREC;
	}

}
