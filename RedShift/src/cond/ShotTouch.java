package cond;


import org.newdawn.slick.geom.Shape;

import core.ClientGameplayState;
import ents.BasicShot;

public class ShotTouch extends Condition {

	private Shape collider;
	
	public ShotTouch(Shape collider){
		this.collider = collider;
	}
	
	@Override
	public boolean updateMe() {
		return true;
	}

	@Override
	public boolean check(ClientGameplayState c, int delta) {
		for( BasicShot shot : c.getShots().values()){
			//Brute force collision check
			if (collider.intersects(shot.getCollider())){
				return true;
			}
		}
		return false;
	}

}
