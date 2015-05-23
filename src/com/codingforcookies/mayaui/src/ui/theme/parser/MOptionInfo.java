package com.codingforcookies.mayaui.src.ui.theme.parser;

import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MOptionInfo extends MOptionParser {
	public boolean shouldParse(String keyclass, String key, String value) {
		return keyclass.equals("info");
	}
	
	public MOptionRuntime[] getRuntime() { return null; }
	
	public MOptionParser parse(UITheme theme, String keyclass, String type, String value) {
		switch(type) {
			case "name":
				theme.name = value;
				break;
			case "creator":
				theme.creator = value;
				break;
			case "description":
				theme.description = value;
				break;
		}
		
		return null;
	}
	
	public <T> T getValue(T type) { return null; }
	
	public void run(MOptionRuntime runtime, float width, float height) { }
}