package ents;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * under construction at the moment, but outlines a basic level class
 * @author Roohr
 * @version 1.0
 */
public class BaseLevel {

	//vars
	private String levelName;
	private int songID;
	private Rectangle bounds;

		//Layer 0
		private int bkgID;
		private Image bkgIMG;
		//Layer 1
		private int foreID;
		private Image foreIMG;
	
		//Layer 2

	//const
	/**
	 * @param name string, name of the level
	 * @param width int, total width of level
	 * @param height int, total height of level
	 */
	public BaseLevel(String name, Rectangle bnds){
		levelName = name;
		bounds = bnds;
	}
	
	//methods
	public void render(Graphics gfx, float x, float y){
		bkgIMG.draw(x,y);
	}

	/**
	 * 
	 * @return
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * 
	 * @return
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * 
	 * @return
	 */
	public int getSongID() {
		return songID;
	}

	/**
	 * 
	 * @return
	 */
	public void setSongID(int songID) {
		this.songID = songID;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBkgID() {
		return bkgID;
	}
	
	/**
	 * 
	 * @return
	 */
	public void setBkgID(int bkgID) {
		this.bkgID = bkgID;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getForeID() {
		return foreID;
	}

	/**
	 * 
	 * @return
	 */
	public void setForeID(int foreID) {
		this.foreID = foreID;
	}
	
	public Image getBkgIMG() {
		return bkgIMG;
	}

	public void setBkgIMG(Image bkgIMG) {
		this.bkgIMG = bkgIMG;
	}

	public Image getForeIMG() {
		return foreIMG;
	}

	public void setForeIMG(Image foreIMG) {
		this.foreIMG = foreIMG;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
