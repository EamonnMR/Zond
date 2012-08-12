package level.triggers;

import level.TriggerTypes;
import core.ClientGameplayState;
import ents.BasicShip;

public class SpawnShip extends BasicTrigger{

	private BasicShip ship;
	
	public SpawnShip(TriggerTypes trig, BasicShip s) {
		super(trig);
		ship = s;
	}
	
	@Override
	public void go(ClientGameplayState cgs){
		ship.setX(getX());
		ship.setY(getY());
		cgs.addShip(ship);
	}
	
	public BasicShip getShip(){
		return ship;
	}

}
