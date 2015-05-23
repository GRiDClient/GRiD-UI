package com.codingforcookies.mayaui.src.ui.theme.parser;

import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MOptionColor extends MOptionParser {
	public MayaColor color;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return key.equals("color");
	}
	
	public MOptionRuntime[] getRuntime() {
		return new MOptionRuntime[] { MOptionRuntime.PRETEXT, MOptionRuntime.POSTTEXT };
	}
	
	public MOptionColor getDefault() {
		color = MayaColor.FALLBACK_COLOR.clone();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(T type) {
		return (T)color;
	}
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		color = new MayaColor(value);
		return this;
	}
	
	public void run(MOptionRuntime runtime, float width, float height) {
		switch(runtime) {
			case PRETEXT:
				color.use();
				break;
			case POSTTEXT:
				break;
			default:
				break;
		}
	}
}