package com.codingforcookies.mayaui.src.ui.window;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.base.MVBO;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

/**
 * Maya UI Panel. A square box with no title bar.
 * @author Stumblinbear
 */
public class MWindowPanel extends MWindowBase {
	public MWindowPanel(String title, float x, float y, float width, float height) {
		super(title, x, y, width, height);
	}
	
	public void init() {
		UITheme theme = ThemeManager.getTheme();
		uiclass = theme.getClass(this instanceof MWindow ? "window" : "panel");
		if(uiclass.hasClass("#" + title.toLowerCase().replace(" ", "_")))
			uiclass = uiclass.getClass("#" + title.toLowerCase().replace(" ", "_"));
		
		super.init();
		
		createVBO();
	}
	
	public void createVBO() {
		MayaColor color;
		if(uiclass.has("background-color"))
			color = uiclass.get("background-color").getValue(new MayaColor());
		else
			color = MayaColor.FALLBACK_COLOR;
		
		mVBO = new MVBO().create(width, height, color);
	}
	
	public void render() {
		GL11.glTranslatef(x, y, 0F);
		
		/* DRAW WINDOW BODY */
		RenderHelper.renderWithTheme(uiclass, width, height);

		drawComponents();
	}
}