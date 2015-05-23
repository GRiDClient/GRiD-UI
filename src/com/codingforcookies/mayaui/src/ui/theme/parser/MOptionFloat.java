package com.codingforcookies.mayaui.src.ui.theme.parser;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MOptionFloat extends MOptionParser {
	public Float value;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return key.equals("width") || key.equals("height");
	}
	
	public MOptionRuntime[] getRuntime() { return null; }
	
	public MOptionFloat getDefault() {
		value = 0F;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(T type) {
		return (T)value;
	}
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		this.value = MayaUI.parseConfigFloat(value);
		return this;
	}
	
	public void run(MOptionRuntime runtime, float width, float height) { }
}