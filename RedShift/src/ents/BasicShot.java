package ents;

import org.newdawn.slick.Sound;
import org.newdawn.slick.particles.ParticleEmitter;

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
	private Sound impactSnd;
	private ParticleEmitter impactPrtl;
	
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
	
	public void onHit(OptionsEnt e) {
		impactSnd.playAt(0.6f, e.getFxvol(), (float)getX(), (float)getY(), 0.0f);
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
	
	public Sound getSnd() {
		return impactSnd;
	} 

	public void setSnd(Sound snd) {
		this.impactSnd = snd;
	}

	public ParticleEmitter getImpactPrtl() {
		return impactPrtl;
	}

	public void setImpactPrtl(ParticleEmitter impactPrtl) {
		this.impactPrtl = impactPrtl;
	}
	
}
