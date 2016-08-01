package com.manuscript.logpicalc.lib;

import java.util.ArrayList;

/**
 * Represents a function.
 * @author Habbes
 *
 */
public class Function {
	public static final int INFINITE_PARAMS = -1;
	private int maxParams = INFINITE_PARAMS;
	private ArrayList<Double> params = new ArrayList<Double>();
	private String name = "";
	private FunctionEvaluator evaluator;
	
	
	/**
	 * Constructor with maximum parameters.
	 * @param n name of function
	 * @param params maximum number of parameters
	 * @param eval callback to invoke when evaluating the represented function
	 */
	public Function(String n, int params, FunctionEvaluator eval){
		name = n;
		evaluator = eval;
		maxParams = params;
	}
	
	/**
	 * Alias for Function(n, INFINITE_PARAMS, eval).
	 * @param n name of function
	 * @param eval callback to invoke when evaluating the represented function
	 */
	public Function(String n, FunctionEvaluator eval){
		this(n, INFINITE_PARAMS, eval);
	}
	
	/**
	 *  Gets the name of the function.
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the text to input when the function is clicked.
	 * This is the name of the function with an opening paranthesis appended
	 * when the function accepts parameters.
	 * @return text
	 */
	public String getText(){
		return (maxParams == 0)? name : name + "(";
	}
	
	public String toString(){
		return getText();
	}
	
	/**
	 * Add a parameter to the function.
	 * @param param parameter to add
	 */
	public void addParam(double param){
		params.add(param);
	}
	
	/**
	 * Returns the value of the function.
	 * @return value of the function
	 */
	public double evaluate(){
		return evaluator.evaluate(params);
	}
	
}
