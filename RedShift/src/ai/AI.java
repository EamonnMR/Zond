package ai;

import ents.BasicShip;

//following this
//http://sourcemaking.com/design_patterns/state/java/5
//and this
//http://www.codeproject.com/Articles/14840/Artificial-Intelligence-in-Games
public class AI {
	
	private AIState[] states = {new MoveState(), new PivotState(), new AttackState(), new TargetState()};
	private int[][] transition = {{0,1,2,3}, {0,1,2,3}, {0,1,2,3}};
	private int current = 0;
	private BasicShip ship;
	private BasicShip target;
	
	private void next(int msg){
		current = transition[current][msg];
	}
	
	public void move(BasicShip s,BasicShip t){
		states[current].move(s,t);
		next(0);
	}
	public void pivot(BasicShip s,BasicShip t){
		states[current].pivot(s,t);
		next(1);
	}
	public void attack(BasicShip s,BasicShip t){
		states[current].attack(s,t);
		next(2);
	}
	public void target(BasicShip s,BasicShip t){
		states[current].target(s,t);
		next(3);
	}
}
