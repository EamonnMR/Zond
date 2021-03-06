package ai;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import core.GameplayState;
import ents.AIShip;
import ents.BasicShip;

public class ScanState extends AIState {

	BasicShip targ;
	double engageRange  = 400;
	double sightRange = 800; 
	
	public ScanState(AIShip s){
		ship = s;
	}
	
	public void onUpdate(int delta, GameplayState gs){
		for (BasicShip s : gs.getShips().values()) {
			if (s.getFaction() != ship.getFaction()) {
				if (!s.equals(ship)) { // don't target self, though this may be hilarious
					double range = distToTarget(ship, s);
					if(range > ship.getSightRange()){
						if(ship.getRadarShape().intersects(s.getRadarShape())){
							targ = s;
							ship.setState(new PursueState(ship, targ), gs);
						}
					}else if (range <= ship.getSightRange()){
						targ = s;
						ship.setState(new PursueState(ship, targ), gs);
					}
				}
			}
		}
	}
	
	public void onEnter(int delta, GameplayState gs){
		System.out.println(ship.getName()+":: Entering Scanner Mode");
		ship.setRadarState(true);
	}
	
	public void onLeave(int delta, GameplayState gs){
		System.out.println(ship.getName()+"::Target found!?");
		targ = null;
	}
	
	public void onMessage(int delta, GameplayState gs){
		
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
