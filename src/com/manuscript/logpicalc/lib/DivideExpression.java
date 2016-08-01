package com.manuscript.logpicalc.lib;

/**
 * Expression representing a division.
 * @author Habbes
 *
 */
public class DivideExpression extends MultExpression {
	
	/**
	 * Returns the value of division
	 */
	@Override
	public double evaluate() {
		return left.evaluate() / right.evaluate();
	}
	
	/**
	 * Gets the precedence level
	 * @return precedence level
	 */
	@Override
	public int getPrecedence(){
		return 4;
	}

}
