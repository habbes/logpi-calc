package com.manuscript.logpicalc.lib;

public class SubtractExpression extends AddExpression {
	
	@Override
	public double evaluate() {
		return left.evaluate() - right.evaluate();
	}
	
	@Override
	public int getPrecedence(){
		return 6;
	}
}