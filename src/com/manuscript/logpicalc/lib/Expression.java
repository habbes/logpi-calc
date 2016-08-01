package com.manuscript.logpicalc.lib;

/**
 * Base class for all Expressions
 * @author Habbes
 *
 */
public abstract class Expression {
	/**
	 * Maximum precedence an expression can have. This is the highest priority for expressions.
	 */
	public static final int MAX_PREC = Integer.MAX_VALUE;
	
	protected int precedence = MAX_PREC;
	protected String type = "expression";
	
	/**
	 * Returns the value of the expression.
	 * @return value of the expression
	 */
	public abstract double evaluate();
	
	/**
	 * Gets the precedence level of the expression. The smaller the value, the higher the priority.
	 * @return precedence level
	 */
	public int getPrecedence(){
		return precedence;
	}
	
	/**
	 * Gets the type of expression.
	 * @return type of expression
	 */
	public String getType(){
		return type;
	}
}
