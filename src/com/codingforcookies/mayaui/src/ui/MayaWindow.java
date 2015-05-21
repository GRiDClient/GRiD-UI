package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.input.Mouse;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MayaWindow extends MayaUpdate implements MayaRender {
	public String title = "";
	public float titleHeight = 0;
	public float x, y, width, height;
	
	/**
	 * 0 = not grabbed
	 * 1 = grabbed
	 * 2 = failed grab
	 */
	public int grabbed = 0;
	public float grabbedX, grabbedY;
	
	public MayaWindow(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		if(Mouse.isButtonDown(0)) {
			if(grabbed == 0) {
				grabbedX = Mouse.getX();
				grabbedY = MayaUI.SCREEN_HEIGHT - Mouse.getY();
				
				if(grabbedX > x && grabbedX < x + width &&
						grabbedY > y && grabbedY < y + titleHeight) {
					grabbed = 1;
					grabbedX -= x;
					grabbedY -= y;
				}else
					grabbed = 2;
			}
			
			if(grabbed == 1) {
				x = Mouse.getX() - grabbedX;
				y = MayaUI.SCREEN_HEIGHT - Mouse.getY() - grabbedY;
			}
		}else
			grabbed = 0;
	}
	
	public void render() {
		UITheme theme = ThemeManager.getTheme();
		if(titleHeight == 0)
			titleHeight = theme.get("window .title").get("height", new Integer(0), 25);
	    
	    /* DRAW WINDOW BODY */
	    RenderHelper.renderWithTheme(theme, "window", x, y + titleHeight, width, height);
	    
	    /* DRAW WINDOW TITLE */
	    RenderHelper.renderWithTheme(theme, "window .title", x, y, width);
	}
}