package com.codingforcookies.mayaui.src.ui.theme.components;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.font.MayaFontRenderer;

/**
 * Maya UI button component. A clickable button for windows and panels.
 * @author Stumblinbear
 */
public class UIButton extends UIComponent {
	/**
	 * The text to be drawn.
	 */
	private String text;
	
	public UIButton(String text) {
		super("button");
		
		this.text = text;
	}
	
	public void update(float delta) { }
	
	public void render(float delta) {
		String drawntext = text;
		
		MayaFontRenderer.push(fontsize);
		
		if(MayaFontRenderer.getStringWidth(drawntext) > width) {
			drawntext += "...";
			while(MayaFontRenderer.getStringWidth(drawntext) > width)
				drawntext = drawntext.substring(0, drawntext.length() - 4) + "...";
		}
		
		GL11.glTranslatef(x, y, 0F);
		RenderHelper.renderWithTheme(isHovering ? hoverClass : uiclass, width, height);
		
		RenderHelper.drawString(isHovering ? hoverClass : uiclass, drawntext, width / 2 - MayaFontRenderer.getStringWidth(drawntext) / 2, MayaFontRenderer.CHAR_WIDTH_HALF - height / 2);
		
		MayaFontRenderer.pop();
	}
	
	public UIButton setBounds(float x, float y, float width, float height) {
		return (UIButton)super.setBounds(x, y, width, height);
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
	public UIButton setText(String text) {
		this.text = text;
		return this;
	}
}