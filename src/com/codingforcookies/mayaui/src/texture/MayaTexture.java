package com.codingforcookies.mayaui.src.texture;

import org.lwjgl.opengl.GL11;

public class MayaTexture {
	private int texture = 0;
	
	public MayaTexture(int textureid) {
		texture = textureid;
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}
}