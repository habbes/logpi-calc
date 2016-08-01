package com.manuscript.logpicalc.lib;

import java.util.ArrayList;

/**
 * Callback invoked when evaluating a function.
 * @author Habbes
 *
 */
public interface FunctionEvaluator {
	/**
	 * Returns the value of the function.
	 * @param params parameters of the function
	 * @return value of the function
	 */
	public abstract double evaluate(ArrayList<Double> params);
}
