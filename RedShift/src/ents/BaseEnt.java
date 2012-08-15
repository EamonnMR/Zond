package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.Audio;

/**
 * root entity from which BasicShip, BasicEngine, and BasicGun derive from
 * @author proohr
 * @version 1.0
 */
public class BaseEnt {

	//vars
	private double x,y;
	private Image img;
	private float turnrate;
	private Shape collider;
	private String trigger;
	private Audio deathSnd;
	
	//constructors
	public BaseEnt(){}
	
	//methods
	public void render(int xOffset, int yOffset){
		getImg().drawCentered((float)getX(),(float) getY());
	}
	
	public void update(int delta){
		
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void addX(double dx){ //Makes movement algorithms neater-EMR
		this.x += dx;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void addY(double dy){ //Makes movement algorithms neater-EMR
		this.y += dy;
	}


	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	public float getTurnrate() {
		return turnrate;
	}
	
	public void setTurnrate(float turnrate) {
		this.turnrate = turnrate;
	}

	public Shape getCollider() {
		return collider;
	}

	public void setCollider(Shape collider) {
		this.collider = collider;
	}

	public void setTriggerTargetName(String trigger) {
		this.trigger = trigger;
	}

	public String getTriggerTargetName() {
		return trigger;
	}

	public Audio getDeathSnd() {
		return deathSnd;
	}

	public void setDeathSnd(Audio deathSnd) {
		this.deathSnd = deathSnd;
	}
	
}
