package com.manuscript.logpicalc.lib;

public class PlusParser extends OperatorParser{

	public PlusParser(){
		super("plus", new AddExpression());
	}

}
