package ents;

import org.lwjgl.Sys;

import ai.AIState;

public class AIShip extends BasicShip {

	private AIState brains;
	
	public void setState(AIState p){
		if(!(brains==null)){
			brains.onLeave(0);
		}
		brains = p;
		brains.onEnter(0);
	}
	
	
	public void update(int delta){
		brains.onUpdate(delta);
		super.update(delta);
	}
	
}
