package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.input.Mouse;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.texture.MayaFontRenderer;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MayaWindow extends MayaWindowPanel {
	public String title = "";
	public float titleHeight = 0;
	
	/**
	 * 0 = not grabbed
	 * 1 = grabbed
	 * 2 = failed grab
	 */
	public int grabbed = 0;
	public float grabbedX, grabbedY;
	
	public MayaWindow(UIManager uimanager, String title, float x, float y, float width, float height) {
		super(uimanager, x, y, width, height);
		
		this.title = title;
	}
	
	public void update() {
		super.update();
		
		if(Mouse.isButtonDown(0)) {
			if(grabbed == 0) {
				grabbedX = Mouse.getX();
				grabbedY = MayaUI.SCREEN_HEIGHT - Mouse.getY();
				
				if(grabbedX > x && grabbedX < x + width &&
						grabbedY > y && grabbedY < y + titleHeight) {
					grabbed = 1;
					grabbedX -= x;
					grabbedY -= y;
					
					uimanager.bringWindow(this);
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
	    
	    MayaFontRenderer.draw(title, x + 4, MayaUI.SCREEN_HEIGHT - y - titleHeight / 2 + 5, theme.get("window .title").get("color", new MayaColor(), MayaColor.WHITE));
	    
	    super.drawComponents(titleHeight);
	}
}