package ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * class that is equivalent to a JSlider type object
 * @author proohr
 *
 */
public class Slider {

	private Rectangle bar, slider;
	private Double value, min, max;
	
	public Slider(float x, float y, double lo, double hi){
		bar = new Rectangle(0,0,0,0);
		bar.setCenterX(x);
		bar.setCenterY(y);
	}
	
	public void render(Graphics gfx){
		
	}
	
	public void update(){
		
	}
}
