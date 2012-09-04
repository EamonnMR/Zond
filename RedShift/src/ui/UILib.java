package ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/**
 * a file of nothing but common methods that are used by 
 * all the menu things. Yeah its primitive but I need it
 * right now :P
 * @author proohr
 *
 */

public class UILib {
 
	public UILib(){
	}
	
	/**
	 * draws a string at the center of a shape
	 * @param s string
	 * @param b shape
	 * @param gfx graphics
	 */
	public void drawStringAtShapeCenter(String s, Shape b, Graphics gfx){
		int len = s.length()*8; 
		float diff = len/2;
		float str_x = b.getCenterX()-diff;
		gfx.drawString(s, str_x, b.getCenterY()-8);
	}
	
	/**
	 * @param s string
	 * @param b shape
	 * @param distance from shape
	 */
	public void drawStringNextToShape(String s, Shape b, int distance, Graphics gfx){
		float dist = b.getMaxX() + distance;
		gfx.drawString(s, dist, b.getCenterY()-8);
	}
}
