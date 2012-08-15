package ents;

import org.newdawn.slick.openal.Audio;

/**
 * derive any kind of projectile from this class
 * @author Roohr
 * @version 1.0
 */
public class BasicShot extends BaseEnt {

	//vars
	double speedX, speedY;
	
	private double damage;
	private float speed;
	private int interval;
	private int timer;
	private Audio impactSnd; //FIXME: Fix sound!
	//constructor
	public BasicShot(){
	}
	
	//methods
	public void update(int delta){	
		timer +=delta;
		if(timer <= interval){
			/*
			float hip = speed * delta;
			double angle = (Math.toRadians(getImg().getRotation()));
			double dx = getX();
			double dy = getY();

			dx += hip * Math.cos(angle);  //This is the proper algorithm... -EMR
			dy += hip * Math.sin(angle);  //I was thought to worship the Unit Circle in high school
			//And I cannot harden my heart against it now, in our darkest hour.
			*/
			addX(speedX * delta);
			addY(speedY * delta);
	
			getCollider().setCenterX((float)getX());
			getCollider().setCenterY((float)getY());
		}
	}
	
	public void onHit() {
		//snd.play(); FIXME: Fix sound!
	}
	
	public void render(int xOffset, int yOffset){
		getImg().drawCentered(xOffset + (float)getX(), yOffset + (float)getY());
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
	
	public void setSpeeds(double speedX, double speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
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
	
	//FIXME: Fix sound!
	
	public Audio getSnd() {
		return impactSnd;
	} 

	public void setSnd(Audio snd) {
		this.impactSnd = snd;
	}
	
}
