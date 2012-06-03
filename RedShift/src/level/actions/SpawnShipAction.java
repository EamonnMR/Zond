package level.actions;

import org.newdawn.slick.Graphics;

import level.BasicAction;
import ents.BasicShip;

/**
 * Spawns a Ship
 * @author Roohr
 *
 */
public class SpawnShipAction extends BasicAction {

	private float x,y;
	private BasicShip ship;
	
	public SpawnShipAction(BasicShip ship, float x, float y){
		this.x = x;
		this.y = y;
		this.ship = ship;
	}
	
	@Override
	public void ini(){
		ship.setX(x);
		ship.setY(y);
		this.setIni(false);
		this.setUpdate(true);
	}
	
	@Override 
	public void update(int delta){
		this.setUpdate(false);
		this.setDone(true);

	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
}
