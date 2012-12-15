package ents;

import core.GameplayState;
import ai.AIState;

public class AIShip extends BasicShip {

	private AIState brains;
	
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
	
}
