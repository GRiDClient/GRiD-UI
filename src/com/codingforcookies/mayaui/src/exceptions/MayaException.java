package com.codingforcookies.mayaui.src.exceptions;

/**
 * Maya UI's generated exceptions
 * @author Stumblinbear
 */
@SuppressWarnings("serial")
public class MayaException extends Exception {
	public MayaException(String error) {
		super(error);
	}
	
	public static void throwNonClosing(MayaException exception) {
		try {
			throw exception;
		} catch(Exception e) { e.printStackTrace(); }
	}
}