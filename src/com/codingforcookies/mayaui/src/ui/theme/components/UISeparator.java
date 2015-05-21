package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;

public class UISeparator extends UIComponent {
	private MayaColor color;
	
	public UISeparator() {
		super("separator");
		
		color = ThemeManager.getTheme().getClass("separator").get("line-color", new MayaColor(), MayaColor.BLACK);
	}
	
	public void update() { }
	
	public void render() {
		if(width >= height)
			RenderHelper.renderBox(x, y, width, 2, color);
		else
			RenderHelper.renderBox(x, y, 2, height, color);
	}
	
	public UISeparator setBounds(float x, float y, float width, float height) {
		return (UISeparator)super.setBounds(x, y, width, height);
	}
	
	public UISeparator setColor(MayaColor color) {
		this.color = color;
		return this;
	}
}