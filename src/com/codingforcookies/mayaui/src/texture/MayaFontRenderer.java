package com.codingforcookies.mayaui.src.texture;

import org.lwjgl.opengl.GL11;

import com.codingforcookies.mayaui.src.ui.theme.MayaColor;

public class MayaFontRenderer {
	private static final double TEXTURE_WIDTH = 256;
	public static final int CHAR_FILE_WIDTH = 16;
	private static final String chars =
			"                " +
					"                " +
					" !\"#$%&'()x+,-./" +
					"0123456789:;<=>?" +
					"@ABCDEFGHIJKLMNO" +
					"PQRSTUVWXYZ[\\]^_" +
					"`abcdefghijklmno" +
					"pqrstuvwxyz{|}~" +
					"" +
					"" +
					"" +
					"" +
					"" +
					"" +
					"" +
					"" +
					"";

	public static MayaTexture font;

	public static void draw(String string, float x, float y) {
		draw(string, x, y, MayaColor.GLOBAL_TEXT, 8);
	}

	public static void draw(String string, float x, float y, int size) {
		draw(string, x, y, MayaColor.GLOBAL_TEXT, size);
	}

	public static void draw(String string, float x, float y, MayaColor color) {
		draw(string, x, y, color, 8);
	}
	
	public static void draw(String string, float x, float y, MayaColor color, int size) {
		GL11.glPushMatrix();
		{
			GL11.glEnable(GL11.GL_TEXTURE_2D); 
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			color.use();
			font.bind();
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			for(int i = 0; i < string.length(); i++) {
				if(string.charAt(i) == ' ')
					continue;

				int ch = chars.indexOf(string.charAt(i));
				if(ch < 0)
					continue;

				int xx = (ch % 16) * CHAR_FILE_WIDTH;
				int yy = (ch / 16) * CHAR_FILE_WIDTH;
				float xd = x + i * (size - 2);

				GL11.glBegin(GL11.GL_QUADS);
				{
					float left = (float)(1.0 * (xx / TEXTURE_WIDTH));
					float right = (float)(1.0 * ((xx + CHAR_FILE_WIDTH) / TEXTURE_WIDTH));
					float top = (float)(1.0 * (yy / TEXTURE_WIDTH));
					float bottom = (float)(1.0 * ((yy + CHAR_FILE_WIDTH) / TEXTURE_WIDTH));

					GL11.glTexCoord2f(left, top);GL11.glVertex2f(xd, y);
					GL11.glTexCoord2f(right, top); GL11.glVertex2f(xd + size, y);
					GL11.glTexCoord2f(right, bottom); GL11.glVertex2f(xd + size, y - size);
					GL11.glTexCoord2f(left, bottom); GL11.glVertex2f(xd, y - size);
				}
				GL11.glEnd();
			}

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
		GL11.glPopMatrix();
	}
}