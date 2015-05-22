package com.codingforcookies.mayaui.src.ui;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

/**
 * Maya UI Panel. A square box with no title bar.
 * @author Stumblinbear
 */
public class MWindowPanel extends MWindowBase {
	/**
	 * The title of the window.
	 */
	public String title = "";
	
	public MWindowPanel(String title, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.title = title;
	}
	
	public void render() {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(x, y, 0F);
			UITheme theme = ThemeManager.getTheme();
			UIClass panelclass = theme.getClass("panel");
			if(panelclass.hasClass("#" + title.toLowerCase().replace(" ", "_")))
				panelclass = panelclass.getClass("#" + title.toLowerCase().replace(" ", "_"));
			
		    /* DRAW WINDOW BODY */
		    RenderHelper.renderWithTheme(panelclass, width, height);
		    
			drawComponents();
		}
		GL11.glPopMatrix();
	}
}