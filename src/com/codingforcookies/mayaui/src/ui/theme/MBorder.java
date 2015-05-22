package com.codingforcookies.mayaui.src.ui.theme;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;

/**
 * The Maya border class. Handles parsing, storage, and rendering of borders.
 * @author Stumblinbear
 */
public class MBorder implements Cloneable {
	public MBorderOptions top, right, bottom, left;
	
	public MBorder() { }

	public MBorder(String type, String parse) {
		parse(type, parse);
	}
	
	public void parse(String type, String parse) {
		if(type != "") {
			String[] types = type.substring(type.indexOf("-") + 1).split("-");
			for(String str : types) {
				if(str.equals("top"))
					top = createBorder("top", parse);
				else if(str.equals("right"))
					right = createBorder("right", parse);
				else if(str.equals("bottom"))
					bottom = createBorder("bottom", parse);
				else if(str.equals("left"))
					left = createBorder("left", parse);
				else
					System.err.println("Unknown border type " + str);
			}
		}else
			top = right = bottom = left = createBorder("all", type);
	}
	
	/**
	 * Create the new border option.
	 */
	private MBorderOptions createBorder(String side, String type) {
		String[] options = type.split(" ");
		MBorderLocation borderLocation = MBorderLocation.valueOf(options[0].toUpperCase());
		float size = ((Integer)MayaUI.parseConfigValue(options[1])).floatValue();
		MBorderType borderType = MBorderType.valueOf(options[2].toUpperCase());
		MayaColor color = (MayaColor)MayaUI.parseConfigValue(options[3]);
		
		return new MBorderOptions(side, borderLocation, size, borderType, color);
	}
	
	/**
	 * Render the border.
	 * TODO: Add outer rendering.
	 */
	public void render(float width, float height) {
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
	
	public MBorder clone() {
		try {
			return (MBorder)super.clone();
		} catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
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