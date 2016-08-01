package com.manuscript.logpicalc.lib;


public class TimesParser extends OperatorParser {

	public TimesParser(){
		super("times", new MultExpression());
	}

}
