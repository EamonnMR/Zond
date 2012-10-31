package ui;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import core.GameDatabase;

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
	 * takes text and draws it using a spritesheet 
	 * @param s string to show
	 * @param font font to use
	 * @param gdb gamedatabase
	 * @param gfx 
	 * @param p point to draw at
	 * @throws SlickException
	 */
	public void convertTextToImage(String s, String font, GameDatabase gdb, Graphics gfx, Point p) throws SlickException{
		SpriteSheetFont f = gdb.getFont(font);
		for(int i=0; i<s.length();i++){
			f.drawString((float)p.x+(i*12), (float)p.y, String.valueOf(s.charAt(i)));
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
	 * draws an image at the center of a shape
	 * @param g Graphics
	 * @param b Shape
	 * @param i Image
	 */
	public void drawImageAtShapeCenter(Graphics gfx, Shape b, Image i){
		float x = b.getCenterX(), y=b.getCenterY();
		int h=i.getHeight(),w=i.getWidth();
		gfx.drawImage(i, x-(w/2), y-(h/2));
	}
	
	/**
	 * draws an image centered on a point
	 * @param gfx
	 * @param i
	 * @param x
	 * @param y
	 */
	public void drawImageCenteredOnPoint(Graphics gfx, Image i, Point p){
		int w=i.getWidth(),h=i.getHeight();
		gfx.drawImage(i, p.x-(w/2), p.y-(h/2));
	}
	
	/**
	 * draws an image directly next to another image, no rotations!
	 * @param gfx
	 * @param orig
	 * @param prox
	 * @param p
	 * @param dir 0-l, 1-r, 2-u, 3-d
	 * @param dist
	 */
	public void drawImageNextToImage(Graphics gfx, Image orig, Image prox, Point p, int dir, int dist){
		int w = orig.getWidth(), h=orig.getHeight();
		float x=0f,y=0f;
		if(dir==0){
			x=(p.x-w/2)-(dist+prox.getWidth());
			y=p.y-h/2;
		}else if(dir==1){
			x=(p.x+w/2)+dist;
			y=p.y-h/2;
		}else if(dir==2){
			x=p.x;
			y=(p.y-h/2)-(prox.getHeight()+dist);
		}else if(dir==3){
			x=p.x;
			y=(p.y+h/2)+(+dist);
		}
		gfx.drawImage(prox, x, y);
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
	 * @param s string
	 * @param b shape
	 * @param distance from shape
	 * @param direction: 0 left, 1 right, 2 up, 3 down
	 * @param gfx graphics class
	 */
	public void drawStringNextToImage(String s, Image i, int distance, int dir, Graphics gfx, Point p){
		float cX= 0.0f, cY = 0.0f;
		int w=i.getWidth(),h=i.getHeight();
		if(dir == 0){
			cX = (float) (p.getX() - (distance+ getStringPixelWidth(s)));
			cY = (float) p.getY();
		}else if(dir == 1){
			cX = (float) ((p.getX()+(w/2)) + distance);
			cY = (float) p.getY();
		}else if(dir == 2){
			cX = (float) (p.getX()-(getStringPixelWidth(s)/2));
			cY = (float) ((p.getY()-h/2) - distance);
		}else {
			cX = (float) (p.getX()-(getStringPixelWidth(s)/2));
			cY = (float) ((p.getY()-h/2) + distance)+16;
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
