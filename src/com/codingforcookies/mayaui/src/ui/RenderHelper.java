package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaclientapi.src.font.MayaFontRenderer;
import com.codingforcookies.mayaui.src.ui.base.MVBO;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionRuntime;

public class RenderHelper {
	public static void renderWithTheme(UIClass uiclass, float width) {
		renderWithTheme(uiclass, width, uiclass.get("height") == null ? 0F : uiclass.get("height").getValue(new Float(0)));
	}
	
	public static void renderWithTheme(UIClass uiclass, float width, float height) {
		renderWithTheme(uiclass, width, height, null, null);
	}
	
	public static void renderWithTheme(UIClass uiclass, MVBO mVBO) {
		renderWithTheme(uiclass, uiclass.get("height") == null ? 0F : uiclass.get("height").getValue(new Float(0)), mVBO);
	}
	
	public static void renderWithTheme(UIClass uiclass, float width, MVBO mVBO) {
		renderWithTheme(uiclass, width, uiclass.get("height") == null ? 0F : uiclass.get("height").getValue(new Float(0)), mVBO);
	}
	
	public static void renderWithTheme(UIClass uiclass, float width, float height, MVBO mVBO) {
		renderWithTheme(uiclass, width, height, null, mVBO);
	}
	
	public static void renderWithTheme(UIClass uiclass, float width, float height, MayaColor color) {
		renderWithTheme(uiclass, width, height, color, null);
	}
	
	/**
	 * Renders a square. But reads the theme file and appends all defined components such as borders. Includes a color override.<br><br>
	 * Warning: Does not run GL11.glPushMatrix() or GL11.glPopMatrix()
	 */
	public static void renderWithTheme(UIClass uiclass, float width, float height, MayaColor color, MVBO mVBO) {
		uiclass.run(MOptionRuntime.PRERENDER, width, height);
		
		if(color != null)
			color.use();
		
		if(mVBO != null)
			mVBO.render();
		else
			renderBox(0, 0, width, height);
		
		uiclass.run(MOptionRuntime.POSTRENDER, width, height);
	}

	/**
	 * Various renderWithTheme functions.
	 */
	public static void renderWithTheme(UIClass uiclass) {
		renderWithTheme(uiclass, uiclass.get("width").getValue(new Float(0)), uiclass.get("height").getValue(new Float(0)));
	}

	public static void renderBox(float x, float y, float width, float height, MayaColor color) {
		color.use();
		renderBox(x, y, width, height);
	}

	/**
	 * Render a box.
	 */
	public static void renderBox(float x, float y, float width, float height) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x, y - height);
			GL11.glVertex2f(x + width, y - height);
			
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y - height);
		}
		GL11.glEnd();
	}

	public static void drawString(UIClass uiclass, String string, float x, float y) {
		boolean hastextclass = uiclass.hasClass("text");
		float width = MayaFontRenderer.getStringWidth(string);
		
		uiclass.run(MOptionRuntime.PRETEXT, width, MayaFontRenderer.CHAR_WIDTH);

		if(hastextclass) {
			uiclass.getClass("text").runSingle(MOptionRuntime.PRERENDER, width, MayaFontRenderer.CHAR_WIDTH, null);
			uiclass.getClass("text").run(MOptionRuntime.PRETEXT, width, MayaFontRenderer.CHAR_WIDTH);
		}
		
		MayaFontRenderer.draw(string, x, y);
		
		if(hastextclass) {
			uiclass.getClass("text").run(MOptionRuntime.POSTTEXT, width, MayaFontRenderer.CHAR_WIDTH);
			uiclass.getClass("text").runSingle(MOptionRuntime.POSTRENDER, width, MayaFontRenderer.CHAR_WIDTH, null);
		}

		uiclass.run(MOptionRuntime.POSTTEXT, width, MayaFontRenderer.CHAR_WIDTH);
	}
}