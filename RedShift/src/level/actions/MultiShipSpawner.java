package level.actions;

import java.awt.Point;
import java.util.HashMap;

import core.ClientGameplayState;
import ents.BasicShip;

/**
 * give this Action an array of Points and a ship hashmap, and it will those ships at the given
 *points when fired.
 * @author proohr
 *
 */
public class MultiShipSpawner extends BasicAction {

	private HashMap<String, BasicShip> shipsToSpawn;
	private Point[] points;
	
	/**
	 * ship order in the hashmap matters! its FIFO :P
	 * @param name
	 * @param x
	 * @param y
	 * @param locations
	 * @param ships
	 */
	public MultiShipSpawner(String name, float x ,float y, Point[] locations, HashMap<String, BasicShip> ships){
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
	
	@Override
	public void update(int delta, ClientGameplayState cgs){
		int i = 0;
		for(BasicShip ship : shipsToSpawn.values()){
				ship.setX(points[i].getX());
				ship.setY(points[i].getY());
				cgs.addShip(ship);
				i++;
		}

		setUpdate(false);
		setDone(true);
	}
	
}
