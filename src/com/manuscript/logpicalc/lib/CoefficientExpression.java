package com.manuscript.logpicalc.lib;

/**
 * Expression representing a coefficient.
 * Created when a number directly precedes an opening bracket or function
 * @author Habbes
 *
 */
public class CoefficientExpression extends MultExpression {
	/**
	 * Gets the precedence level of this expression
	 * @return precedence level
	 */
	public int getPrecedence(){
		return 2;
	}
}
