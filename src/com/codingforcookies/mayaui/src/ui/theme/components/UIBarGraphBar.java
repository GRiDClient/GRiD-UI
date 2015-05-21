package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.theme.MayaColor;

public class UIBarGraphBar {
	public String name;
	public float value = 0;
	float percent = 0F;
	MayaColor color;
	
	public UIBarGraphBar(String name, MayaColor color) {
		this.name = name;
		this.color = color;
	}
}