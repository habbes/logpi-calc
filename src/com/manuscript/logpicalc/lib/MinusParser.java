package com.manuscript.logpicalc.lib;

public class MinusParser extends OperatorParser {

	public MinusParser(){
		super("minus", new SubtractExpression());
	}

}
