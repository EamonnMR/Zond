package ai;

import ents.BasicShip;


public abstract class AIState {
	
	protected BasicShip ship;
	
	void onEnter(){}
	void onLeave(){}
	void onUpdate(){}
	void onMessage(){}
}
