import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.MayaUI;

public class Test {
	public static void main(String[] args) {
		System.out.println("Starting LWJGL " + Sys.getVersion() + "..");

		new Test();
	}
	
	public Test() {
		// Initialize GRiD UI
		MayaUI.initialize();
		
		try {
		    Display.setDisplayMode(new DisplayMode(800, 600));
		    Display.create();
		    Display.setTitle("GRiD UI  v." + MayaUI.version);
		} catch (LWJGLException e) {
		    e.printStackTrace();
		    System.exit(0);
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		while(!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
			
			render();
			
		    Display.update();
		}
	 
		Display.destroy();
	}
	
	public void render() {
		
	}
}