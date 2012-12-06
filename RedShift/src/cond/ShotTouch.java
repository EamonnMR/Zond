package cond;


import org.newdawn.slick.geom.Shape;

import core.GameplayState;
import ents.BasicShot;

public class ShotTouch extends Condition {

	private Shape collider;
	
	public ShotTouch(Shape collider, String target){
		this.target = target;
		this.collider = collider;
	}
	
	public String toString(){
		return "COND: ShotTouch collider = " + collider.toString();
	}
	@Override
	public boolean updateMe() {
		return true;
	}

	@Override
	public boolean check(GameplayState c, int delta) {
		for( BasicShot shot : c.getShots().values()){
			//Brute force collision check
			if (collider.intersects(shot.getCollider())){
				return true;
			}
		}
		return false;
	}

}
