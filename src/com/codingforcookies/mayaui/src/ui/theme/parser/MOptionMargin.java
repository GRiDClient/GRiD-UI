package com.codingforcookies.mayaui.src.ui.theme.parser;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MOptionMargin extends MOptionParser {
	public float top, left;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return key.startsWith("margin");
	}
	
	public MOptionRuntime[] getRuntime() {
		return new MOptionRuntime[] { MOptionRuntime.PRERENDER };
	}
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		if(key != "margin") {
			String[] types = key.substring(key.indexOf("-") + 1).split("-");
			for(String str : types) {
				if(str.equals("top"))
					top = MayaUI.parseConfigFloat(value);
				else if(str.equals("left"))
					left = MayaUI.parseConfigFloat(value);
				else
					System.err.println("Unknown side " + str);
			}
		}else
			top = left = MayaUI.parseConfigFloat(value);
		
		return this;
	}
	
	public <T> T getValue(T type) { return null; }
	
	public void run(MOptionRuntime runtime, float width, float height) {
		GL11.glTranslatef(left, top, 0F);
		System.out.println("translated");
	}
}