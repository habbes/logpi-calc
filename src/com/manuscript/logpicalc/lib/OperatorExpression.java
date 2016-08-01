package com.manuscript.logpicalc.lib;

/**
 * Base class for Expression representing binary operators.
 * @author Habbes
 *
 */
abstract public class OperatorExpression extends Expression {
	
	protected Expression left;
	protected Expression right;
	
	/**
	 * Default constructor.
	 * @param l left operand
	 * @param r right operand
	 */
	public OperatorExpression(Expression l, Expression r){
		left = l;
		right = r;
	}
	
	/**
	 * Sets the left operand.
	 * @param l
	 */
	public void setLeft(Expression l){
		left = l;
	}
	
	/**
	 * Sets the right operand.
	 * @param r
	 */
	public void setRight(Expression r){
		right = r;
	}
	
	/**
	 * Returns the result of the operation.
	 */
	@Override
	abstract public double evaluate();
	
	/**
	 * Gets the precedence level of the operator.
	 */
	abstract public int getPrecedence();

}
