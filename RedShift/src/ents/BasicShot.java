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
	private int lifetime;
	private int interval;
	private double theta;
	

	//const
	public BasicShot(Image img, double spd, int life, double dmg, double sx, double sy, Shape col){
		this.setImg(img);
		this.speed = spd;
		this.lifetime = life;
		this.damage = dmg;
		this.setX(sx);
		this.setY(sy);
		this.collider = col;
		this.theta = Math.PI/2;
		this.interval = 10;
	}
	//methods
	public void update(int delta){
		lifetime -= (delta);
			
			
			collider.setCenterX((float)getX());
			collider.setCenterY((float)getY());

			//+getX()+speed/+getY()+speed
			double gx = (-Math.cos(Math.toRadians(getImg().getRotation())+theta))+getX(); 
			double gy = (-Math.sin(Math.toRadians(getImg().getRotation())+theta))+getY(); 
		
			setX(gx);
			setY(gy);
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

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
}
