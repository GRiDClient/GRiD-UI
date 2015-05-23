package com.codingforcookies.mayaui.src.notification;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.components.UIComponent;

public class MNotification extends UIComponent {
	public int number = 0;
	
	private MNotificationType type;
	private String message;
	private long startTime = 0L;
	
	public MNotification(MNotificationType type, String message) {
		super("notification");
		
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
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0F, 20 * number, 0F);
			System.out.println(uiclass.name);
			RenderHelper.renderWithTheme(uiclass);
			RenderHelper.drawString(uiclass, message, 0, 0);
		}
		GL11.glPopMatrix();
	}
}