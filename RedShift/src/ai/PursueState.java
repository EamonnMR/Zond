package ai;

import ents.BasicShip;

public class PursueState extends AIState{

//	Params:
//		Distance from target: dP
//	Angle towards target: dA
		double dP, dA;
//		Tuneable constants:
//		Arc within which the ship consideres itself "pointed at" its target (and needs no correction): noise
		double noise;
//		Absolute longest range the ships will fire from: range
		double range;
//		How far from a perfect shot: miss
		float miss = 0.1f;
//		How wide is the definition of "pointing towards": pointing
		float pointing;
	
	public PursueState(BasicShip p){
		ship = p;
	}
	void onUpdate(int delta){
		if( arc(noise, dA)){
			if (dA > 0){
				ship.rotateLeft(delta);
			} else { //It never equals zero, honest
				ship.rotateRight(delta);
			}
			if( arc( pointing, dA) ){
				ship.moveForward(delta);
			}
			if( dP < range){
				if( arc( miss, dA) ){
					ship.tryShot();
				}
			}
		}
	}
	
	void onEnter(){
		
	}
	
	void onLeave(){
		
	}
	
	void onMessage(){
		
	}
	
	private boolean arc(double width, double difference){
		 return (-1 * width) > difference && difference > width;
	}

}
