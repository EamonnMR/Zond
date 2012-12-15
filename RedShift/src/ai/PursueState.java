package ai;

import java.awt.Point;

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
		double margin = 0.1f;
//		Absolute longest range the ships will fire from: range
		double engageRange  = 400;
		double sightRange = 800; 
//		How far from a perfect shot: miss
		float miss = 0.1f;
//		How wide is the definition of "pointing towards": pointing
		float pointing = 0.50f;
		BasicShip targ;
		GameplayState g;
		static double TWOPI = Math.PI * 2;
		
	public PursueState(AIShip p, BasicShip target){
		ship = p;
		targ =target;
	}
	
	public PursueState(AIShip p){
		ship = p;
	}
	public PursueState(){}
	
	public void onUpdate(int delta, GameplayState gs){
//		System.out.println(ship.getName()+"::Pursuing");
		
		//algo
			//draw line from ship to target
			distToTarg = distToTarget(ship, targ);

			//see if line is in ships angle
			double targetAngle = Math.atan2((ship.getY() - targ.getY()),(ship.getX() - targ.getX()));
			//Ungodly hack to reverse direction of pointing:
			//It subs 180 (PI) from the angle to make it point the other way.

			double shipAngle = ship.getRot();
			
			//ASS
			//We *really* need to refactor this, just to make sure *we* know what it does.
			double diff = calcDiff(shipAngle, targetAngle);
			
			//if not bring angle to line
			if (diff < margin) {
				ship.rotateRight(delta);
			} else if (diff > -margin) {
				ship.rotateLeft(delta);
			}

			//if angle is good, but out of range, get into range
			if(!(targ.isDead())){
				if(Math.abs(diff) < miss){
					if(distToTarg > engageRange){
						ship.moveForward(delta);
					}else if(distToTarg <=engageRange){
						if(ship.tryShot()){
							GameplayState.getME().addShot(ship.getWeapon().makeShot(gs.getSFXVol()));
						}
					}else if(!(ship.getRadarRadius().intersects(targ.getCollider()))){
						ship.setState(new ScanState(ship), gs);
					}
				}
			}else{
				ship.setState(new ScanState(ship), gs);
			}
	}
	public void onEnter(int delta, GameplayState gs){
		System.out.println(ship.getName()+":: targetting "+targ.getName());
	}
	
	public void onLeave(int delta, GameplayState gs){
		System.out.println(ship.getName()+"::Leaving Pursue, "+targ.getName()+" must be dead...");
		targ = null;
	}
	
	public void onMessage(){
		
	}
	
	private double calcDiff(double sAngle, double targAngle) {
		double diff = targAngle - sAngle;
		return Math.signum(diff) * (TWOPI % Math.abs(diff));
	}

	
	private boolean arc(double width, double difference){
		 return (-1 * width) > difference && difference > width;
	}
	
	private Vector2f circularFunction(float angle, double rad){
//	       return new Vector2f((float) (Math.cos(angle+Math.PI) * rad + ship.getX()), (float)(Math.sin(angle+Math.PI) * rad + ship.getY()));
	       return new Vector2f((float) (Math.cos(angle+Math.PI) * rad + ship.getX()), (float)(Math.sin(angle+Math.PI) * rad + ship.getY()));
	}
	
	private double distToTarget(BasicShip ship, BasicShip targ){
		double distToTarg = 0.0;
		Vector2f s = new Vector2f((float)ship.getX(), (float)ship.getY());
		Vector2f t = new Vector2f((float)targ.getX(), (float)targ.getY());
		Line dist = new Line(s, t);
		distToTarg = dist.length();
		return distToTarg;
	}
}
