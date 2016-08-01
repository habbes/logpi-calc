package com.manuscript.logpicalc.lib;

/**
 * Expression representing a function.
 * @author Habbes
 *
 */
public class FunctionExpression extends Expression {
	
	private Function function = null;
	
	/**
	 * Constructor accepting a Function.
	 * @param f function
	 */
	public FunctionExpression(Function f){
		function = f;
	}
	
	/**
	 * Constructor accepting name of function.
	 * @param name name of the function
	 * @throws EvalException
	 */
	public FunctionExpression(String name) throws EvalException{
		
		function = FunctionList.getFunction(name);
		
		if(function == null){
			throw new EvalException("Function '" + name + "' not found");
		}
	}
	
	/**
	 * Gets the function associated with this expression.
	 * @return function
	 */
	public Function getFunction(){
		return function;
	}
	
	/**
	 * Add an Expression as parameter to the function.
	 * @param e parameter
	 * @throws EvalException
	 */
	public void addParam(Expression e) throws EvalException{
		function.addParam(e.evaluate());
	}
	
	/**
	 * Add a parameter to the function.
	 * @param d parameter
	 */
	public void addParam(double d){
		function.addParam(d);
	}
	
	/**
	 * Returns the value of the evaluated function.
	 */
	@Override
	public double evaluate() {
		return function.evaluate();
	}
	
	/**
	 * Gets the precedence level of this expression.
	 * @return precedence level
	 */
	public int getPrecedence(){
		return Expression.MAX_PREC;
	}

}
