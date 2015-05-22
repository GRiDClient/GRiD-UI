package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;

/**
 * Maya UI progress bar component. Displays a progress bar in the window.
 * @author Stumblinbear
 */
public class UIProgressBar extends UIComponent {
	/**
	 * The bar that shows the progress' class.
	 */
	private UIClass progressClass;
	private MayaColor bgcolor, color;
	private float progress = 0F;
	
	public UIProgressBar() {
		super("progressbar");
		progressClass = uiclass.getClass(".overlay");
		
		bgcolor = uiclass.get("background-color", new MayaColor(), MayaColor.GLOBAL_BACKGROUND);
		color = progressClass.get("background-color", new MayaColor(), MayaColor.GLOBAL_BACKGROUND);
	}
	
	public void update() { }
	
	public void render() {
		RenderHelper.renderWithTheme(uiclass, width, height, bgcolor);
		RenderHelper.renderWithTheme(progressClass, width * progress, height, color);
	}
	
	public UIProgressBar setBounds(float x, float y, float width, float height) {
		return (UIProgressBar)super.setBounds(x, y, width, height);
	}
	
	/**
	 * Set the current progress. 0F = 0% .5F = 50% 1F = 100%
	 * @param progress
	 */
	public void setProgress(float progress) {
		this.progress = progress;
	}
	
	/**
	 * Gets the current progress. 0F = 0% .5F = 50% 1F = 100%
	 */
	public float getProgress() {
		return progress;
	}
}