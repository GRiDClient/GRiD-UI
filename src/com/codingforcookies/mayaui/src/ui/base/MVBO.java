package com.codingforcookies.mayaui.src.ui.base;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.exceptions.MayaException;
import com.codingforcookies.mayaui.src.exceptions.VBOInvalidException;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;

public class MVBO {
	private VertexBufferObject vbo;

	public void render() {
		if(vbo == null) {
			MayaException.throwNonClosing(new VBOInvalidException("VBO not generated!"));
			return;
		}

		vbo.draw();
	}

	public MVBO create(float width, float height, MayaColor color) {
		vbo = new VertexBufferObject(GL11.GL_QUADS, false, true, false, false, 4);

		vbo.createBuffer();

		vbo.addVertex(0, 0, 0);
		vbo.addColor(color);
		vbo.addVertex(width, 0, 0);
		vbo.addColor(color);
		vbo.addVertex(width, -height, 0);
		vbo.addColor(color);
		vbo.addVertex(0, -height, 0);
		vbo.addColor(color);

		vbo.createVBO();

		return this;
	}
	
	public void destroy() {
		vbo.destroy();
	}
}