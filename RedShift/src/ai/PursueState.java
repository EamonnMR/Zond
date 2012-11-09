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
		double distToTarg, angleOfTarg;
//		Tuneable constants:
//		Arc within which the ship consideres itself "pointed at" its target (and needs no correction): noise
		double margin = 0.2f;
//		Absolute longest range the ships will fire from: range
		double engageRange  = 400;
//		How far from a perfect shot: miss
		float miss = 0.1f;
//		How wide is the definition of "pointing towards": pointing
		float pointing = 0.50f;
		BasicShip targ;
		GameplayState g;
		
	public PursueState(AIShip p, BasicShip target, GameplayState gs){
		ship = p;
		targ =target;
		g = gs;
	}
	
	public void onUpdate(int delta){
//		System.out.println(ship.getName()+"::Pursuing");
		
		//algo
			//draw line from ship to target
			Vector2f s = new Vector2f((float)ship.getX(), (float)ship.getY());
			Vector2f t = new Vector2f((float)targ.getX(), (float)targ.getY());
			Line dist = new Line(s, t);	
			distToTarg = dist.length();
			
			//see if line is in ships angle
			double angle = Math.atan2((ship.getY() - targ.getY()),(ship.getX() - targ.getX()));
			
			//if not bring angle to line
			if(angle>ship.getImg().getRotation()+margin){
				ship.rotateLeft(delta);
			}else if(angle<ship.getImg().getRotation()-margin){
				ship.rotateLeft(delta);
			}//if angle is good, but out of range, get into range
			else if(angle<=ship.getImg().getRotation()+margin || angle>=ship.getImg().getRotation()-margin ){
				if(distToTarg > engageRange){
					ship.moveForward(delta);
				}else if(distToTarg <=engageRange){
					if(ship.tryShot()){
						g.addShot(ship.getWeapon().makeShot());
					}
				}
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


}
