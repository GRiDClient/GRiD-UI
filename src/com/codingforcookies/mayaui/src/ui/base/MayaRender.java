package com.codingforcookies.mayaui.src.ui.base;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.theme.MAlign;

/**
 * Common abstract class for render components.
 * @author Stumblinbear
 */
public abstract class MayaRender extends MayaUpdate {
	public float x, y, width, height;
	
	public MAlign anchor = MAlign.BOTTOMLEFT;
	public MayaRender setAnchor(MAlign anchor) {
		this.anchor = anchor;
		return this;
	}
	
	protected MVBO mVBO;

	/**
	 * Initializes the render.
	 */
	public void startRender() {
		GL11.glPushMatrix();
		{
			render();
		}
		GL11.glPopMatrix();
	}
	
	/**
	 * Create the VBO.
	 */
	public void createVBO() {
		
	}
	
	/**
	 * Renders everything else.
	 */
	protected abstract void render();
	
	public void onWindowResized(int changewidth, int changeheight) {
		
	}
}