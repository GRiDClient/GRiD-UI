package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.theme.MBorder;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;

public class RenderHelper {
	/**
	 * Renders a square. But reads the theme file and appends all defined components such as borders.
	 */
	public static void renderWithTheme(UIClass uiclass, float width, float height, MayaColor bgColor) {
		bgColor.use();
		
		if(bgColor.getAlpha() != 1F) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		
		renderBox(0, 0, width, height);
		
		if(uiclass.has("border"))
			uiclass.get("border", new MBorder()).render(width, height);
		
		if(bgColor.getAlpha() != 1F)
			GL11.glDisable(GL11.GL_BLEND);
	}
	
	/**
	 * Various renderWithTheme functions.
	 */
	public static void renderWithTheme(UIClass uiclass, float width, float height) {
		renderWithTheme(uiclass, width, height, uiclass.get("background-color", new MayaColor(), MayaColor.GLOBAL_BACKGROUND));
	}

	/**
	 * Various renderWithTheme functions.
	 */
	public static void renderWithTheme(UIClass uiclass, float width) {
		renderWithTheme(uiclass, width, uiclass.get("height", new Integer(0), 0).floatValue());
	}
	
	/**
	 * Render a box. WITH COLOR!
	 */
	public static void renderBox(float x, float y, float width, float height, MayaColor color) {
		color.use();
		renderBox(x, y, width, height);
	}
	
	/**
	 * Render a box.
	 */
	public static void renderBox(float x, float y, float width, float height) {
		GL11.glBegin(GL11.GL_QUADS);
	    {
	        GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y - height);
			GL11.glVertex2f(x, y - height);
	    }
	    GL11.glEnd();
	}
}