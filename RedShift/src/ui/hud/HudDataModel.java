package ui.hud;

import java.awt.Point;

/**
 * Data class to hold all the data related to the hud. It will have default values and variables
 * this is so the user can modify the in-game Hud.
 * @author Roohr
 *
 */
public class HudDataModel {

	private Point shipName_pt_def, gunName_pt_def, engName_pt_def, hp_pt_def, radar_pt_def, minimap_pt_def;
	private Point shipName_pt_mod, gunName_pt_mod, engName_pt_mod, hp_pt_mod, radar_pt_mod, minimap_pt_mod;
	
	public HudDataModel(){
		shipName_pt_def = new Point(512, 690);
		hp_pt_def = new Point(413,715);
		gunName_pt_def = new Point(596, 715);
		engName_pt_def = new Point(628, 744);
		radar_pt_def = new Point(394, 744);
		minimap_pt_def = new Point(160,618);
		
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
