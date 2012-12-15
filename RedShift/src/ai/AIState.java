package ai;

import core.GameplayState;
import ents.AIShip;


public abstract class AIState {
	
	AIShip ship;
	
	public void onEnter(int delta, GameplayState g){};
	public void onLeave(int delta, GameplayState g){};
	public void onUpdate(int delta, GameplayState g){};
	public void onMessage(int delta, GameplayState g){};
}
