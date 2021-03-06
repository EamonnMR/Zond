package ui.hud;

import level.LevelDataModel;
import level.LevelObjective;
import level.NavPoint;
import level.triggers.BasicTrigger;

import org.newdawn.slick.Color;
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
import ai.PursueState;
import core.GameDatabase;
import core.GameplayState;
import core.PlayerClient;
import ents.AIShip;
import ents.BasicShip;
import ents.BasicShot;

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
	private Image hp_i,radar_i,engine_i,wep_i, on_i, off_i;
	float x, y;
	double hp, totalHP;
	private boolean googles = false; 		// bool for developer view mode
	private boolean boundsWarning = false; 	// warn the player they are leaving
	private boolean radarOn = false; 		// is radar active?
	private boolean showPoints = false;		//display navpoints
	private boolean showMap = false;		//show minimap
	private boolean showObjs = false;		//show objectives
	private GameplayState cgs;
	private PlayerClient pc;
	private SpriteSheetFont greenFont;
	private SpriteSheetFont grayFont;
	private Color brightRed, brightBlue, brightYel;

	public Hud(PlayerClient cl, int camBoundsW, int camBoundsH, HudDataModel h, GameDatabase gdb) {
		uiLib = new UILib();
		camBounds = new Rectangle(1,1,camBoundsW,camBoundsH);
		hdm = h;
		pc = cl;
		hp = pc.getPlayShip().getHealth();
		totalHP = hp;
		
		hp_i = gdb.getIMG("hp_i");
		radar_i = gdb.getIMG("radar_i");
		engine_i = gdb.getIMG("engine_i");
		wep_i = gdb.getIMG("wep_i");
		on_i=gdb.getIMG("onBTN_n");
		off_i=gdb.getIMG("offBTN_n");
		
		greenFont = gdb.getFont("green");
		grayFont = gdb.getFont("gray");
		brightRed = new Color(255,39,64);
		brightBlue = new Color(28, 87, 255);
		brightYel = new Color(242, 255, 28);
		showMap = true;
		showPoints = true;
		showObjs = true;
	}

	public void update(PlayerClient cl, GameplayState cgs) {
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
		}
		renderPoints(ldm, gfx, camX,camY);
		
		if(showPoints){
			renderNavTags(ldm, gfx, camX, camY);
		}
		if(showMap){
			renderMinimap(gfx,camX, camY);
		}
		
		if(showObjs){
			renderObjectives(ldm, gfx);
		}
		
		if (googles) {
			gfx.setColor(brightYel);
			devGoggles(gfx, gc, ldm, camX, camY);
		}
		if (boundsWarning) {
			grayFont.drawString(458, 627, "<==!WARNING!==>",brightRed);
			grayFont.drawString(400, 652, "<==!Leaving Mission Area!==>",brightRed);
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
	public void shipRadarCheck(GameplayState cgs, Graphics gfx, int camX, int camY){
		for(BasicShip s : cgs.getShips().values()){
			if(!s.equals(pc.getPlayShip())){
				if(pc.getPlayShip().getRadarShape().intersects(s.getCollider())||pc.getPlayShip().getRadarShape().contains(s.getCollider())){
					drawTargetBox(s, 0, gfx, camX, camY);
				}
			}
		}
	}
	
	/**
	 * draw the minimap
	 * @param gfx
	 */
	private void renderMinimap(Graphics gfx, int camX, int camY) {
		float ratio = 1.0f/100f;
		int xOffset = hdm.getMinimap_point_mod().x, yOffset = hdm.getMinimap_point_mod().y;
		Shape actArea=null, warnArea=null;
		if(cgs.getLevel().getActiveArea().getClass().equals(Circle.class)){
			Circle aa = (Circle) cgs.getLevel().getActiveArea();
			actArea = new Circle(aa.getCenterX()+xOffset,
								aa.getCenterY()+yOffset,
								aa.getRadius()*ratio);
			
			Circle aw = (Circle) cgs.getLevel().getWarnArea();
			warnArea = new Circle(aw.getCenterX() + xOffset,
								aw.getCenterY()+ yOffset,
								aw.getRadius()*ratio) ;
			
		}else if (cgs.getLevel().getActiveArea().getClass().equals(Rectangle.class)){
			actArea = new Rectangle(cgs.getLevel().getActiveArea().getX()
					+ xOffset, cgs.getLevel().getActiveArea().getY()
					+ yOffset, cgs.getLevel().getActiveArea().getWidth() * ratio,
					cgs.getLevel().getActiveArea().getHeight() * ratio);

			warnArea = new Rectangle(cgs.getLevel().getWarnArea().getX()
					+ xOffset, cgs.getLevel().getWarnArea().getY() * ratio
					+ yOffset, cgs.getLevel().getWarnArea().getWidth() * ratio, cgs
					.getLevel().getWarnArea().getHeight()
					* ratio);
		}
		gfx.setColor(Color.red);
		gfx.draw(warnArea);
		gfx.setColor(Color.green);
		gfx.draw(actArea);
		
		float x = (float)((pc.getPlayShip().getX()*ratio)+xOffset);
		float y = (float)((pc.getPlayShip().getY()*ratio)+yOffset);
		
		gfx.draw(new Circle(x,y,2));
		if(radarOn){
			gfx.setColor(Color.blue);
			Circle rad = new Circle((float)((pc.getPlayShip().getX()*ratio)+xOffset),
									(float)((pc.getPlayShip().getY()*ratio)+yOffset),
									pc.getPlayShip().getRadarShape().getRadius()*ratio);
			gfx.draw(rad);
			for(BasicShip s : cgs.getShips().values()){
				if(pc.getPlayShip().getRadarShape().intersects(s.getRadarShape())){
					if(!(pc.getPlayShip().equals(s))){
						if(pc.getPlayShip().getFaction()==s.getFaction()){
							gfx.setColor(Color.green);
						}else{
							gfx.setColor(Color.red);
						}
						Circle targRad = new Circle((float)((s.getX()*ratio)+xOffset),
												(float)((s.getY()*ratio)+yOffset),
												s.getRadarShape().radius*ratio);
						gfx.draw(targRad);
					}
				}
			}
		}
		if(showPoints){
			for(NavPoint p : cgs.getLevel().getNavMap().values()){
				if(p.isActive()){
					gfx.setColor(brightYel);
					gfx.draw(new Circle(p.getX()*ratio+xOffset, p.getY()*ratio+yOffset, 4));
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
		
		if(radarOn){
			gfx.setColor(Color.blue);
			gfx.draw(offsetShape(pc.getPlayShip().getRadarShape(), camX, camY));
			
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
			gfx.draw(offsetShape(s.getRadarShape(), camX, camY));
			gfx.drawString("X:"+String.valueOf(s.getX()), (float)s.getX()-100+camX, (float)s.getY()+100+camY);
			gfx.drawString("Y:"+String.valueOf(s.getY()), (float)s.getX()-100+camX, (float)s.getY()+75+camY);
			gfx.drawString(String.valueOf("Angle:"+s.getRot()), (float)s.getX()-100+camX, (float)s.getY()+50+camY);
			if(s.getClass().equals(AIShip.class)){
				AIShip p = (AIShip) s;
				if(p.getBrains().getClass().equals(PursueState.class)){
					PursueState b = (PursueState) p.getBrains();
					gfx.drawString(String.valueOf("TargetAngle:"+b.targetAngle), (float)s.getX()-100+camX, (float)s.getY()+25+camY);
					gfx.drawString(String.valueOf("TargetDist:"+b.distToTarg), (float)s.getX()-100+camX, (float)s.getY()+125+camY);
				}

			}
		}
		
		for(BasicShot s : cgs.getShots().values()){
			gfx.draw(offsetShape(s.getCollider(), camX, camY));
		}

	}


	public void checkHP(double hp, Graphics gfx) {
		if (hp <= totalHP / 2 && hp > totalHP/4) {
			grayFont.drawString(hdm.getHp_point_mod().x+hp_i.getWidth()/2+4, hdm.getHp_point_mod().y-8.5f, String.valueOf(hp), brightYel);
		} else if (hp <= totalHP / 4) {
			grayFont.drawString(hdm.getHp_point_mod().x+hp_i.getWidth()/2+4, hdm.getHp_point_mod().y-8.5f, String.valueOf(hp), brightRed);
		}else {
			grayFont.drawString(hdm.getHp_point_mod().x+hp_i.getWidth()/2+4, hdm.getHp_point_mod().y-8.5f, String.valueOf(hp), Color.green);
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
		if(s.getFaction()==pc.getPlayShip().getFaction()){
			col= Color.green;
		}else{
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
		grayFont.drawString((x-half)+camX, (y+(h/2)+4)+camY, s.getName(), col); 
		grayFont.drawString((x-(w/2)-5)+camX, (y+(h/2)+20)+camY, "hp:", col);
		grayFont.drawString((x+(w/2)-5)+camX, (y+(h/2)+20)+camY, String.valueOf(s.getHealth()), col);
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
				greenFont.drawString((p.getX()-(tenlen/2))+camX,  p.getY()+camY-5, p.getName(),brightYel);
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
				if (len > 600) {
					double angle = Math.atan2((ship.getY() - pLoc.getY()),(ship.getX() - pLoc.getX()));
					Vector2f point = cgs.circularFunction((float) angle, 350);;
					grayFont.drawString(point.getX(), point.getY(), p.getName(),brightYel);
					grayFont.drawString(point.getX(), point.getY() + 25,String.valueOf(len),brightYel);
				}
			}
		}
	}
	
	public void renderShipTags(GameplayState cgs, Graphics gfx,int camX, int camY){
		Vector2f player = new Vector2f((float)pc.getPlayShip().getX(), (float)pc.getPlayShip().getY());
		for(BasicShip ship : cgs.getShips().values()){
			if(!ship.equals(cgs.getPlayerShip())){	
				Vector2f target = new Vector2f((float)(ship.getX()), (float)(ship.getY()));
				Line toTarg = new Line(player, target);
				int len = (int) toTarg.length();
				if (len < cgs.getPlayerShip().getRadarShape().getRadius() && len > 600) {
					double angle = Math.atan2((player.getY() - target.getY()),(player.getX() - target.getX()));
					Vector2f point = cgs.circularFunction((float) angle, 150);
					if(ship.getFaction()!=pc.getPlayShip().getFaction()){
						grayFont.drawString(point.getX(),  point.getY(), "!["+ship.getName()+"]!", brightRed);
						grayFont.drawString(point.getX(), point.getY() + 25, String.valueOf(len), brightRed);
					}else if(ship.getFaction()==pc.getPlayShip().getFaction()){
						grayFont.drawString(point.getX(),  point.getY(), "["+ship.getName()+"]", brightBlue);
						grayFont.drawString(point.getX(), point.getY() + 25, String.valueOf(len), brightBlue);
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
		greenFont.drawString(hdm.getShipName_point_mod().x-nameW/2, hdm.getShipName_point_mod().y-8.5f, pc.getPlayShip().getName());
		
		//weapon
		uiLib.drawImageCenteredOnPoint(gfx, wep_i, hdm.getGunName_point_mod());
		
		greenFont.drawString(hdm.getGunName_point_mod().x+wep_i.getWidth()/2+4, hdm.getGunName_point_mod().y-8.5f, pc.getPlayShip().getWeapon().getName());
		
		//engine
		uiLib.drawImageCenteredOnPoint(gfx, engine_i, hdm.getEngName_point_mod());
		greenFont.drawString(hdm.getEngName_point_mod().x+engine_i.getWidth()/2+4, hdm.getEngName_point_mod().y-8.5f, pc.getPlayShip().getEngine().getName());
		
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
	 * @param ldm
	 */
	private void renderObjectives(LevelDataModel ldm, Graphics gfx) {
		int x = 575, y=20;
		for(LevelObjective lo : ldm.getObjectives().values()){
			if(!(lo.getComplete()) && lo.getActive()){
				greenFont.drawString(x,y, lo.getTltip());
			}else if(!(lo.getComplete()) && !(lo.getActive())){
				grayFont.drawString(x,y, lo.getTltip());
			}
			y+=20;
		}
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

	public boolean getShowMap() {
		return showMap;
	}

	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
	}

	public boolean getShowObjs() {
		return showObjs;
	}

	public void setShowObjs(boolean showObjs) {
		this.showObjs = showObjs;
	}
}
