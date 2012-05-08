package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

/**
 * 
 * @author Roohr
 * @version 1.0
 */
public class BasicShot extends BaseEnt {

	//vars
	private double damage;
	private Shape collider;
	private double speed;
	private int interval;
	private boolean isDead;
	private int timer;
	private double theta;
	

	//const
	public BasicShot(Image img, double spd, int life, double dmg, double sx, double sy, Shape col){
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
//			timer += interval;
			collider.setCenterX((float)getX());
			collider.setCenterY((float)getY());

			double gx = (-Math.cos(Math.toRadians(getImg().getRotation())+theta))+getX(); 
			double gy = (-Math.sin(Math.toRadians(getImg().getRotation())+theta))+getY(); 
			
			setX(gx);
			setY(gy);
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

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getInterval() {
		return interval;
	}
	
	public boolean getDead() {
		return isDead;
	}
	public void setDead(boolean isD) {
		this.isDead = isD;
	}
}
