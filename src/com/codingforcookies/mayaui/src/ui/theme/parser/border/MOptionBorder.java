package com.codingforcookies.mayaui.src.ui.theme.parser.border;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UITheme;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionParser;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionRuntime;

/**
 * The Maya border class. Handles parsing, storage, and rendering of borders.
 * @author Stumblinbear
 */
public class MOptionBorder extends MOptionParser {
	public MBorderOptions top, right, bottom, left;
	
	public boolean shouldParse(String keyclass, String key, String value) {
		return key.startsWith("border");
	}
	
	public MOptionRuntime[] getRuntime() {
		return new MOptionRuntime[] { MOptionRuntime.POSTRENDER };
	}
	
	public <T> T getValue(T type) { return null; }
	
	public MOptionParser parse(UITheme theme, String keyclass, String key, String value) {
		if(!key.equals("border")) {
			String[] types = key.substring(key.indexOf("-") + 1).split("-");
			for(String str : types) {
				if(str.equals("top"))
					top = createBorder("top", value);
				else if(str.equals("right"))
					right = createBorder("right", value);
				else if(str.equals("bottom"))
					bottom = createBorder("bottom", value);
				else if(str.equals("left"))
					left = createBorder("left", value);
				else
					System.err.println("Unknown border type " + str);
			}
		}else
			top = right = bottom = left = createBorder("all", value);
		
		return this;
	}
	
	/**
	 * Create the new border option.
	 */
	private MBorderOptions createBorder(String side, String type) {
		String[] options = type.split(" ");
		MBorderLocation borderLocation = MBorderLocation.valueOf(options[0].toUpperCase());
		float size = MayaUI.parseConfigFloat(options[1]);
		MBorderType borderType = MBorderType.valueOf(options[2].toUpperCase());
		MayaColor color = new MayaColor(options[3]);
		
		return new MBorderOptions(side, borderLocation, size, borderType, color);
	}
	
	/**
	 * Render the border.
	 * TODO: Add outer rendering.
	 */
	public void run(MOptionRuntime runtime, float width, float height) {
		boolean istop = top != null;
		boolean isbottom = bottom != null;
		if(istop)
			RenderHelper.renderBox(0, 0, width, top.size, top.color);
		if(isbottom)
			RenderHelper.renderBox(0, bottom.size - height, width, bottom.size, bottom.color);
		if(right != null)
			RenderHelper.renderBox(width - right.size, (istop ? -top.size : 0), right.size, height - (istop ? top.size : 0) - (isbottom ? bottom.size : 0), right.color);
		if(left != null)
			RenderHelper.renderBox(0, (istop ? -top.size : 0), left.size, height - (istop ? top.size : 0) - (isbottom ? bottom.size : 0), left.color);
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
		String ret = "";
		if(top != null)
			ret += top.toString();
		if(right != null)
			ret += right.toString();
		if(bottom != null)
			ret += bottom.toString();
		if(left != null)
			ret += left.toString();
		return ret;
	}
}

/**
 * Holds side render information.
 * @author Stumblinbear
 */
class MBorderOptions {
	public String side = "";
	public MBorderLocation borderLocation;
	public float size;
	public MBorderType borderType;
	public MayaColor color;
	
	public MBorderOptions(String side, MBorderLocation borderLocation, float size, MBorderType borderType, MayaColor color) {
		this.side = side;
		this.borderLocation = borderLocation;
		this.size = size;
		this.borderType = borderType;
		this.color = color;
	}
	
	public String toString() {
		return "border-" + side + "(" + borderLocation + "; " + size + "; " + borderType + "; " + color.toString() + ")";
	}
}