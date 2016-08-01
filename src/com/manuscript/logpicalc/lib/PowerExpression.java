package com.manuscript.logpicalc.lib;

public class PowerExpression extends OperatorExpression {
	
	public PowerExpression(Expression l){
		super(l, new NumberExpression(1d));
	}
	
	public PowerExpression(){
		this(new NumberExpression(1d));
	}

	@Override
	public double evaluate() {
		return Math.pow(left.evaluate(), right.evaluate());
	}

	@Override
	public int getPrecedence() {
		return 3;
	}

}
