package com.codingforcookies.mayaui.src.exceptions;

/**
 * Throws when there is an error in the loaded theme file.
 * @author Stumblinbear
 */
@SuppressWarnings("serial")
public class ThemeInvalidException extends MayaException {
	public ThemeInvalidException(String error) {
		super(error);
	}
}