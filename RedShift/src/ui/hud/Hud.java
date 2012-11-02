package ui.hud;

import java.util.HashMap;

import level.BasicObjective;
import level.LevelDataModel;
import level.NavPoint;
import level.triggers.BasicTrigger;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import ui.UILib;
import core.ClientGameplayState;
import core.GameDatabase;
import core.PlayerClient;
import ents.BasicShip;

/**
 * Hud class: responsible for rendering all heads up info for the player
 * including radar pings, player ship information, nav points, minimap
 * 
 * @author Roohr
 * 
 */
public class Hud {

	private HudDataModel hdm;
	private UILib uiLib;
	Rectangle camBounds;
	private GameDatabase gdb;
	private Image hp_i,radar_i,engine_i,wep_i, on_i, off_i;
	float x, y;
	double hp, totalHP;
	private boolean googles = false; // bool for developer view mode
	private boolean boundsWarning = false; // warn the player they are leaving
	private boolean radarOn = false; // is radar active?
	private boolean showPoints = false;		//display navpoints
	private boolean showMission = false;	//show list of active objective
	private ClientGameplayState cgs;
	private PlayerClient pc;
	private SpriteSheetFont grnF;
	private SpriteSheetFont graF;
	private Color brightRed, brightBlue, brightYel;

	public Hud(PlayerClient cl, int camBoundsW, int camBoundsH, HudDataModel h, GameDatabase gdb) {
		uiLib = new UILib();
		camBounds = new Rectangle(1,1,camBoundsW,camBoundsH);
		hdm = h;
		pc = cl;
		hp = pc.getPlayShip().getHealth();
		totalHP = hp;
		configHud(pc.getPlayShip());
		this.gdb=gdb;
		
		hp_i = gdb.getIMG("hp_i");
		radar_i = gdb.getIMG("radar_i");
		engine_i = gdb.getIMG("engine_i");
		wep_i = gdb.getIMG("wep_i");
		on_i=gdb.getIMG("onBTN_n");
		off_i=gdb.getIMG("offBTN_n");
		
		grnF = gdb.getFont("green");
		graF = gdb.getFont("gray");
		brightRed = new Color(255,39,64);
		brightBlue = new Color(28, 87, 255);
		brightYel = new Color(242, 255, 28);
	}

	public void update(PlayerClient cl, ClientGameplayState cgs) {
		pc = cl;
		x = (float) pc.getPlayShip().getX();
		y = (float) pc.getPlayShip().getY();
		hp = pc.getPlayShip().getHealth();
		this.cgs = cgs;
	}

	/**
	 * big ugly method
	 * @param gfx Graphics
	 * @param gc GameContainer
	 * @param bl BasicLevel
	 * @param camX
	 * @param camY
	 */
	public void render(Graphics gfx, GameContainer gc, LevelDataModel ldm, int camX,
			int camY) {
		renderShipInfo(gfx,camX,camY);
		
		if(radarOn){
			shipRadarCheck(cgs, gfx, camX, camY);
			renderShipTags(cgs, gfx, camX, camY);
		}else{
		}
		
		renderPoints(ldm, gfx, camX,camY);
		
		if(showMission){
			renderObjectivesList(gfx);
		}
		
		if(showPoints){
			renderNavTags(ldm, gfx, camX, camY);
		}
		
		if (googles) {
			gfx.setColor(brightYel);
			devGoggles(gfx, gc, ldm, camX, camY);
		}
		if (boundsWarning) {
			graF.drawString(458, 627, "<==!WARNING!==>",brightRed);
			graF.drawString(400, 652, "<==!Leaving Mission Area!==>",brightRed);
		}

		// sanity check to make sure color is reset
		gfx.setColor(Color.white);
	}

	/**
	 * byzantine radar rendering!
	 * wait, the byzantine-ness moved to drawTargetBox; do not look EMR, you'll cry
	 * @param cgs
	 * @param gfx
	 * @param camX
	 * @param camY
	 */
	public void shipRadarCheck(ClientGameplayState cgs, Graphics gfx, int camX, int camY){
		for(BasicShip s : cgs.getShips().values()){
			if(!s.equals(pc.getPlayShip())){
				if(pc.getPlayShip().getRadarRadius().intersects(s.getCollider())||pc.getPlayShip().getRadarRadius().contains(s.getCollider())){
					drawTargetBox(s, 0, gfx, camX, camY);
				}
			}
		}
	}

