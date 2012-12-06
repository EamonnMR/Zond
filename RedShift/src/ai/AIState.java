package ai;

import ents.BasicShip;


public abstract class AIState {
	
	BasicShip ship;
	
	public void onEnter(int delta){};
	public void onLeave(int delta){};
	public void onUpdate(int delta){};
	public void onMessage(int delta){};
}
