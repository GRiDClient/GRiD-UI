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
	
	public MOptionBgColor getDefault() {
		color = MayaColor.FALLBACK_COLOR.clone();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(T type) {
		return (T)color;
	}
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		boolean hasBgColor = theme.getClass(keyclass).has("background-color");
		MOptionBgColor parentColor = (MOptionBgColor)theme.getClass(keyclass).get("background-color");
		
		if(key.equals("opacity")) {
			if(hasBgColor) {
				parentColor.color.setAlpha(MayaUI.parseConfigFloat(value));
				return null;
			}else if(parentColor == null) {
				parentColor = this;
				color = new MayaColor();
			}
			
			color = parentColor.color.clone();
			color.setAlpha(MayaUI.parseConfigFloat(value));
			return this;
		}else{
			if(hasBgColor) {
				parentColor.color.setColor(value);
				return null;
			}else{
				if(parentColor != null)
					color = parentColor.getValue(new MayaColor()).clone();
				else
					color = new MayaColor();
				
				color.setColor(value);
			}
			
			return this;
		}
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