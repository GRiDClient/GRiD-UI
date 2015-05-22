package com.codingforcookies.mayaui.src.notification;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import com.codingforcookies.mayaui.src.ui.MWindowBase;

public class NotificationManager {
	private static MWindowBase notificationRender;
	private static List<MNotification> notifications = new ArrayList<MNotification>();

	protected void addNotification(MNotification mNotification) {
		if(notifications.size() == 0) {
			notificationRender = new MWindowBase(0, 0, Display.getWidth(), Display.getHeight()) {
				public void update() {
					for(int i = 0; i < notifications.size(); i++)
						notifications.get(i).update();
					
					if(notifications.size() == 0)
						notificationRender.scheduledForDrop = true;
				}
				
				public void render() {
					for(int i = 0; i < notifications.size(); i++)
						notifications.get(i).render();
				}
			};
		}
		
		notifications.add(mNotification);
	}
}