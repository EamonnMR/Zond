package ai;

import core.GameplayState;
import ents.AIShip;

public class IdleState extends AIState {

	public IdleState(AIShip p){
		ship = p;
	}
	
	public void onUpdate(int delta, GameplayState gs){
		
	}
	
	public void onEnter(int delta, GameplayState gs){
		System.out.println(ship.getName()+":: Entering Idle");
	}
	
	public void onLeave(int delta, GameplayState gs){
		System.out.println(ship.getName()+":: Leaving Idle");
		
	}
	
	public void onMessage(int delta, GameplayState gs){
		
	}
}