	/**
	 * the mighty dev gog
	 * @param gfx Graphics
	 * @param gc GameContainer
	 * @param bl BasicLevel
	 * @param camX
	 * @param camY
	 */
	public void devGoggles(Graphics gfx, GameContainer gc, LevelDataModel ldm,
			int camX, int camY) {
		String x = String.valueOf(gc.getInput().getMouseX());
		gfx.drawString(x, 100, 10);
		x = String.valueOf(gc.getInput().getMouseY());
		gfx.drawString(x, 150, 10);
		
		if(radarOn){
			gfx.setColor(Color.blue);
			gfx.draw(offsetShape(pc.getPlayShip().getRadarRadius(), camX, camY));
			
		}
		gfx.draw(offsetShape(pc.getPlayShip().getCollider(), camX, camY));
		for (BasicTrigger trig : ldm.getTriggerMap().values()) {
			if(trig.getCollider()!=null){
				gfx.setColor(Color.gray);
				gfx.draw(offsetShape(trig.getCollider(), camX, camY));
				float tx = trig.getCollider().getCenterX() + camX;
				float ty = trig.getCollider().getCenterY() + camY;
				gfx.setColor(brightRed);
				gfx.drawString(trig.getName(), tx, ty);
			}
		}
		gfx.setColor(Color.yellow);
		for (BasicShip s : cgs.getShips().values()) {
			gfx.draw(offsetShape(s.getCollider(), camX, camY));
		}

	}

	/**
	 * a one time run thingie at CGS startup
	 * @param s
	 */
	public void configHud(BasicShip s) {
//		hdm.setShipName(pc.getPlayShip().getName());
//		int padding = 4;
//		
//		Rectangle r = new Rectangle(0,
//									0,
//									uiLib.getStringPixelWidth(hdm.getShipName())+padding,
//									25);
//		hdm.setShipName_rec(r);
//		hdm.getShipName_rec().setCenterX(hdm.getShipName_point_mod().x);
//		hdm.getShipName_rec().setCenterY(hdm.getShipName_point_mod().y);
//		
//		Rectangle q = new Rectangle(0, 
//									0, 
//									uiLib.getStringPixelWidth(hdm.getGunName())+padding,
//									25);
//		hdm.setGunName_rec(q);
//		hdm.getGunName_rec().setCenterX(hdm.getGunName_point_mod().x);
//		hdm.getGunName_rec().setCenterY(hdm.getGunName_point_mod().y);
//		
//		Rectangle t = new Rectangle(0, 
//				  					0, 
//				  					uiLib.getStringPixelWidth(hdm.getHealth())+padding,
//				  					25);
//		hdm.setHp_rec(t);
//		hdm.getHp_rec().setCenterX(hdm.getHp_point_mod().x);
//		hdm.getHp_rec().setCenterY(hdm.getHp_point_mod().y);
//		
//		Rectangle u = new Rectangle(0, 
//				  					0, 
//				  					uiLib.getStringPixelWidth(hdm.getEngName())+padding,
//				  					25);
//		hdm.setEngName_rec(u);
//		hdm.getEngName_rec().setCenterX(hdm.getEngName_point_mod().x);
//		hdm.getEngName_rec().setCenterY(hdm.getEngName_point_mod().y);
//		
//		Rectangle v = new Rectangle(0, 
//				  					0, 
//				  					uiLib.getStringPixelWidth(hdm.getRadar())+padding,
//				  					25);
//		hdm.setRadar_rec(v);
//		hdm.getRadar_rec().setCenterX(hdm.getRadar_point_mod().x);
//		hdm.getRadar_rec().setCenterY(hdm.getRadar_point_mod().y);
	}
	
	public void setDevGog(boolean b) {
		googles = b;
	}

	public boolean getDevGogState() {
		return googles;
	}

	public void setWarn(boolean b) {
		boundsWarning = b;
	}

	public boolean getRadarOn() {
		return radarOn;
	}

	public void setRadarOn(boolean radarOn) {
		this.radarOn = radarOn;
	}
	
	public void setShowNav(boolean p){
		this.showPoints = p;
	}
	
	public boolean getShowNav(){
		return this.showPoints;
	}

	public void checkHP(double hp, Graphics gfx) {
		if (hp <= totalHP / 2 && hp > totalHP/4) {
			graF.drawString(hdm.getHp_point_mod().x+hp_i.getWidth()/2+4, hdm.getHp_point_mod().y-8.5f, String.valueOf(hp), brightYel);
		} else if (hp <= totalHP / 4) {
			graF.drawString(hdm.getHp_point_mod().x+hp_i.getWidth()/2+4, hdm.getHp_point_mod().y-8.5f, String.valueOf(hp), brightRed);
		}else {
			graF.drawString(hdm.getHp_point_mod().x+hp_i.getWidth()/2+4, hdm.getHp_point_mod().y-8.5f, String.valueOf(hp), Color.green);
		}

	}

