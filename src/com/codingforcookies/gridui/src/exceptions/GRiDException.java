package com.codingforcookies.gridui.src.exceptions;

@SuppressWarnings("serial")
public class GRiDException extends Exception {
	public GRiDException(String error) {
		super(error);
	}
	
	public static void throwNonClosing(GRiDException exception) {
		try {
			throw exception;
		} catch(Exception e) { e.printStackTrace(); }
	}
}