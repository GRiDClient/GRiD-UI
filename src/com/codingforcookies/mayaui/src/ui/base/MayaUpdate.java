package com.codingforcookies.mayaui.src.ui.base;

/**
 * Common abstract class for update components.
 * @author Stumblinbear
 */
public abstract class MayaUpdate {
	public boolean scheduledForDrop = false;
	
	public abstract void update(float delta);
}