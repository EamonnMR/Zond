package ui.hud;

import level.BasicLevel;
import level.BasicTrigger;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import core.ClientGameplayState;
import core.PlayerClient;

/**
 * Hud class: responsible for rendering all heads up info for the player including
 * radar pings, player ship information, nav points, minimap
 * @author Roohr
 *
 */
public class Hud {
	
	String shipName, gunName, engName;
	float x,y;
	double hp, totalHP;
	boolean googles = false;				//bool for developer view mode
	boolean boundsWarning = false;			//warn the player they are leaving
	boolean shipHPCaution = false;			//if health is below half - make hp yellow, show caution
	boolean shipHPWarning = false;			//if health is below 1/4  - make hp red, show warning
	boolean radarOn = false;				//is radar active?
	Font hudFont;
	PlayerClient pc;
	public Hud(PlayerClient cl){
		pc = cl;
		totalHP = pc.getPlayShip().getHealth();
		shipName = pc.getPlayShip().getName();
		gunName = pc.getPlayShip().getWeapon().getName();
		engName = pc.getPlayShip().getEngine().getName();
	}
	
	
	public void update(PlayerClient cl, ClientGameplayState cgs){
		pc = cl;
		x = (float)pc.getPlayShip().getX();
		y = (float)pc.getPlayShip().getY();
		hp = pc.getPlayShip().getHealth();
	}
	
	public void render(Graphics gfx, GameContainer gc, BasicLevel bl, int camX, int camY){
		gfx.setColor(Color.green);
		
		gfx.drawString(shipName, 480, 686);
		gfx.draw(new Rectangle(477,683,(shipName.length()*10)+2,25));
		
		gfx.drawString("["+gunName+"]", 590, 709);
		gfx.draw(new Rectangle(587,703,(gunName.length()*10)+22,25));
		
		gfx.drawString("["+engName+"]", 590, 732);
		gfx.draw(new Rectangle(587,729,(engName.length()*10)+22,25));
		
		checkHP(hp, gfx);
		gfx.drawString("[HP] "+hp, 360, 732);
		gfx.draw(new Rectangle(357,729,(4*10)+2,25));
		
		if(shipHPCaution){
			
		}else if(shipHPWarning){
			
		}
		
		if(googles){
			gfx.setColor(Color.yellow);
			devGoggles(gfx, gc, bl, camX, camY);
		}
		if(boundsWarning){
			gfx.setColor(Color.red);
			String x = "<==!WARNING!==>";
			gfx.drawString(x, 458,640);
			x = "<==Leaving Mission Area==>";
			gfx.drawString(x, 400,663);
		}
		
		//sanity check to make sure color is reset
		gfx.setColor(Color.white);
	}
	
	public void shipRadarCheck(ClientGameplayState cgs){

	}
	
	public void devGoggles(Graphics gfx, GameContainer gc, BasicLevel bl, int camX, int camY){
		String x = String.valueOf(gc.getInput().getMouseX());
		gfx.drawString(x, 100, 10);
		x = String.valueOf(gc.getInput().getMouseY());
		gfx.drawString(x, 150, 10);
		gfx.draw(offsetShape(pc.getPlayShip().getCollider(), camX, camY));
		for(BasicTrigger trig : bl.getLevelTriggerMap().values()){
			
			gfx.draw(offsetShape(trig.getCollider(), camX, camY));
			float tx = trig.getCollider().getCenterX()+camX;
			float ty = trig.getCollider().getCenterY()+camY;

			gfx.drawString(trig.getName(), tx, ty);
		}
	}
	
	public void setDevGog(boolean b){
		googles  = b;
	}
	
	public boolean getDevGogState(){
		return googles;
	}
	
	public void setWarn(boolean b){
		boundsWarning = b;
	}
	
	public void checkHP(double hp, Graphics gfx){
		if(hp <= totalHP/2 ){
			gfx.setColor(Color.yellow);
			shipHPCaution = true;
			shipHPWarning = false;
		}else if(hp <= totalHP/4){
			gfx.setColor(Color.red);
			shipHPCaution = false;
			shipHPWarning = true;
		}
	}
	
	public static Shape offsetShape(Shape s, int dx, int dy){
	    float  x = s.getCenterX();
	    float y = s.getCenterY();
	    Shape toSender;
	    toSender = s.transform(new Transform() );
	    toSender.setCenterX( x + dx);
	    toSender.setCenterY( y + dy);
	    return toSender;
	}
}
