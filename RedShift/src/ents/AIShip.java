package ents;

import org.newdawn.slick.geom.Polygon;

import ai.AIState;
import core.GameplayState;

public class AIShip extends BasicShip {

	private AIState brains;
	private Polygon sight;
	private double attackRange = 400;
	private double sightRange = 800;
	private double accuracy = 100;
	
	public void setState(AIState p, GameplayState g){
		if(!(brains==null)){
			brains.onLeave(0,g);
		}
		brains = p;
		brains.onEnter(0,g);
	}
	
	
	public void update(int delta, GameplayState g){
		brains.onUpdate(delta,g);
		super.update(delta, g);
	}

	public AIState getBrains() {
		return brains;
	}

	public void setBrains(AIState brains) {
		this.brains = brains;
	}

	public Polygon getSight() {
		return sight;
	}

	public void setSight(Polygon sight) {
		this.sight = sight;
	}

	public double getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(double attackRange) {
		this.attackRange = attackRange;
	}

	public double getSightRange() {
		return sightRange;
	}

	public void setSightRange(double sightRange) {
		this.sightRange = sightRange;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
}