	public static Shape offsetShape(Shape s, int dx, int dy) {
		float x = s.getCenterX();
		float y = s.getCenterY();
		Shape toSender;
		toSender = s.transform(new Transform());
		toSender.setCenterX(x + dx);
		toSender.setCenterY(y + dy);
		return toSender;
	}
	
	/**
	 * draw a target box based ship size, and type
	 * dont look now, this method is written crap :/
	 * @param s
	 * @param type
	 */
	public void drawTargetBox(BasicShip s, int type, Graphics gfx, int camX, int camY){
		float w = s.getImg().getWidth();
		float h = s.getImg().getHeight();
		float x = (float)s.getX();
		float y = (float)s.getY();
		float lenX = w/3;
		float lenY = h/3;	
		Color col = Color.green;
		if(s.getName().equals("lunar")||s.getName().equals("gemini")||s.getName().equals("mercury")||s.getName().equals("skylab")){
			col= Color.green;
		}else if(s.getName().equals("vostok")||s.getName().equals("voskhod")||s.getName().equals("zond4")){
			col = brightRed;
		}
			gfx.setColor(col);
			gfx.drawLine((x-(w/2)-5)+camX, (y-(h/2)-5)+camY, ((x-(w/2)-5)+lenX)+camX, (y-(h/2)-5)+camY);
			gfx.drawLine((x-(w/2)-5)+camX, (y-(h/2)-5)+camY, (x-(w/2)-5)+camX, ((y-(h/2)-5)+lenY)+camY);
		
			gfx.drawLine((x+(w/2)+5)+camX, (y-(h/2)-5)+camY, ((x+(w/2)+5)-lenX)+camX, (y-(h/2)-5)+camY);
			gfx.drawLine((x+(w/2)+5)+camX, (y-(h/2)-5)+camY, (x+(w/2)+5)+camX, ((y-(h/2)-5)+lenY)+camY);
		
			gfx.drawLine((x-(w/2)-5)+camX, (y+(h/2)+5)+camY, ((x-(w/2)-5)+lenX)+camX, (y+(h/2)+5)+camY);
			gfx.drawLine((x-(w/2)-5)+camX, (y+(h/2)+5)+camY, (x-(w/2)-5)+camX, ((y+(h/2)+5)-lenY)+camY);
		
			gfx.drawLine((x+(w/2)+5)+camX, (y+(h/2)+5)+camY, ((x+(w/2)+5)-lenX)+camX, (y+(h/2)+5)+camY);
			gfx.drawLine((x+(w/2)+5)+camX, (y+(h/2)+5)+camY, (x+(w/2)+5)+camX, ((y+(h/2)+5)-lenY)+camY);
		
			
		int half = (s.getName().length()*8)/2;
		graF.drawString((x-half)+camX, (y+(h/2)+4)+camY, s.getName(), col);
		int shipX = (int)s.getX();
		graF.drawString((x-(w/2)-5)+camX, (y+(h/2)+20)+camY, String.valueOf(shipX), col);
		shipX = (int)s.getY();
		graF.drawString((x+(w/2)-5)+camX, (y+(h/2)+20)+camY,String.valueOf(shipX), col);
	}
	
	/**
	 * renders all ACTIVE NavPoints on the map
	 * @param bl BasicLevel
	 * @param gfx Graphics
	 * @param camX
	 * @param camY
	 */
	public void renderPoints(LevelDataModel ldm, Graphics gfx, int camX, int camY){
		gfx.setColor(brightYel);
		for(NavPoint p : ldm.getNavMap().values()){
			if(p.isActive()){
				int len = p.getName().length();
				int tenlen = len*8;
				grnF.drawString((p.getX()-(tenlen/2))+camX,  p.getY()+camY-5, p.getName(),brightYel);
				gfx.draw(new Circle(p.getX()+camX, p.getY()+camY, tenlen));
			}
		}
	}
	
