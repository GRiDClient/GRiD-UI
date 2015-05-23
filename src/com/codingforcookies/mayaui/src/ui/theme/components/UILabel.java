package com.codingforcookies.mayaui.src.ui.theme.components;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.MayaFontRenderer;
import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MAlign;

/**
 * Maya UI label component. Displays text in the window.
 * @author Stumblinbear
 */
public class UILabel extends UIComponent {
	/**
	 * The text to be drawn.
	 */
	private String text;
	private MAlign align = MAlign.LEFT;
	
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

		GL11.glPushMatrix();
		{
			RenderHelper.drawString(uiclass, drawntext, x + (align == MAlign.CENTER ? width / 2 - MayaFontRenderer.getStringWidth(drawntext) / 2 : align == MAlign.RIGHT ? width - MayaFontRenderer.getStringWidth(drawntext) : 0), y - 2);
		}
		GL11.glPopMatrix();
	}
	
	public UILabel setBounds(float x, float y, float width, float height) {
		return (UILabel)super.setBounds(x, y, width, height);
	}
	
	/**
	 * Get the text to be drawn.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Set the text to be drawn.
	 */
	public UILabel setText(String text) {
		this.text = text;
		return this;
	}
}