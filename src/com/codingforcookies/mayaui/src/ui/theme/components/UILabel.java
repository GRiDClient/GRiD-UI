package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.texture.MayaFontRenderer;
import com.codingforcookies.mayaui.src.ui.theme.MAlign;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;

public class UILabel extends UIComponent {
	private String text;
	private MAlign align = MAlign.LEFT;
	private MayaColor color;
	
	public UILabel(String text) {
		super("label");
		
		this.text = text;
	}
	
	public UILabel(String text, MAlign align) {
		this(text);
		
		this.align = align;
	}
	
	public void update() { }
	
	public void render() {
		String drawntext = text;
		
		if(MayaFontRenderer.getStringWidth(drawntext) > width) {
			drawntext += "...";
			while(MayaFontRenderer.getStringWidth(drawntext) > width)
				drawntext = drawntext.substring(0, drawntext.length() - 4) + "...";
		}
		
		//RenderHelper.renderBox(x, y, width, height);
		
		MayaFontRenderer.draw(drawntext, x + (align == MAlign.CENTER ? width / 2 - MayaFontRenderer.getStringWidth(drawntext) / 2 : align == MAlign.RIGHT ? width - MayaFontRenderer.getStringWidth(drawntext) : 0), y - 2, color != null ? color : uiclass.get("color", new MayaColor(), MayaColor.GLOBAL_TEXT));
	}
	
	public UILabel setBounds(float x, float y, float width, float height) {
		return (UILabel)super.setBounds(x, y, width, height);
	}
	
	
	public String getText() {
		return text;
	}
	
	public UILabel setText(String text) {
		this.text = text;
		return this;
	}

	public UILabel setColor(MayaColor color) {
		this.color = color;
		return this;
	}
}