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
		float diff = getStringPixelWidth(s);
		float str_x = b.getCenterX()-(diff/2);
		gfx.drawString(s, str_x, b.getCenterY()-8);
	}
	
	/**
	 * @param s string
	 * @param b shape
	 * @param distance from shape
	 * @param direction: 0 left, 1 right, 2 up, 3 down
	 * @param gfx graphics class
	 */
	public void drawStringNextToShape(String s, Shape b, int distance, int dir, Graphics gfx){
		float cX= 0.0f, cY = 0.0f;
		if(dir == 0){
			cX = b.getX() - distance;
			cY = b.getCenterY();
		}else if(dir == 1){
			cX = b.getMaxX() + distance;
			cY = b.getCenterY();
		}else if(dir == 2){
			cX = b.getCenterX()-(getStringPixelWidth(s)/2);
			cY = b.getY() - distance;
		}else {
			cX = b.getCenterX()-(getStringPixelWidth(s)/2);
			cY = (b.getY() + distance)+16;
		}
		gfx.drawString(s, cX, cY-8);
	}
	
	/**
	 * simply gets the width in pixels of a given string
	 * @param s string
	 * @return width in pixels
	 */
	public int getStringPixelWidth(String s){
		return s.length()*10;
	}
	
	
}
