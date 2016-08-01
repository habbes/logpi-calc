package com.manuscript.logpicalc.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Takes an input string and breaks it down into tokens.
 * @author Habbes
 *
 */
public class Lexer {
	
	/**
	 * Breaks an input string into tokens.
	 * @param source input string
	 * @param exprs list of token expressions
	 * @return list of tokens
	 * @throws BadSyntaxException
	 */
	public static ArrayList<Token> lex(String source, List<TokenExpression> exprs) throws BadSyntaxException{
		ArrayList<Token> tokens = new ArrayList<Token>();
		int pos = 0;
		
		Pattern re;
		Matcher matcher = null;
		while(pos < source.length()){
			boolean found = false;
			for(TokenExpression expr : exprs){
				//test all patterns for the one that matches the next sequence in the string
				re = Pattern.compile(expr.getPattern());
				matcher = re.matcher(source);
				if(matcher.find(pos)){
					if(matcher.start() != pos){
						continue;
					}
					found = true;
					if(expr.getTag() != TokenExpression.NONE){
						String text = matcher.group();
						tokens.add(new Token(text, expr));
					}
					break;
				}
			}
			if(!found){
				throw new BadSyntaxException("Syntax error around character '" + 
						source.charAt(pos) + "' at position " + (pos + 1));
			}
			else {
				pos = matcher.end();
			}
		}
		
		return tokens;
	}
}
