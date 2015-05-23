package com.codingforcookies.mayaui.src.ui.theme.parser;

import com.codingforcookies.mayaui.src.ui.theme.ThemeManager;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public abstract class MOptionParser implements Cloneable {
	public MOptionParser() { ThemeManager.optionParsers.add(this); }
	
	public abstract boolean shouldParse(String keyclass, String key, String value);
	public abstract MOptionRuntime[] getRuntime();
	
	public abstract MOptionParser parse(UITheme theme, String keyclass, String key, String value);
	public abstract <T> T getValue(T type);
	public abstract void run(MOptionRuntime runtime, float width, float height);
	
	public MOptionParser clone() {
		try {
			return (MOptionParser)super.clone();
		} catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}