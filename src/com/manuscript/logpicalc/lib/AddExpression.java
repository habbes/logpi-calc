package com.manuscript.logpicalc.lib;

/**
 * Expression representing an addition.
 * @author Habbes
 * @see OperatorExpression
 */
public class AddExpression extends OperatorExpression {
	
	/**
	 * Constructor accepting left operand
	 * @param l	the left operand
	 */
	public AddExpression(Expression l){
		super(l, new NumberExpression(0d));
	}
	
	/**
	 * Class constructor.
	 */
	public AddExpression(){
		this(new NumberExpression(0d));
	}
	
	/**
	 * Returns the sum of the operands.
	 */
	public double evaluate(){
		return left.evaluate() + right.evaluate();
	}
	
	/**
	 * Gets the precedence level of addition
	 * @return precedence level
	 */
	@Override
	public int getPrecedence(){
		return 7;
	}
}
