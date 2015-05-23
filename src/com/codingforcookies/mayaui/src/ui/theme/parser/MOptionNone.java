package com.codingforcookies.mayaui.src.ui.theme.parser;

import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MOptionNone extends MOptionParser {
	private Object value;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return true;
	}
	
	public MOptionRuntime[] getRuntime() { return null; }
	
	public MOptionParser getDefault() {
		value = null;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(T type) { return (T)value; }
	
	public MOptionParser parse(UITheme theme, String keyclass, String type, String value) {
		System.out.println("Unhandled option: " + type);
		this.value = value;
		return this;
	}
	
	public void run(MOptionRuntime runtime, float width, float height) { }
}