	/**
	 * renders a 'tag' for an item on a given edge of the players screen
	 * the 'tag' tells the player what the item is, and its distance to the player
	 * @param bl
	 * @param gfx
	 * @param camX
	 * @param camY
	 */
	public void renderNavTags(LevelDataModel ldm, Graphics gfx,int camX, int camY){
		Vector2f ship = new Vector2f((float)pc.getPlayShip().getX(), (float)pc.getPlayShip().getY());
		for(NavPoint p : ldm.getNavMap().values()){
			if(p.isActive()){
				Vector2f pLoc = new Vector2f((p.getX()), (p.getY()));
				Line toTarg = new Line(ship, pLoc);
				int len = (int) toTarg.length();
				if (len > 600) {double angle = Math.atan2((ship.getY() - pLoc.getY()),(ship.getX() - pLoc.getX()));
					Vector2f point = cgs.circularFunction((float) angle, 350);;
					graF.drawString(point.getX(), point.getY(), p.getName(),brightYel);
					graF.drawString(point.getX(), point.getY() + 25,String.valueOf(len),brightYel);
				}
			}
		}
	}
	
	public void renderShipTags(ClientGameplayState cgs, Graphics gfx,int camX, int camY){
		Vector2f player = new Vector2f((float)pc.getPlayShip().getX(), (float)pc.getPlayShip().getY());
		for(BasicShip ship : cgs.getShips().values()){
			if(!ship.equals(cgs.getPlayerShip())){	
				Vector2f target = new Vector2f((float)(ship.getX()), (float)(ship.getY()));
				Line toTarg = new Line(player, target);
				int len = (int) toTarg.length();
				if (len < cgs.getPlayerShip().getRadarRadius().getRadius() && len > 600) {
					double angle = Math.atan2((player.getY() - target.getY()),(player.getX() - target.getX()));
					Vector2f point = cgs.circularFunction((float) angle, 150);
					if(ship.getFaction()==0){
						graF.drawString(point.getX(),  point.getY(), "!["+ship.getName()+"]!", brightRed);
						graF.drawString(point.getX(), point.getY() + 25, String.valueOf(len), brightRed);
					}else if(ship.getFaction()==1){
						graF.drawString(point.getX(),  point.getY(), "["+ship.getName()+"]", brightBlue);
						graF.drawString(point.getX(), point.getY() + 25, String.valueOf(len), brightBlue);
					}
				}
			}	
		}
	}
	/**
	 * displays the player's ship info images 
	 * @param gfx
	 * @param camX
	 * @param camY
	 */
	public void renderShipInfo(Graphics gfx, int camX, int camY) {
		gfx.setColor(Color.green);
		
		//name
		int nameW = pc.getPlayShip().getEngine().getName().length()*12;
		grnF.drawString(hdm.getShipName_point_mod().x-nameW/2, hdm.getShipName_point_mod().y-8.5f, pc.getPlayShip().getName());
		
		//weapon
		uiLib.drawImageCenteredOnPoint(gfx, wep_i, hdm.getGunName_point_mod());
		grnF.drawString(hdm.getGunName_point_mod().x+wep_i.getWidth()/2+4, hdm.getGunName_point_mod().y-8.5f, pc.getPlayShip().getWeapon().getName());
		
		//engine
		uiLib.drawImageCenteredOnPoint(gfx, engine_i, hdm.getEngName_point_mod());
		grnF.drawString(hdm.getEngName_point_mod().x+engine_i.getWidth()/2+4, hdm.getEngName_point_mod().y-8.5f, pc.getPlayShip().getEngine().getName());
		
		//health
		uiLib.drawImageCenteredOnPoint(gfx, hp_i, hdm.getHp_point_mod());
		checkHP(hp, gfx);
		
		
		//radar
		uiLib.drawImageCenteredOnPoint(gfx, radar_i, hdm.getRadar_point_mod());
		if(radarOn){
			uiLib.drawImageNextToImage(gfx, radar_i, on_i, hdm.getRadar_point_mod(), 1, 4);

		}else{
			uiLib.drawImageNextToImage(gfx, radar_i, off_i, hdm.getRadar_point_mod(), 1, 4);
		}

	}
	
	
	/**
	 * draw objectives list on-screen
	 * @param gfx
	 */
	private void renderObjectivesList(Graphics gfx) {
		HashMap<String, BasicObjective> objs = cgs.getLevel().getObjectives();
//		int x = 0, y = 0;
		for(BasicObjective obj : objs.values()){
//			String msg = obj.getBlurb();
			if(!obj.getComplete()){
				gfx.setColor(Color.green);
//				gfx.drawString(msg, x, y)
			}else if(obj.getComplete()){
				gfx.setColor(Color.gray);
			}
			
		}
	}
	
	
}
