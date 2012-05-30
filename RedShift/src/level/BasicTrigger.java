package level;

import org.newdawn.slick.geom.Shape;

/**
 * Defines a basic Trigger entity that is used by the 
 * BasicLevel class. 
 * @author proohr
 * @version 1.0
 */
public class BasicTrigger {

	private String name;
	private String targetName;
	private Shape collider;
	private float x,y;
	private boolean isTrigged;
	
	public BasicTrigger(){
		
	}
	
	public void trigger(boolean b){
		isTrigged = b;
	}
	
	public boolean isTrigged() {
		return isTrigged;
	}
	
	public void setTrigged(boolean isTrigged) {
		this.isTrigged = isTrigged;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Shape getCollider() {
		return collider;
	}

	public void setCollider(Shape collider) {
		this.collider = collider;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}
