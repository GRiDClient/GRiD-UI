package com.codingforcookies.mayaui.src.texture;

import org.lwjgl.opengl.GL11;

public class MTexture {
	private int texture = 0;
	
	public MTexture(int textureid) {
		texture = textureid;
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}
}