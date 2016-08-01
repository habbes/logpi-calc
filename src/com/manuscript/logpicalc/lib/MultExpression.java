package com.manuscript.logpicalc.lib;

/**
 * Expression representing a multiplication.
 * @author Habbes
 *
 */
public class MultExpression extends OperatorExpression {
	
	
	/**
	 * Constructor that accepts left operand. Right operand defaulted to 1.
	 * @param l Left operand.
	 */
	public MultExpression(Expression l){
		super(l, new NumberExpression(1d));
	}
	
	/**
	 * Default constructor. All operands defaulted to 1.
	 */
	public MultExpression(){
		this(new NumberExpression(1d));
	}
	
	/**
	 * Returns the product of the operands.
	 */
	@Override
	public double evaluate() {
		return left.evaluate() * right.evaluate();
	}
	
	/**
	 * Gets the precedence level of the expression
	 * @return precedence level
	 */
	@Override
	public int getPrecedence(){
		return 4;
	}

}
