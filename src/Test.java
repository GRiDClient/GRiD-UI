import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.MayaWindow;
import com.codingforcookies.mayaui.src.ui.MayaWindowPanel;
import com.codingforcookies.mayaui.src.ui.UIManager;
import com.codingforcookies.mayaui.src.ui.theme.MayaColor;
import com.codingforcookies.mayaui.src.ui.theme.components.UIBarGraph;

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
		    Display.setDisplayMode(new DisplayMode(800, 500));
		    Display.create();
		} catch (LWJGLException e) {
		    e.printStackTrace();
		    System.exit(0);
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 500, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		MayaUI.initialize();
		
		getDelta();
        lastFPS = getTime();
		
		init();
		
		while(!Display.isCloseRequested()) {
			update(getDelta());
			updateFPS();
			
			render();
			
		    Display.update();
		}
	 
		Display.destroy();
	}
	
	public int getDelta() {
        long time = getTime();
        int delta = (int)(time - lastFrame);
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
	
	public UIManager uimanager = new UIManager();
	
	private UIBarGraph performancechart;
	public void init() {
		MayaUI.SCREEN_WIDTH = Display.getWidth();
		MayaUI.SCREEN_HEIGHT = Display.getHeight();
		
		uimanager.newWindow(new MayaWindow(uimanager, "Test Window", 10, 10, 300, 200));
		
		uimanager.newWindow(new MayaWindow(uimanager, "Test Window", 320, 10, 470, 200));
		
		uimanager.newWindow(new MayaWindowPanel(uimanager, Display.getWidth() - 210, Display.getHeight() - 210, 200, 200) {
			public void init() {
				super.init();
				
				performancechart = new UIBarGraph(Display.getWidth() - 202, Display.getHeight() - 202, 180, 185);
				addComponent(performancechart);
			}
		});

		performancechart.addBar("update", new MayaColor().random());
		performancechart.addBar("render", new MayaColor().random());
		performancechart.addBar("hyrtj", new MayaColor().random(), 37);
		performancechart.addBar("hyet", new MayaColor().random(), 23);
		performancechart.addBar("hrehtre", new MayaColor().random(), 13);
		performancechart.addBar("xcfgjtu", new MayaColor().random(), 50);
		performancechart.addBar("grehtr", new MayaColor().random(), 0);
		performancechart.addBar("kjur", new MayaColor().random(), 8);
		performancechart.addBar("jyrtjtyr", new MayaColor().random(), 64);
		performancechart.addBar("loikuyjh", new MayaColor().random(), 100);
	}
	
	public void update(int delta) {
		long start = System.currentTimeMillis() - 50;
		
		uimanager.doUpdateUI();
		
		performancechart.updateBar("update", (int)(System.currentTimeMillis() - start) + 2);
	}
	
	public void render() {
		long start = System.currentTimeMillis() - 100;
		
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
		
		performancechart.updateBar("render", (int)(System.currentTimeMillis() - start) + 5);
	}
}