package com.codingforcookies.mayaui.src.ui;

/**
 * Common abstract class for update components.
 * @author Stumblinbear
 */
public abstract class MayaUpdate {
	public boolean scheduledForDrop = false;
	
	public abstract void update();
}