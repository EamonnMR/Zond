package level.triggers;

import level.TriggerTypes;
import core.GameplayState;
import ents.BasicShip;
import ents.ShipDesc;

public class SpawnShip extends BasicTrigger{

	private ShipDesc ship;
	
	public SpawnShip(TriggerTypes trig, BasicShip s) {
		super(trig);
	}
	
	public SpawnShip(){}
	
	@Override
	public void go(GameplayState cgs){
		cgs.addShip(cgs.getEntFac().shipFromDesc(ship));
	}
	
	public void setShip(ShipDesc s){
		ship = s;
	}
	
	public ShipDesc getShip(){
		return ship;
	}

}
