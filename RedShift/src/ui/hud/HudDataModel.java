package ui.hud;

import java.awt.Point;

import org.newdawn.slick.geom.Rectangle;

/**
 * Data class to hold all the data related to the hud. It will have default values and variables
 * this is so the user can modify the in-game Hud.
 * @author Roohr
 *
 */
public class HudDataModel {

	private String shipName, gunName, engName, health, radar, minimap;
	private Rectangle shipName_rec, gunName_rec, engName_rec, hp_rec, radar_rec, minimap_rec;
	private Point shipName_pt_def, gunName_pt_def, engName_pt_def, hp_pt_def, radar_pt_def, minimap_pt_def;
	private Point shipName_pt_mod, gunName_pt_mod, engName_pt_mod, hp_pt_mod, radar_pt_mod, minimap_pt_mod;
	
	public HudDataModel(){
		shipName = "ship name";
		gunName = "[WEAPON]";
		engName = "[ENGINE]";
		health = "[HEALTH]";
		radar = "[RADAR]";
		minimap = "minimap";
		
		shipName_pt_def = new Point(512, 690);
		hp_pt_def = new Point(413,715);
		gunName_pt_def = new Point(596, 715);
		engName_pt_def = new Point(628, 744);
		radar_pt_def = new Point(394, 744);
		minimap_pt_def = new Point(100,740);
		
		
		shipName_rec = new Rectangle(0, 0,(float)(shipName.length()*10),(float)25);
		shipName_rec.setCenterX(shipName_pt_def.x);
		shipName_rec.setCenterY(shipName_pt_def.y);
		
		gunName_rec = new Rectangle(0, 0,(float)(gunName.length()*10),(float)25);
		gunName_rec.setCenterX(gunName_pt_def.x);
		gunName_rec.setCenterY(gunName_pt_def.y);
		
		engName_rec = new Rectangle(0, 0,(float)(engName.length()*10),(float)25);
		engName_rec.setCenterX(engName_pt_def.x);
		engName_rec.setCenterY(engName_pt_def.y);
		
		hp_rec = new Rectangle(0, 0,(float)(health.length()*10),(float)25);
		hp_rec.setCenterX(hp_pt_def.x);
		hp_rec.setCenterY(hp_pt_def.y);
		
		radar_rec = new Rectangle(0, 0,(float)(radar.length()*10),(float)25);
		radar_rec.setCenterX(radar_pt_def.x);
		radar_rec.setCenterY(radar_pt_def.y);
		
		minimap_rec = new Rectangle(0, 0,(float)(minimap.length()*10),(float)25);
		minimap_rec.setCenterX(minimap_pt_def.x);
		minimap_rec.setCenterY(minimap_pt_def.y);
		
		
		shipName_pt_mod = shipName_pt_def;
		gunName_pt_mod = gunName_pt_def;
		engName_pt_mod = engName_pt_def;
		hp_pt_mod = hp_pt_def;
		radar_pt_mod = radar_pt_def;
		minimap_pt_mod = minimap_pt_def;
	}
	
	/**
	 * resets the hud psoitions back to their default locations
	 */
	public void reset(){
		shipName_pt_mod = shipName_pt_def;
		gunName_pt_mod = gunName_pt_def;
		engName_pt_mod = engName_pt_def;
		hp_pt_mod = hp_pt_def;
		radar_pt_mod = radar_pt_def;
		minimap_pt_mod = minimap_pt_def;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getGunName() {
		return gunName;
	}

	public void setGunName(String gunName) {
		this.gunName = gunName;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getRadar() {
		return radar;
	}

	public void setRadar(String radar) {
		this.radar = radar;
	}

	public String getMinimap() {
		return minimap;
	}

	public void setMinimap(String minimap) {
		this.minimap = minimap;
	}

	public Rectangle getShipName_rec() {
		return shipName_rec;
	}

	public void setShipName_rec(Rectangle shipName_rec) {
		this.shipName_rec = shipName_rec;
	}

	public Rectangle getGunName_rec() {
		return gunName_rec;
	}

	public void setGunName_rec(Rectangle gunName_rec) {
		this.gunName_rec = gunName_rec;
	}

	public Rectangle getEngName_rec() {
		return engName_rec;
	}

	public void setEngName_rec(Rectangle engName_rec) {
		this.engName_rec = engName_rec;
	}

	public Rectangle getRadar_rec() {
		return radar_rec;
	}

	public void setRadar_rec(Rectangle radar_rec) {
		this.radar_rec = radar_rec;
	}

	public Rectangle getMinimap_rec() {
		return minimap_rec;
	}

	public void setMinimap_rec(Rectangle minimap_rec) {
		this.minimap_rec = minimap_rec;
	}

	public Point getShipName_point_def() {
		return shipName_pt_def;
	}

	public void setShipName_point_def(Point shipName_point_def) {
		this.shipName_pt_def = shipName_point_def;
	}

	public Point getGunName_point_def() {
		return gunName_pt_def;
	}

	public void setGunName_point_def(Point gunName_point_def) {
		this.gunName_pt_def = gunName_point_def;
	}

	public Point getEngName_point_def() {
		return engName_pt_def;
	}

	public void setEngName_point_def(Point engName_point_def) {
		this.engName_pt_def = engName_point_def;
	}

	public Point getRadar_point_def() {
		return radar_pt_def;
	}

	public void setRadar_point_def(Point radar_point_def) {
		this.radar_pt_def = radar_point_def;
	}

	public Point getMinimap_point_def() {
		return minimap_pt_def;
	}

	public void setMinimap_point_def(Point minimap_point_def) {
		this.minimap_pt_def = minimap_point_def;
	}

	public Point getShipName_point_mod() {
		return shipName_pt_mod;
	}

	public void setShipName_point_mod(Point shipName_point_mod) {
		this.shipName_pt_mod = shipName_point_mod;
	}

	public Point getGunName_point_mod() {
		return gunName_pt_mod;
	}

	public void setGunName_point_mod(Point gunName_point_mod) {
		this.gunName_pt_mod = gunName_point_mod;
	}

	public Point getEngName_point_mod() {
		return engName_pt_mod;
	}

	public void setEngName_point_mod(Point engName_point_mod) {
		this.engName_pt_mod = engName_point_mod;
	}

	public Point getRadar_point_mod() {
		return radar_pt_mod;
	}

	public void setRadar_point_mod(Point radar_point_mod) {
		this.radar_pt_mod = radar_point_mod;
	}

	public Point getMinimap_point_mod() {
		return minimap_pt_mod;
	}

	public void setMinimap_point_mod(Point minimap_point_mod) {
		this.minimap_pt_mod = minimap_point_mod;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public Rectangle getHp_rec() {
		return hp_rec;
	}

	public void setHp_rec(Rectangle hp_rec) {
		this.hp_rec = hp_rec;
	}

	public Point getHp_point_def() {
		return hp_pt_def;
	}

	public void setHp_point_def(Point hp_point_def) {
		this.hp_pt_def = hp_point_def;
	}

	public Point getHp_point_mod() {
		return hp_pt_mod;
	}

	public void setHp_point_mod(Point hp_point_mod) {
		this.hp_pt_mod = hp_point_mod;
	}
	
}
