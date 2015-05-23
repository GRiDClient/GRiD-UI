import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.notification.MNotification;
import com.codingforcookies.mayaui.src.notification.MNotificationType;
import com.codingforcookies.mayaui.src.ui.MWindow;
import com.codingforcookies.mayaui.src.ui.MWindowBase;
import com.codingforcookies.mayaui.src.ui.MWindowPanel;
import com.codingforcookies.mayaui.src.ui.UIManager;
import com.codingforcookies.mayaui.src.ui.theme.MAlign;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.components.UIBarGraph;
import com.codingforcookies.mayaui.src.ui.theme.components.UIBox;
import com.codingforcookies.mayaui.src.ui.theme.components.UILabel;
import com.codingforcookies.mayaui.src.ui.theme.components.UIProgressBar;
import com.codingforcookies.mayaui.src.ui.theme.components.UISeparator;

/**
 * Simple testing program
 * @author Stumblinbear
 */
public class Test {
	public static void main(String[] args) {
		System.out.println("Starting LWJGL " + Sys.getVersion() + "..");

		new Test();
	}
	
	long lastFrame;
    int fps;
    long lastFPS;
	
	public Test() {
		try {
		    Display.setResizable(true);
		    Display.setDisplayMode(new DisplayMode(800, 500));
		    Display.create();
		} catch (LWJGLException e) {
		    e.printStackTrace();
		    System.exit(0);
		}
		
		MayaUI.initialize();
		
		getDelta();
        lastFPS = getTime();
		
		init();
		
		while(!Display.isCloseRequested()) {
			if(Display.wasResized()) {
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());

				int changewidth = Display.getWidth() - MayaUI.SCREEN_WIDTH;
				int changeheight = Display.getHeight() - MayaUI.SCREEN_HEIGHT;
				
				MayaUI.SCREEN_WIDTH = Display.getWidth();
				MayaUI.SCREEN_HEIGHT = Display.getHeight();
				
				MayaUI.getUIManager().onWindowResized(changewidth, changeheight);
			}
			
			update(getDelta());
			updateFPS();
			
			render();
			
		    Display.update();
		}
		
