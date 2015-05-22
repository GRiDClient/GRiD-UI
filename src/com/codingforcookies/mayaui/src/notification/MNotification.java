package com.codingforcookies.mayaui.src.notification;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.MayaRender;

public class MNotification extends MayaRender {
	private MNotificationType type;
	private String message;
	private long startTime = 0L;
	
	public MNotification(MNotificationType type, String message) {
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
		
	}
}