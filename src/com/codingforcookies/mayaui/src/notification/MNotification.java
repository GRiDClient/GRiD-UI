package com.codingforcookies.mayaui.src.notification;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.MayaFontRenderer;
import com.codingforcookies.mayaui.src.ui.RenderHelper;
import com.codingforcookies.mayaui.src.ui.theme.UIClass;
import com.codingforcookies.mayaui.src.ui.theme.components.UIComponent;
import com.codingforcookies.mayaui.src.ui.theme.parser.MOptionMargin;

public class MNotification extends UIComponent {
	private static long SHOW_TIME = 500L;
	private static long STAY_TIME = 5000L;
	private static long HIDE_TIME = 500L;
	
	float spacing;
	
	private MNotificationType type;
	private String message;
	
	private long startTime = 0L;
	private float showPercent = 0F;
	
	public MNotification(MNotificationType type, String message) {
		super("notification");
		
		this.type = type;
		this.message = message;

		this.width = MayaFontRenderer.getStringWidth(message) + 20F;
		this.height = uiclass.get("height").getValue(new Float(0));
		this.spacing = ((MOptionMargin)uiclass.get("margin")).bottom;
	}
	
	public void push() {
		MayaUI.addNotification(this);
	}
	
	public void update(float delta) {
		if(startTime == 0L)
			startTime = System.currentTimeMillis();
		
		long timeSince = System.currentTimeMillis() - startTime + 1;
		
		if(timeSince < SHOW_TIME)
			showPercent = -easeOut(timeSince, 0F, 1F, SHOW_TIME);
		else if(timeSince > SHOW_TIME + STAY_TIME + HIDE_TIME)
			scheduledForDrop = true;
		else if(timeSince > SHOW_TIME + STAY_TIME)
			showPercent = -easeOut(timeSince - (SHOW_TIME + STAY_TIME), 1F, -1F, HIDE_TIME);
	}
	
	public void render() {
		GL11.glPushMatrix();
		{
			UIClass notifClass;
			if(uiclass.hasClass("#" + type.name().toLowerCase()))
				notifClass = uiclass.getClass("#" + type.name().toLowerCase());
			else
				notifClass = uiclass;
			
			GL11.glTranslatef(width * showPercent, -height + spacing, 0F);
			RenderHelper.renderWithTheme(notifClass, width);
			
			GL11.glTranslatef(10F, -(height / 2 - MayaFontRenderer.CHAR_WIDTH_HALF), 0F);
			RenderHelper.drawString(notifClass, message, 0, 0);
		}
		GL11.glPopMatrix();
	}
	
	public float easeOut(float t, float b, float c, float d) {
		return c * (float)Math.sqrt(1 - (t=t/d-1)*t) + b;
	}
}