package com.codingforcookies.mayaui.src.ui.theme.parser;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MOptionFloat extends MOptionParser {
	public Float size;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return key.equals("width") || key.equals("height");
	}
	
	public MOptionRuntime[] getRuntime() { return null; }
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		size = MayaUI.parseConfigFloat(value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(T type) {
		return (T)size;
	}
	
	public void run(MOptionRuntime runtime, float width, float height) { }
}