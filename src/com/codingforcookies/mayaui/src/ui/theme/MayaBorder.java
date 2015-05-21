package com.codingforcookies.mayaui.src.ui.theme;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;

public class MayaBorder {
	public boolean top, right, bottom, left;
	public MayaBorderType borderType;
	public float size;
	public MayaColor color;
	
	public MayaBorder() { }

	public MayaBorder(String type, String parse) {
		if(type != "") {
			String[] types = type.substring(type.indexOf("-") + 1).split("-");
			for(String str : types) {
				if(str.equals("top"))
					top = true;
				else if(str.equals("right"))
					right = true;
				else if(str.equals("bottom"))
					bottom = true;
				else if(str.equals("left"))
					left = true;
				else
					System.err.println("Unknown border type " + str);
			}
		}else
			top = right = bottom = left = true;
		
		String[] options = parse.split(" ");
		borderType = MayaBorderType.valueOf(options[0].toUpperCase());
		size = ((Integer)MayaUI.parseConfigValue(options[1])).floatValue();
		color = (MayaColor)MayaUI.parseConfigValue(options[2]);
	}

	public void render(float x, float y, float width, float height) {
		color.use();
		
		switch(borderType) {
			case INNER:
				if(top)
					RenderHelper.renderBox(x, y, width, size);
				if(right)
					RenderHelper.renderBox(x + width - size, y + (top ? size : 0), size, height - (top ? size : 0) - (bottom ? size : 0));
				if(bottom)
					RenderHelper.renderBox(x, y + height - size, width, size);
				if(left)
					RenderHelper.renderBox(x, y + (top ? size : 0), size, height - (top ? size : 0) - (bottom ? size : 0));
				break;
			case OUTER:
				if(top)
					RenderHelper.renderBox(x - (left ? size : 0), y - size, width + (left ? size : 0) + (right ? size : 0), size);
				if(right)
					RenderHelper.renderBox(x + width, y, size, height);
				if(bottom)
					RenderHelper.renderBox(x - (left ? size : 0), y + height, width + (left ? size : 0) + (right ? size : 0), size);
				if(left)
					RenderHelper.renderBox(x - size, y, size, height);
				break;
		}
	}
}