package ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
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
		float width = getStringPixelWidth(s);
		float height = getStringPixelHeight(s); 
		float str_x = b.getCenterX()-(width/2);
		float str_y = b.getCenterY()-height;
		gfx.drawString(s, str_x, str_y);
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
		int nl = 0;
		for(int i=0; i<s.length();i++){
			if(s.charAt(i)=='\n'){
				nl = i;
				break;
			}
		}
		if(nl!=0){
			String str = s.substring(0, nl);
			return str.length()*10;
		}else{
			return s.length()*10;
		}
	}
	
	/**
	 * simply gets the height in pixels of a given string
	 * @param s
	 * @return
	 */
	public int getStringPixelHeight(String s){
		int nl=0;
		for(int i=0; i<s.length();i++){
			if(s.charAt(i)=='\n'){
				nl++;
			}
		}
		if(nl>=1){
			return nl*14;
		}else{
			return 8;
		}
	}
	
	/**
	 * draws a rectangle around a given string;
	 * @param s
	 * @param x
	 * @param y
	 * @param gfx
	 */
	public void drawRectangleAroundString(String s, float x, float y, Graphics gfx){
		Rectangle rec;
		int len = getStringPixelWidth(s);
		int h = getStringPixelHeight(s);
		rec = new Rectangle(x-2, y-2, len+4,h+14);
		gfx.draw(rec);
	}
	
	
	/**
	 * draws a shape with a given color
	 * @param b
	 * @param color
	 */
	public void highlightShape(Shape b, Color color, Graphics gfx){
		gfx.setColor(color);
		gfx.draw(b);
	}
	
	/**
	 * draws a string with a given color
	 * @param s
	 * @param color
	 * @param gfx
	 */
	public void highlightString(String s, Color color, Graphics gfx, float x, float y){
		gfx.setColor(color);
		gfx.drawString(s, x, y);
	}
	
}
