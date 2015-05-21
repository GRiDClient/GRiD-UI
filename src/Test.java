import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;
import com.codingforcookies.mayaui.src.ui.MayaWindow;
import com.codingforcookies.mayaui.src.ui.UIManager;

public class Test {
	public static void main(String[] args) {
		System.out.println("Starting LWJGL " + Sys.getVersion() + "..");

		new Test();
	}
	
	long lastFrame;
    int fps;
    long lastFPS;
	
	public Test() {
		MayaUI.initialize();
		
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
	
	public void init() {
		MayaUI.SCREEN_WIDTH = Display.getWidth();
		MayaUI.SCREEN_HEIGHT = Display.getHeight();
		
		uimanager.newWindow(new MayaWindow(10, 10, 300, 200) {
			public void update() {
				super.update();
				
			}
			
			public void render() {
				super.render();
				
				
			}
		});
	}
	
	public void update(int delta) {
		uimanager.doUpdateUI();
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		
	    GL11.glColor3f(.1F, .1F, .1F);
	    
	    GL11.glBegin(GL11.GL_QUADS);
	        GL11.glVertex2f(0, 0);
			GL11.glVertex2f(Display.getWidth(), 0);
			GL11.glVertex2f(Display.getWidth(), Display.getHeight());
			GL11.glVertex2f(0, Display.getHeight());
	    GL11.glEnd();
	    
		uimanager.doRenderUI();
	}
}