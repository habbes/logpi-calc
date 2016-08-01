package com.manuscript.logpicalc.lib;

/**
 * Result info after parsing from a list of tokens.
 * @author Habbes
 *
 */
public class ParserResult {
	private ParserResultValue value;
	private int pos;
	boolean sizeChanged = false;
	int newStop = 0;
	
	/**
	 * Constructor.
	 * @param v info about the parsed token
	 * @param p position of the next token to parse
	 */
	public ParserResult(ParserResultValue v, int p){
		value = v;
		pos = p;
	}
	
	/**
	 * Gets the value containing more info about the parsed token.
	 */
	public ParserResultValue getValue(){
		return value;
	}
	
	/**
	 * Gets the position of the next token to parse.
	 * @return
	 */
	public int getPos(){
		return pos;
	}
	
	/**
	 * Sets whether the size of tokens list has changed.
	 * @param v
	 */
	public void setSizeChanged(boolean v){
		sizeChanged = v;
	}
	
	/**
	 * Checks whether the size of the tokens list has changed.
	 * @return
	 */
	public boolean hasSizeChanged(){
		return sizeChanged;
	}
	
	/**
	 * Sets the new stop position of tokens list.
	 * @param s
	 */
	public void setNewStop(int s){
		newStop = s;
	}
	
	/**
	 * Gets the new stop position of the tokens list.
	 * @return
	 */
	public int getNewStop(){
		return newStop;
	}
}
