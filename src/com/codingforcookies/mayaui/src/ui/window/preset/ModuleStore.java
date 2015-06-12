package com.codingforcookies.mayaui.src.ui.window.preset;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.theme.components.UILabel;
import com.codingforcookies.mayaui.src.ui.window.MWindowPanel;

public class ModuleStore extends MWindowPanel {
	public ModuleStore() {
		super("Module Store", 0, 0, MayaUI.SCREEN_WIDTH, MayaUI.SCREEN_HEIGHT);
	}
	
	public void init() {
		super.init();
		
		addComponent(new UILabel("Module Store").setFontSize(16F).setBounds(10, 10, MayaUI.SCREEN_WIDTH - 20, 30));
	}
}