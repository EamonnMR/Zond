package ui.hud;

import java.util.HashMap;

import level.LevelDataModel;
import level.NavPoint;
import level.Objective;
import level.triggers.BasicTrigger;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import core.ClientGameplayState;
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

	String shipName, gunName, engName;
	Rectangle camBounds;
	float x, y;
	double hp, totalHP;
	boolean googles = false; // bool for developer view mode
	boolean boundsWarning = false; // warn the player they are leaving
	boolean shipHPCaution = false; // if health is below half - make hp yellow,
									// show caution
	boolean shipHPWarning = false; // if health is below 1/4 - make hp red, show
									// warning
	boolean radarOn = false; // is radar active?
	
	boolean showMission = false;	//show list of active objectives
	HashMap<Boolean, BasicShip> detected;

	ClientGameplayState cgs;
	Font hudFont;
	PlayerClient pc;

	public Hud(PlayerClient cl, int camBoundsW, int camBoundsH) {
		pc = cl;
		totalHP = pc.getPlayShip().getHealth();
		shipName = pc.getPlayShip().getName();
		gunName = pc.getPlayShip().getWeapon().getName();
		engName = pc.getPlayShip().getEngine().getName();
		detected = new HashMap<Boolean, BasicShip>();
		camBounds = new Rectangle(1,1,camBoundsW,camBoundsH);
	}

	public void update(PlayerClient cl, ClientGameplayState cgs) {
		pc = cl;
		x = (float) pc.getPlayShip().getX();
		y = (float) pc.getPlayShip().getY();
		hp = pc.getPlayShip().getHealth();
		this.cgs = cgs;
		
		//update 
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
		if(radarOn){
			shipRadarCheck(cgs, gfx, camX, camY);
		}
		gfx.setColor(Color.green);
		gfx.drawString(shipName, 480, 680);
		gfx.draw(new Rectangle(477, 677, (shipName.length() * 10) + 2, 25));

		gfx.drawString("[WEAPON] "+gunName, 557, 706);
		gfx.draw(new Rectangle(555, 703, 80, 25));

		gfx.drawString("[ENGINE] "+engName, 590, 732);
		gfx.draw(new Rectangle(588, 729, 80, 25));

		checkHP(hp, gfx);
		gfx.drawString("[HP] " + hp, 396, 707);
		gfx.draw(new Rectangle(393, 703, 45, 25));

		if(showMission){
			renderObjectivesList(gfx);
		}
		
		renderRadarTags(ldm, gfx, camX, camY);
		
		if(radarOn){
			gfx.drawString("[RADAR] ON", 360, 732);
		}else{
			gfx.drawString("[RADAR] OFF", 360, 732);
		}
		gfx.draw(new Rectangle(357, 729, 74, 25));

		renderPoints(ldm, gfx, camX,camY);
		if (googles) {
			gfx.setColor(Color.yellow);
			devGoggles(gfx, gc, ldm, camX, camY);
		}
		if (boundsWarning) {
			gfx.setColor(Color.red);
			String x = "<==!WARNING!==>";
			gfx.drawString(x, 458, 627);
			x = "<==!Leaving Mission Area!==>";
			gfx.drawString(x, 400, 652);
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
			gfx.setColor(Color.gray);
			gfx.draw(offsetShape(trig.getCollider(), camX, camY));
			float tx = trig.getCollider().getCenterX() + camX;
			float ty = trig.getCollider().getCenterY() + camY;
			gfx.setColor(Color.red);
			gfx.drawString(trig.getName(), tx, ty);
		}
		gfx.setColor(Color.yellow);
		for (BasicShip s : cgs.getShips().values()) {
			gfx.draw(offsetShape(s.getCollider(), camX, camY));
		}

		gfx.draw(camBounds);
		gfx.setColor(Color.white);

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

	public void checkHP(double hp, Graphics gfx) {
		if (hp <= totalHP / 2) {
			gfx.setColor(Color.yellow);
			shipHPCaution = true;
			shipHPWarning = false;
		} else if (hp <= totalHP / 4) {
			gfx.setColor(Color.red);
			shipHPCaution = false;
			shipHPWarning = true;
		}
		gfx.setColor(Color.green);
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
		
		if(s.getName().equals("lunar")||s.getName().equals("gemini")||s.getName().equals("mercury")||s.getName().equals("skylab")){
			gfx.setColor(Color.green);
		}else if(s.getName().equals("vostok")||s.getName().equals("voskhod")||s.getName().equals("zond4")){
			gfx.setColor(Color.red);
		}
			gfx.drawLine((x-(w/2)-5)+camX, (y-(h/2)-5)+camY, ((x-(w/2)-5)+lenX)+camX, (y-(h/2)-5)+camY);
			gfx.drawLine((x-(w/2)-5)+camX, (y-(h/2)-5)+camY, (x-(w/2)-5)+camX, ((y-(h/2)-5)+lenY)+camY);
		
			gfx.drawLine((x+(w/2)+5)+camX, (y-(h/2)-5)+camY, ((x+(w/2)+5)-lenX)+camX, (y-(h/2)-5)+camY);
			gfx.drawLine((x+(w/2)+5)+camX, (y-(h/2)-5)+camY, (x+(w/2)+5)+camX, ((y-(h/2)-5)+lenY)+camY);
		
			gfx.drawLine((x-(w/2)-5)+camX, (y+(h/2)+5)+camY, ((x-(w/2)-5)+lenX)+camX, (y+(h/2)+5)+camY);
			gfx.drawLine((x-(w/2)-5)+camX, (y+(h/2)+5)+camY, (x-(w/2)-5)+camX, ((y+(h/2)+5)-lenY)+camY);
		
			gfx.drawLine((x+(w/2)+5)+camX, (y+(h/2)+5)+camY, ((x+(w/2)+5)-lenX)+camX, (y+(h/2)+5)+camY);
			gfx.drawLine((x+(w/2)+5)+camX, (y+(h/2)+5)+camY, (x+(w/2)+5)+camX, ((y+(h/2)+5)-lenY)+camY);
		
			
		int half = (s.getName().length()*8)/2;
		gfx.drawString(s.getName(), (x-half)+camX, (y+(h/2)+4)+camY);
		int shipX = (int)s.getX();
		gfx.drawString(String.valueOf(shipX), (x-(w/2)-5)+camX,(y+(h/2)+20)+camY);
		shipX = (int)s.getY();
		gfx.drawString(String.valueOf(shipX), (x+(w/2)-5)+camX,(y+(h/2)+20)+camY);
		
		gfx.setColor(Color.white);
	}
	
	/**
	 * renders all ACTIVE NavPoints on the map
	 * @param bl BasicLevel
	 * @param gfx Graphics
	 * @param camX
	 * @param camY
	 */
	public void renderPoints(LevelDataModel ldm, Graphics gfx, int camX, int camY){
		gfx.setColor(Color.green);
		for(NavPoint p : ldm.getNavMap().values()){
			if(p.isActive()){
				int len = p.getName().length();
				int tenlen = len*8;
				gfx.drawString(p.getName(),(p.getX()-(tenlen/2))+camX, p.getY()+camY-5);
				gfx.draw(new Circle(p.getX()+camX, p.getY()+camY, tenlen));
			}
		}
		gfx.setColor(Color.white);
	}
	
	/**
	 * renders a 'tag' for an item on a given edge of the players screen
	 * the 'tag' tells the player what the item is, and its distance to the player
	 * @param bl
	 * @param gfx
	 * @param camX
	 * @param camY
	 */
	public void renderRadarTags(LevelDataModel ldm, Graphics gfx,int camX, int camY){
		Vector2f ship = new Vector2f((float)pc.getPlayShip().getX(), (float)pc.getPlayShip().getY());
		for(NavPoint p : ldm.getNavMap().values()){
			Vector2f pLoc = new Vector2f((p.getX()),(p.getY()));
			Line toTarg = new Line(ship, pLoc);
			int len = (int)toTarg.length();
			if(len> 600){
				double angle = Math.atan2((ship.getY()-pLoc.getY()) ,(ship.getX()-pLoc.getX()));
				Vector2f point  = cgs.circularFunction((float)angle);
				gfx.drawString(p.getName(), point.getX(), point.getY());
				gfx.drawString(String.valueOf(len), point.getX(), point.getY()+25);
			}
		}
	}
	
	/**
	 * draw objectives list on-screen
	 * @param gfx
	 */
	private void renderObjectivesList(Graphics gfx) {
		HashMap<Integer, Objective> objs = cgs.getLevel().getObjectiveList();
		int x = 0, y = 0;
		for(Objective obj : objs.values()){
			String msg = obj.getBlurb();
			if(!obj.getComplete()){
				gfx.setColor(Color.green);
			}else if(obj.getComplete()){
				gfx.setColor(Color.gray);
			}
			
		}
	}
}
