package cond;


import org.newdawn.slick.geom.Shape;

import core.ClientGameplayState;
import ents.BasicShip;

public class ShipTouch extends Condition {

	private Shape collider;
	
	public ShipTouch(Shape collider){
		this.collider = collider;
	}
	
	@Override
	public boolean updateMe() {
		return true;
	}

	@Override
	public boolean check(ClientGameplayState c, int delta) {
		for( BasicShip ship : c.getShips().values()){
			//Brute force collision check
			if (collider.intersects(ship.getCollider())){
				return true;
			}
		}
		return false;
	}

}
