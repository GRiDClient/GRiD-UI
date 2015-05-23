package com.codingforcookies.mayaui.src.notification;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.components.UIComponent;

public class MNotification extends UIComponent {
	public int number = 0;
	
	private MNotificationType type;
	private String message;
	private long startTime = 0L;
	
	public MNotification(MNotificationType type, String message) {
		super("notifiation");
		
		this.type = type;
		this.message = message;
	}
	
	public void push() {
		MayaUI.addNotification(this);
		startTime = System.currentTimeMillis();
	}
	
	public void update() {
		
	}
	
	public void render() {
		RenderHelper.renderWithTheme(uiclass);
		RenderHelper.drawString(uiclass, message, 0, -20);
	}
}