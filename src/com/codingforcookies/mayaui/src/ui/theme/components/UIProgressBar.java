package com.codingforcookies.mayaui.src.ui.theme.components;

import com.codingforcookies.mayaui.src.ui.RenderHelper;
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
	private float progress = 0F;
	
	public UIProgressBar() {
		super("progressbar");
		progressClass = uiclass.getClass(".overlay");
	}
	
	public void update(float delta) { }

	public void render() {
		RenderHelper.renderWithTheme(isHovering ? hoverClass : uiclass, width, height);
		RenderHelper.renderWithTheme(progressClass, width * progress, height);
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