package level.actions;

import java.util.HashMap;
import java.util.Map;

import core.ClientGameplayState;

import ents.BasicShip;


public class MultiShipSpawner extends BasicAction {

	private HashMap<String, BasicShip> shipsToSpawn;
	private HashMap<Double, Double> points;
	
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param locations
	 * @param ships
	 */
	public MultiShipSpawner(String name, float x ,float y, HashMap<Double, Double> locations, HashMap<String, BasicShip> ships){
		setName(name);
		shipsToSpawn =  ships;
		points = locations;
		this.setIni(true);
		this.setUpdate(false);
		this.setDone(false);
	}
	
	@Override
	public void ini(ClientGameplayState cgs){
		setIni(false);
		setUpdate(true);
	}
	
	//FIXME: this method works, but not correctly, take a look sometime soon.
	@Override
	public void update(int delta, ClientGameplayState cgs){
		for(BasicShip ship : shipsToSpawn.values()){
			for(Map.Entry<Double, Double> d : points.entrySet()){
				ship.setX(d.getKey());
				ship.setY(d.getValue());
				cgs.addShip(ship);
			}
		}

		setUpdate(false);
		setDone(true);
	}
	
}
