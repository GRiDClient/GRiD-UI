package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.texture.MayaFontRenderer;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

/**
 * Maya UI Window. Same as Maya Panel, but includes a title bar.
 * @author Stumblinbear
 */
public class MWindow extends MWindowPanel {
	public float titleHeight = 0;
	
	/**
	 * 0 = not grabbed
	 * 1 = grabbed
	 * 2 = failed grab
	 */
	public int grabbed = 0;
	public float grabbedX, grabbedY;
	
	public MWindow(String title, float x, float y, float width, float height) {
		super(title, x, y, width, height);
	}
	
	public void update() {
		super.update();
		
		if(Mouse.isButtonDown(0)) {
			if(grabbed == 0) {
				grabbedX = Mouse.getX();
				grabbedY = Mouse.getY();
				
				if(grabbedX > x && grabbedX < x + width &&
						grabbedY > y && grabbedY < y + titleHeight) {
					grabbed = 1;
					grabbedX -= x;
					grabbedY -= y;
					
					MayaUI.getUIManager().bringWindow(this);
				}else
					grabbed = 2;
			}
			
			if(grabbed == 1) {
				x = Mouse.getX() - grabbedX;
				y = Mouse.getY() - grabbedY;
			}
		}else
			grabbed = 0;
	}
	
	public void render() {
		GL11.glPushMatrix();
		{
			UITheme theme = ThemeManager.getTheme();
			UIClass windowclass = theme.getClass("window");
			if(theme.hasClass("#" + title.toLowerCase().replace(" ", "_")))
				windowclass = theme.getClass("#" + title.toLowerCase().replace(" ", "_"));
			
			if(titleHeight == 0)
				titleHeight = windowclass.getClass(".title").get("height", new Integer(0), 25);
		    
		    /* DRAW WINDOW BODY */
			GL11.glTranslatef(x, y, 0F);
		    RenderHelper.renderWithTheme(windowclass, width, height);
		    
		    /* DRAW WINDOW TITLE */
			GL11.glTranslatef(0F, titleHeight, 0F);
		    RenderHelper.renderWithTheme(windowclass.getClass(".title"), width);
		    
		    MayaFontRenderer.draw(title, 6, -7, windowclass.getClass(".title").get("color", new MayaColor(), MayaColor.WHITE));
		    
		    super.drawComponents();
		}
		GL11.glPopMatrix();
	}
}