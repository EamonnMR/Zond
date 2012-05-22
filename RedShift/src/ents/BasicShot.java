package ents;

/**
 * derive any kind of projectile from this class
 * @author Roohr
 * @version 1.0
 */
public class BasicShot extends BaseEnt {

	//vars
	private double damage;
	private float speed;
	private int interval;
	private int timer;
	private double theta;
	//constructor
	public BasicShot(){
		theta = Math.PI/2;
	}
	
	//methods
	public void update(int delta){	
		timer +=delta;
		if(timer <= interval){
			float hip = speed * delta;
			double angle = (Math.toRadians(getImg().getRotation()+theta));
			double dx = getX();
			double dy = getY();
			
			dx += hip * Math.sin(angle);
			dy -= hip * Math.cos(angle);
			
			setX(dx);
			setY(dy);
	
			getCollider().setCenterX((float)getX());
			getCollider().setCenterY((float)getY());
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

	public float getSpeed() {
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

	
}
