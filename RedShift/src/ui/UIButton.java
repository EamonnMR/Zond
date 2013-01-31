package ui;

import org.newdawn.slick.geom.Rectangle;

/**
 * dont ask; I didnt want to make a button class (mainly cause I would be tempted to write a real UI lib)
 * but it was necessary for the HangarBayState dammit!
 * @author proohr
 * @version: 1.01
 */
public class UIButton {

	private String name;
	private Rectangle r;
	private boolean state;
	private Object thing;
	
	/**
	 * 
	 * @param s name of button
	 * @param b clicked / rolled over
	 * @param obj 
	 */
	public UIButton(String s, boolean b, Object obj){
		name = s;
		state = b;
		thing = obj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Object getThing() {
		return thing;
	}

	public void setThing(Object thing) {
		this.thing = thing;
	}

	public Rectangle getRectangle() {
		return r;
	}

	public void setRectangle(Rectangle r) {
		this.r = r;
	}
}
