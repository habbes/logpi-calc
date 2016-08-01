package com.manuscript.logpicalc.lib;

/**
 * Exception due to error occuring during evaluation of an expression.
 * @author Habbes
 *
 */
public class EvalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6876094701513059484L;

	public EvalException(String msg){
		super(msg);
	}
}
