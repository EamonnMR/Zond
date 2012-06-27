package ui.hud;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import core.ClientGameplayState;
import core.PlayerClient;
import ents.BasicShip;

/**
 * basic hud class, gets info from a player client and displays it at the bottom of the screen
 * @author Roohr
 *
 */
public class Hud {
	
	String shipName, gunName, engName;
	float x,y;
	boolean googles = false;		//bool for developer view mode
	Font hudFont;
	public Hud(PlayerClient cl){
		shipName = cl.getPlayShip().getName();
		gunName = cl.getPlayShip().getWeapon().getName();
		engName = cl.getPlayShip().getEngine().getName();
	}
	
	
	public void update(PlayerClient cl, ClientGameplayState cgs){
		x = (float)cl.getPlayShip().getX();
		y = (float)cl.getPlayShip().getY();
	}
	
	public void render(Graphics gfx){
		gfx.drawString("Ship: "+shipName, 165, 700);
		gfx.drawString("Gun: "+gunName, 165, 720);
		gfx.drawString("Engine: "+engName, 165, 740);
		if(googles){
			
		}
	}
	
	public void shipRadarCheck(ClientGameplayState cgs){
		for(BasicShip s : cgs.getShips().values()){
			//if ship collider intersects player radar draw it
		}
	}
	
	public void setDevGog(boolean b){
		googles  = b;
	}
	
	public boolean getDevGogState(){
		return googles;
	}
}
