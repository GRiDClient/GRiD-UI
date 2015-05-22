package com.codingforcookies.mayaui.src.notification;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.MWindowBase;

public class NotificationManager {
	private static List<MNotification> notifications = new ArrayList<MNotification>();

	public static void addNotification(MNotification mNotification) {
		if(notifications.size() == 0) {
			MayaUI.getUIManager().createWindow(new MWindowBase(0, 0, Display.getWidth(), Display.getHeight()) {
				public void update() {
					for(int i = 0; i < notifications.size(); i++) {
						notifications.get(i).update();
						if(notifications.get(i).scheduledForDrop) {
							notifications.remove(i);
							System.out.println("Dropped notification");
						}
					}
					
					if(notifications.size() == 0) {
						this.scheduledForDrop = true;
						System.out.println("Dropped notification panel");
					}
				}
				
				public void render() {
					for(int i = 0; i < notifications.size(); i++)
						notifications.get(i).render();
				}
			});
			
			System.out.println("Created notification panel");
		}
		
		notifications.add(mNotification);
	}
}