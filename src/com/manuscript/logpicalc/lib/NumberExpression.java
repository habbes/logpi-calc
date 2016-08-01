package com.manuscript.logpicalc.lib;

/**
 * Expression representing a number.
 * @author Habbes
 *
 */
public class NumberExpression extends Expression {
	
	private double value;
	
	/**
	 * Constructor.
	 * @param num
	 */
	public NumberExpression(double num){
		value = num;
		precedence = MAX_PREC;
		type = "number";
	}
	
	/**
	 * Returns value of the number.
	 * @return number
	 */
	@Override
	public double evaluate() {
		return value;
	}

}
