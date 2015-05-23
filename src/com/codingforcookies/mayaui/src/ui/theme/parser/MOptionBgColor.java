package com.codingforcookies.mayaui.src.ui.theme.parser;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;

public class MOptionBgColor extends MOptionParser {
	public MayaColor color;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return key.equals("background-color") || key.equals("opacity");
	}
	
	public MOptionRuntime[] getRuntime() {
		return new MOptionRuntime[] { MOptionRuntime.PRERENDER, MOptionRuntime.POSTRENDER };
	}
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		MOptionBgColor precolor = (MOptionBgColor)theme.getClass(keyclass).get("background-color");
		if(key.equals("opacity")) {
			if(precolor != null)
				precolor.getValue(new MayaColor()).setAlpha(MayaUI.parseConfigFloat(value));
			return null;
		}
		
		if(precolor != null) {
			precolor.getValue(new MayaColor()).setColor(value);
			return null;
		}
		
		color = new MayaColor(value);
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(T type) {
		return (T)color;
	}
	
	public void run(MOptionRuntime runtime, float width, float height) {
		switch(runtime) {
			case PRETEXT:
				color.use();
				break;
			case PRERENDER:
				color.use();
				
				if(color.getAlpha() != 1F) {
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				}
				break;
			case POSTTEXT:
				break;
			case POSTRENDER:
				if(color.getAlpha() != 1F)
					GL11.glDisable(GL11.GL_BLEND);
				break;
			default:
				break;
		}
	}
}