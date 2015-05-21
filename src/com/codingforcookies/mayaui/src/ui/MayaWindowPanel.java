package com.codingforcookies.mayaui.src.ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;
import com.codingforcookies.mayaui.src.ui.theme.components.UIComponent;

public class MayaWindowPanel extends MayaUpdate implements MayaRender {
	protected UIManager uimanager;
	public float x, y, width, height;
	
	private List<UIComponent> components;
	
	public MayaWindowPanel(UIManager uimanager, float x, float y, float width, float height) {
		this.uimanager = uimanager;
		this.x = x;
		this.y = y;
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
		UITheme theme = ThemeManager.getTheme();
	    
	    /* DRAW WINDOW BODY */
	    RenderHelper.renderWithTheme(theme, "panel", x, y, width, height);
	    
	    drawComponents(0F);
	}
	
	public void addComponent(UIComponent component) {
		components.add(component);
	}

	public void drawComponents(float yoffset) {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0F, yoffset, 0F);
			for(UIComponent component : components)
				component.render();
		}
		GL11.glPopMatrix();
	}
}