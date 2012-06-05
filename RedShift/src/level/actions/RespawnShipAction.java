package level.actions;

import level.BasicAction;
import core.ClientGameplayState;
import ents.BasicShip;

/**
 * respawns a ship after a given amount of time
 * @author proohr
 * @version 1.1
 */

public class RespawnShipAction extends BasicAction{

	private BasicShip ship;
	private int timer;
	private int lifetime;
	private float x,y;
	
	public RespawnShipAction(BasicShip s, float x, float y, int life){
		
		this.ship = s;
		this.x = x;
		this.y = y;
		this.lifetime = life;
		this.timer = 0;
		setIni(true);
		setUpdate(false);
		setDone(false);
	}
	
	@Override
	public void ini(ClientGameplayState cgs){
//		ship = cgs.getEntFac().buildShip(ship., gunPointer, engPointer)
		
		
		setIni(false);
		setUpdate(true);
	}
	
	@Override
	public void update(int delta, ClientGameplayState cgs){
		timer += delta;
		if(timer <= lifetime){
			
			
		}else{
			setUpdate(false);
			setDone(true);
		}
	}
}
