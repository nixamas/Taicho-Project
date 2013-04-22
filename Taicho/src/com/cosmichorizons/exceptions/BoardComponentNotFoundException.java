package com.cosmichorizons.exceptions;

/**
 * Exception that is thrown when a BC is not found. 
 * Can be ignored, best if used with try/catch
 * @author Ryan
 *
 */
public class BoardComponentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage(){
		return "BOARD COMPONENT NOT FOUND EXCEPTION  ::> ";
	}//getMessage()

}//BoardComponentNotFoundException
