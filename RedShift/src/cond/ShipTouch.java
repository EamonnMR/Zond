package cond;


import org.newdawn.slick.geom.Shape;

import core.GameplayState;
import ents.BasicShip;

public class ShipTouch extends Condition {

	private Shape collider;
	
	public ShipTouch(Shape collider, String target){
		this.target = target;
		this.collider = collider;
	}
	
	public String toString(){
		return "COND: ShitTouch collider = " + collider.toString();
	}
	
	@Override
	public boolean updateMe() {
		return true;
	}

	@Override
	public boolean check(GameplayState c, int delta) {
		for( BasicShip ship : c.getShips().values()){
			//Brute force collision check
			if (collider.intersects(ship.getCollider())){
				return true;
			}
		}
		return false;
	}

}
