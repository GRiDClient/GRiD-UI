package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;

public class UIBox extends UIComponent {
	private MayaColor color;
	
	public UIBox(MayaColor color) {
		super("box");
		
		this.color = color;
	}
	
	public void update() { }
	
	public void render() {
		RenderHelper.renderBox(x, y, width, height, color);
	}
	
	public UIBox setBounds(float x, float y, float width, float height) {
		return (UIBox)super.setBounds(x, y, width, height);
	}
	
	public UIBox setColor(MayaColor color) {
		this.color = color;
		return this;
	}
}