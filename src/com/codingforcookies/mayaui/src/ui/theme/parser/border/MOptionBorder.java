package com.codingforcookies.mayaui.src.ui.theme.parser.border;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionParser;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionRuntime;

/**
 * The Maya border class. Handles parsing, storage, and rendering of borders.
 * @author Stumblinbear
 */
public class MOptionBorder extends MOptionParser {
	public String side = "";
	public MBorderLocation borderLocation = MBorderLocation.INNER;
	public float size = 0F;
	public MBorderType borderType = MBorderType.SOLID;
	public MayaColor color = MayaColor.FALLBACK_COLOR;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return key.startsWith("border");
	}
	
	public MOptionRuntime[] getRuntime() {
		return new MOptionRuntime[] { MOptionRuntime.POSTRENDER };
	}
	
	public boolean shouldCopyToChildren() { return true; }
	
	public MOptionParser getDefault() { return this; }
	
	public <T> T getValue(T type) { return null; }
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		UIClass uiclass = theme.getClass(keyclass);
		if(key.equals("border")) {
			uiclass.set("border-top", new MOptionBorder().parse(theme, keyclass, "border-top", value));
			uiclass.set("border-right", new MOptionBorder().parse(theme, keyclass, "border-right", value));
			uiclass.set("border-bottom", new MOptionBorder().parse(theme, keyclass, "border-bottom", value));
			uiclass.set("border-left", new MOptionBorder().parse(theme, keyclass, "border-left", value));
			return null;
		}else{
			boolean hasBorder = theme.getClass(keyclass).has(key);
			MOptionBorder preborder;
			
			if(!hasBorder)
				preborder = this;
			else
				preborder = (MOptionBorder)theme.getClass(keyclass).get(key);
			
			return process(preborder, key, value);
		}
	}
	
	/**
	 * Create the new border option.
	 */
	private MOptionBorder process(MOptionBorder border, String side, String type) {
		if(type.equals("none") || type.equals("0") || border == null)
			return null;
		
		border.side = side.split("-")[1];
		
		String[] options = type.split(" ");
		border.borderLocation = MBorderLocation.valueOf(options[0].toUpperCase());
		border.size = MayaUI.parseConfigFloat(options[1]);
		border.borderType = MBorderType.valueOf(options[2].toUpperCase());
		border.color = new MayaColor(options[3]);
		
		return border;
	}
	
	/**
	 * Render the border.
	 * TODO: Add outer rendering.
	 */
	public void run(MOptionRuntime runtime, float width, float height) {
		switch(borderLocation) {
			case INNER:
				switch(side) {
					case "top":
						RenderHelper.renderBox(0, 0, width, size, color);
						break;
					case "bottom":
						RenderHelper.renderBox(0, size - height, width, size, color);
						break;
					case "right":
						RenderHelper.renderBox(width - size, 0, size, height, color);
						break;
					case "left":
						RenderHelper.renderBox(0, 0, size, height, color);
						break;
				}
				break;
			case OUTER:
				switch(side) {
					case "top":
						RenderHelper.renderBox(0, size, width, size, color);
						break;
					case "bottom":
						RenderHelper.renderBox(0, - height, width, size, color);
						break;
					case "right":
						RenderHelper.renderBox(width, 0, size, height, color);
						break;
					case "left":
						RenderHelper.renderBox(-size, 0, size, height, color);
						break;
				}
				break;
		}
		/*
			case OUTER:
				if(top)
					RenderHelper.renderBox((left ? -size : 0), size, width + (left ? size : 0) + (right ? size : 0), size);
				if(right)
					RenderHelper.renderBox(width, 0, size, height);
				if(bottom)
					RenderHelper.renderBox((left ? -size : 0), -height, width + (left ? size : 0) + (right ? size : 0), size);
				if(left)
					RenderHelper.renderBox(-size, 0, size, height);
				break;
		}*/
	}
	
	public String toString() {
		return "border-" + side + "(" + borderLocation + "; " + size + "; " + borderType + "; " + color.toString() + ")";
	}
}