		Display.destroy();
	}
	
	public float getDelta() {
        long time = getTime();
        float delta = (int)(time - lastFrame);
        lastFrame = time;
      
        return delta;
    }
	
	public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
	public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("Maya UI  v." + MayaUI.version + " - " + fps + "fps");
            fps = 0;
            lastFPS += 1000;
        }
        
        fps++;
    }
	
	private UIManager uimanager;
	
	private UIBarGraph perfChart;
	private UILabel perfTime;

	private List<Float> updateTimes = new ArrayList<Float>();
	private List<Float> renderTimes = new ArrayList<Float>();
	
	public void init() {
		MayaUI.SCREEN_WIDTH = Display.getWidth();
		MayaUI.SCREEN_HEIGHT = Display.getHeight();
		
		uimanager = MayaUI.getUIManager();
		
		uimanager.createWindow(new MWindow("Test Window", 10, 15, 300, 200) {
			public void init() {
				super.init();
				this.anchor = MAlign.TOPLEFT;
			}
		});
		uimanager.createWindow(new MWindow("Test Window", 320, 15, 470, 200) {
			public void init() {
				super.init();
				this.anchor = MAlign.TOPRIGHT;
			}
		});
		uimanager.createWindow(new MWindowPanel("Performance Monitor", 10, Display.getHeight() - 210, 300, 200) {
			public void init() {
				super.init();
				this.anchor = MAlign.BOTTOMLEFT;
				
				UILabel perfLabel = new UILabel("Performance Monitor", MAlign.CENTER).setBounds(5, 5, 185, 10);
				addComponent(perfLabel);
				
				perfChart = new UIBarGraph().setBounds(5, 17, 185, 165);
				addComponent(perfChart);
				
				MayaColor perfUpdateColor = new MayaColor("#DDAF08").darker();
				MayaColor perfRenderColor = new MayaColor("#2676AB").darker();

				addComponent(new UISeparator().setBounds(198, 20, 0, 180));
				
				addComponent(new UIBox(perfUpdateColor).setBounds(210, 20, 10, 10));
				addComponent(new UIBox(perfRenderColor).setBounds(210, 35, 10, 10));
				
				addComponent(new UILabel("Update").setBounds(230, 20, 60, 10));
				addComponent(new UILabel("Render").setBounds(230, 35, 60, 10));
				
				perfTime = new UILabel("0.0s", MAlign.RIGHT).setBounds(210, 180, 80, 10);
				addComponent(perfTime);
			}
		});
		
		uimanager.createWindow(new MWindowBase(0, 0, Display.getWidth(), 5) {
			UIProgressBar prgBar;
			public void init() {
				super.init();
				anchor = MAlign.TOPLEFT;
				
				prgBar = new UIProgressBar().setBounds(0, 0, Display.getWidth(), 5);
				addComponent(prgBar);
			}
			
			public void update(float delta) {
				prgBar.setProgress(prgBar.getProgress() + .0005F);
				
				if(prgBar.getProgress() >= 1F)
					prgBar.setProgress(0);
			}
			
			public void onWindowResized(int changewidth, int changeheight) {
				prgBar.width += changewidth;
			}
		});
		
		perfChart.addBar("update", new MayaColor("#DDAF08"));
		perfChart.addBar("render", new MayaColor("#2676AB"));
		
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000L);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
				MayaUI.addNotification(MNotificationType.INFO, "Loading complete.");
				try {
					Thread.sleep(1000L);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
				MNotification[] testNotifications = new MNotification[] {
						new MNotification(MNotificationType.WARNING, "Initializing to tha moon thrusters."),
						new MNotification(MNotificationType.HELP, "To tha moon thrusters are functioning."),
						new MNotification(MNotificationType.INFO, "Taking off in 3..."),
						new MNotification(MNotificationType.INFO, "Taking off in 2..."),
						new MNotification(MNotificationType.INFO, "Taking off in 1..."),
						new MNotification(MNotificationType.WARNING, "BLAST OFF!!"),
						new MNotification(MNotificationType.INFO, "..."),
						new MNotification(MNotificationType.INFO, "..."),
						new MNotification(MNotificationType.INFO, "..."),
						new MNotification(MNotificationType.INFO, "..."),
						new MNotification(MNotificationType.INFO, "..."),
						new MNotification(MNotificationType.INFO, "..."),
						new MNotification(MNotificationType.INFO, "..."),
						new MNotification(MNotificationType.ERROR, "Out of fuel!"),
						new MNotification(MNotificationType.INFO, "Initiating order #374!"),
						new MNotification(MNotificationType.ERROR, "Self destruct activated.")
					};
				
				for(int i = 0; i < testNotifications.length; i++) {
					testNotifications[i].push();
					
					try {
						Thread.sleep(new Random().nextInt(2000));
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void update(float delta) {
		long start = System.nanoTime();
		
		uimanager.doUpdateUI(delta);
		
		updateTimes.add(0, (float)(System.nanoTime() - start));
		
		while(updateTimes.size() > 500)
			updateTimes.remove(updateTimes.size() - 1);
		while(renderTimes.size() > 500)
			renderTimes.remove(renderTimes.size() - 1);
		
		float updateAvg = average(updateTimes) / 1000000000F;
		float renderAvg = average(renderTimes) / 1000000000F;
		
		perfChart.updateBar("update", updateAvg);
		perfChart.updateBar("render", renderAvg);
		
		perfTime.setText(new DecimalFormat("#.0000000000").format((updateAvg + renderAvg) / 2) + "s");
	}
	
	public float average(List<Float> list) {
		float avg = 0F;
		
		for(Float flo : list)
			avg += flo;
		
		return avg / list.size();
	}
	
	public void render() {
		long start = System.nanoTime();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
	    GL11.glColor3f(.1F, .1F, .1F);
	    
	    GL11.glBegin(GL11.GL_QUADS);
	    {
	        GL11.glVertex2f(0, 0);
			GL11.glVertex2f(Display.getWidth(), 0);
			GL11.glVertex2f(Display.getWidth(), Display.getHeight());
			GL11.glVertex2f(0, Display.getHeight());
	    }
	    GL11.glEnd();
	    
		uimanager.doRenderUI();
		
		renderTimes.add(0, (float)(System.nanoTime() - start));
	}
}