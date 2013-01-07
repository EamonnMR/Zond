package ai;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import core.GameplayState;
import ents.AIShip;
import ents.BasicShip;

public class PursueState extends AIState{

//	Params:
		public double distToTarg, 				//distance between the aiship and its target
						targetAngle, 			//the angle of the target relative to the aiship's X/Y
						angleDifference, 		//the difference between the two angles
						shipAngle;				//the angle of the aiship
//		Tuneable constants:
//		Arc within which the ship considers itself "pointed at" its target (and needs no correction): noise
		double margin = 0.1f;
//		Absolute longest range the ships will fire from: range
//		How far from a perfect shot: miss
		float miss = 0.5f;
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

		//try again, bragg!
		Vector2f shipVec = new Vector2f((float)ship.getX(),(float)ship.getY());
		Vector2f targVec = new Vector2f((float)targ.getX(),(float)targ.getY());
		
		Line lineToTarg = new Line(shipVec,targVec);
		distToTarg = lineToTarg.length();
		targetAngle = getAngle((double)shipVec.getX(), 
				(double)shipVec.getY(), 
				(double)targVec.getX(), 
				(double)targVec.getY());
		shipAngle = ship.getRot();
		//checks
		//--targ not dead
		//--targ in range
			//yes->check angle
			//no->get in range
		
		//--targ in angle
			//yes->make shot
			//no->change angle
		
		if(!(targ.isDead())){
			//if out of range, maneuver to attack range
			if((distToTarg > ship.getAttackRange()) && (distToTarg > 200)){
				
				if (shipAngle < targetAngle) {
					ship.rotateRight(delta);
				} else if (shipAngle > targetAngle) {
					ship.rotateLeft(delta);
				}
				ship.moveForward(delta);
				
			}else if(distToTarg <= ship.getAttackRange()){
				if (shipAngle < targetAngle) {
					ship.rotateRight(delta);
				} else if (shipAngle > targetAngle) {
					ship.rotateLeft(delta);
				}
			//target in range
				if((shipAngle > targetAngle-miss)&&(shipAngle < targetAngle+miss)){
					if (ship.tryShot()) {
						GameplayState.getME().addShot(
								ship.getWeapon().makeShot(gs.getSFXVol()));
					}
				}
			//TARGET TOO CLOSE! D:
			}else if(distToTarg <= 200){
				if (shipAngle < targetAngle) {
					ship.rotateRight(delta);
				} else if (shipAngle > targetAngle) {
					ship.rotateLeft(delta);
				}
				ship.moveBackward(delta);
			//target in range
				if((shipAngle > targetAngle-miss)&&(shipAngle < targetAngle+miss)){
					if (ship.tryShot()) {
						GameplayState.getME().addShot(
								ship.getWeapon().makeShot(gs.getSFXVol()));
					}
				}
			}else if (ship.getHealth()<=ship.getHealth()/4){
			for (BasicShip s : gs.getShips().values()) {
				if (!(s.equals(ship))) {
					if (s.getFaction() == ship.getFaction()) {
						Vector2f sVec = new Vector2f((float) targ.getX(),
								(float) targ.getY());
						Vector2f fVec = new Vector2f((float) s.getX(),
								(float) s.getY());

						Line lineToFriend = new Line(sVec, fVec);
						double distToF = lineToFriend.length();
						if (distToF >= 1000) {
							double fAngle = getAngle((double) sVec.getX(),
									(double) sVec.getY(),
									(double) fVec.getX(),
									(double) fVec.getY());
							if (shipAngle < fAngle) {
								ship.rotateRight(delta);
							} else if (shipAngle > fAngle) {
								ship.rotateLeft(delta);
							}
							ship.moveForward(delta);
						}
					}
				}

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
	
	/*
	 * gets the angle to the target
	 */
	public float getAngle(double x1,double y1,double x2, double y2){
		float angle = (float)Math.atan2((y2-y1), 
								(x2-x1));
		return angle;
	}
	
	private double calcDiff(double sAngle, double targAngle) {
		
		if(targAngle > sAngle){
			double diff = targAngle - sAngle;
			return Math.signum(diff) * (TWOPI % Math.abs(diff));
		}else if(targAngle < sAngle){
			double diff = sAngle-  targAngle;
			return Math.signum(diff) * (TWOPI % Math.abs(diff));
		}
		
		return 0.0;
	}

	private boolean arc(double width, double difference){
		 return (-1 * width) > difference && difference > width;
	}
}
