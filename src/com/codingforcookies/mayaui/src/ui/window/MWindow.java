package com.codingforcookies.mayaui.src.ui.window;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;

/**
 * Maya UI Window. Same as Maya Panel, but includes a title bar.
 * @author Stumblinbear
 */
public class MWindow extends MWindowPanel {
	public UIClass uititleclass;
	
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
	
	public void init() {
		super.init();
		
		uititleclass = uiclass.getClass(".title");
		
		if(uititleclass.has("height"))
			titleHeight = uititleclass.get("height").getValue(new Float(0F));
	}
	
	public void update(float delta) {
		super.update(delta);
		
		if(Mouse.isButtonDown(0)) {
			if(grabbed == 0) {
				grabbedX = Mouse.getX();
				grabbedY = Mouse.getY();
				
				if(grabbedX > x && grabbedX < x + width &&
						grabbedY > y - titleHeight && grabbedY < y) {
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
		/* DRAW WINDOW BODY */
		GL11.glTranslatef(x, y - titleHeight, 0F);
		RenderHelper.renderWithTheme(uiclass, width, height);
		
		/* DRAW WINDOW TITLE */
		GL11.glTranslatef(0F, titleHeight, 0F);
		RenderHelper.renderWithTheme(uititleclass, width);
		
		RenderHelper.drawString(uititleclass, title, 0, 0);

		GL11.glTranslatef(-7F, titleHeight - 5F, 0F);
		drawComponents();
	}
}