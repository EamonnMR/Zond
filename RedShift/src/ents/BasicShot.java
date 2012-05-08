package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

/**
 * derive any kind of projectile from this class
 * @author Roohr
 * @version 1.0
 */
public class BasicShot extends BaseEnt {

	//vars
	private double damage;
	private Shape collider;
	private float speed;
	private int interval;
	private boolean isDead;
	private int timer;

	//constructor
	public BasicShot(Image img, float spd, int life, double dmg, double sx, double sy, Shape col){
		this.setImg(img);
		this.speed = spd;
		this.damage = dmg;
		this.setX(sx);
		this.setY(sy);
		this.collider = col;
		this.theta = Math.PI/2;
		this.setInterval(400);
		this.timer = 0;
	}
	
	//methods
	public void update(int delta){	
		timer +=delta;
		if(timer < interval){
			float hip = speed * delta;
			double angle = Math.toRadians(getImg().getRotation()+theta);
			double dx = getX();
			double dy = getY();
			
			dx += hip * Math.sin(angle);
			dy -= hip * Math.cos(angle);
			
			setX(dx);
			setY(dy);
	
			collider.setCenterX((float)getX());
			collider.setCenterY((float)getY());
		}
	}
	
	public void render(){
		getImg().drawCentered((float)getX(), (float)getY());
	}
	
	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public Shape getCollider() {
		return collider;
	}

	public void setCollider(Shape collider) {
		this.collider = collider;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getInterval() {
		return interval;
	}
	
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	private double theta;
	
}
