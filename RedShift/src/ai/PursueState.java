package ai;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import core.GameplayState;

import ents.AIShip;
import ents.BasicShip;

public class PursueState extends AIState{

//	Params:
//		Distance from target: dP
//	Angle towards target: dA
		double dP, dA;
//		Tuneable constants:
//		Arc within which the ship consideres itself "pointed at" its target (and needs no correction): noise
		double noise = 0.25f;
//		Absolute longest range the ships will fire from: range
		double range  = 400;
//		How far from a perfect shot: miss
		float miss = 0.1f;
//		How wide is the definition of "pointing towards": pointing
		float pointing;
		BasicShip targ;
		GameplayState g;
		
	public PursueState(AIShip p, BasicShip target, GameplayState gs){
		ship = p;
		targ =target;
		g = gs;
	}
	
	public void onUpdate(int delta){
//		System.out.println(ship.getName()+"::Pursuing");
		dP = getDistance();
		
//		if( arc(noise, dA)){
//			if (dA > 0){
//				ship.rotateLeft(delta);
//			} else { //It never equals zero, honest
//				ship.rotateRight(delta);
//			}
//			if( arc( pointing, dA) ){
//				ship.moveForward(delta);
//			}
//			if( dP < range){
//				if( arc( miss, dA) ){
//					ship.tryShot();
//				}
//			}
//		}
		if(dP > 100){
			ship.moveForward(delta);
		}
		if(dP < range){
//			if(ship.tryShot()){
//				g.addShot(ship.getWeapon().makeShot());
//			}
		}
	}
	
	public void onEnter(){
		System.out.println(ship.getName()+"::Entering Pursue");
	}
	
	public void onLeave(){
		System.out.println(ship.getName()+"::Leaving Pursue");
	}
	
	public void onMessage(){
		
	}
	
	private boolean arc(double width, double difference){
		 return (-1 * width) > difference && difference > width;
	}
	
	private double getDistance(){
		Vector2f s = new Vector2f((float)ship.getX(), (float)ship.getY());
		Vector2f t = new Vector2f((float)targ.getX(), (float)targ.getY());
		Line dist = new Line(s, t);
		return (double)dist.length();
	}

}
