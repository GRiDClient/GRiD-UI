package com.codingforcookies.mayaui.src.notification;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.MWindowBase;
import com.codingforcookies.mayaui.src.ui.theme.MAlign;
import com.codingforcookies.mayaui.src.ui.theme.components.UIComponent;

public class NotificationManager {
	private final static int MAX_SHOWN = 5;
	private static MWindowBase notificationWindow;
	
	public static void addNotification(MNotification mNotification) {
		if(notificationWindow == null) {
			notificationWindow = new MWindowBase(Display.getWidth(), Display.getHeight(), 0, 0) {
				public void init() {
					super.init();
					anchor = MAlign.BOTTOMRIGHT;
				}
				
				public void update(float delta) {
					for(int i = 0; i < (components.size() > MAX_SHOWN ? MAX_SHOWN : components.size()); i++) {
						components.get(i).update(delta);
						if(components.get(i).scheduledForDrop) {
							components.remove(i);
							i--;
						}
					}
					
					if(components.size() == 0) {
						scheduledForDrop = true;
						notificationWindow = null;
					}
				}
				
				public void addComponent(UIComponent component) {
					components.add(component);
				}
				
				public void drawComponents() {
					float totalspacing = 0F;
					for(int i = 0; i < (components.size() > MAX_SHOWN ? MAX_SHOWN : components.size()); i++)
						totalspacing += -components.get(i).height - ((MNotification)components.get(i)).spacing;
					
					GL11.glTranslatef(0F, -totalspacing, 0F);
					for(int i = 0; i < (components.size() > MAX_SHOWN ? MAX_SHOWN : components.size()); i++) {
						GL11.glTranslatef(0F, -components.get(i).height - ((MNotification)components.get(i)).spacing, 0F);
						components.get(i).render();
					}
				}
			};
			
			MayaUI.getUIManager().createWindow(notificationWindow);
		}
		
		notificationWindow.addComponent(mNotification);
	}
}