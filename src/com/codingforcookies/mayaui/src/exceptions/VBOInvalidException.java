package com.codingforcookies.mayaui.src.exceptions;

/**
 * Throws when there is an error in the loaded theme file.
 * @author Stumblinbear
 */
@SuppressWarnings("serial")
public class VBOInvalidException extends MayaException {
	public VBOInvalidException(String error) {
		super(error);
	}
}