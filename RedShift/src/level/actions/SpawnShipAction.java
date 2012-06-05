package level.actions;

import level.BasicAction;
import core.ClientGameplayState;
import ents.BasicShip;
import ents.EntityFactory;


/**
 * Spawns a given ship and adds it to the ship hashmap
 * @author Roohr
 * @version 1.0
 */
public class SpawnShipAction extends BasicAction{

	private float x,y;
	private BasicShip ship;
	private EntityFactory entFac;
	private String sname,wep,eng;
	
	public SpawnShipAction(String name, float x, float y, String ship, String wep, String eng){
		this.setName(name);
		this.x = x;
		this.y = y;
		this.setIni(true);
		this.setUpdate(false);
		this.setDone(false);
		this.wep = wep;
		this.eng = eng;
		this.sname = ship;
	}
	
	@Override
	public void ini(ClientGameplayState cgs){
		setIni(false);
		setUpdate(true);
		this.entFac = cgs.getEntFac();
		ship = entFac.buildShip(sname, wep,eng);
	}
	
	@Override
	public void update(int delta, ClientGameplayState cgs){
		
		ship.ini(x, y, 0.0f);
		cgs.addShip(ship);
		setUpdate(false);
		setDone(true);
	}
}
