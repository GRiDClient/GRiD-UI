package com.codingforcookies.mayaui.src.ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;
import com.codingforcookies.mayaui.src.ui.theme.components.UIComponent;

public class MWindowPanel extends MayaUpdate implements MayaRender {
	protected UIManager uimanager;
	
	public String title = "";
	public float x, y, width, height;
	
	private List<UIComponent> components;
	
	public MWindowPanel(UIManager uimanager, String title, float x, float y, float width, float height) {
		this.uimanager = uimanager;
		
		this.title = title;
		this.x = x;
		this.y = MayaUI.SCREEN_HEIGHT - y;
		this.width = width;
		this.height = height;
		
		components = new ArrayList<UIComponent>();
		
		init();
	}
	
	public void init() {
		
	}
	
	public void update() {
		for(UIComponent component : components)
			component.update();
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
		    RenderHelper.renderWithTheme(theme, panelclass, width, height);
		    
			drawComponents();
		}
		GL11.glPopMatrix();
	}
	
	public void addComponent(UIComponent component) {
		components.add(component);
	}

	public void drawComponents() {
		for(UIComponent component : components)
			component.render();
	}
}