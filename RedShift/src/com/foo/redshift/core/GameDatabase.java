package com.foo.redshift.core;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Master Database for media resources
 * @author proohr
 * @version 1.0
 */
public class GameDatabase {

	//vars
	private Image merc, vost, engine1, gun1, level1, thrust1, shot1, ast1;
	private Image[] indexShipIMG, indexGunIMG, indexEngIMG, indexArmIMG, indexShotIMG, indexGFXIMG, indexLvlIMG, indexObjIMG;
	
	
	//const
	public GameDatabase(){
		//subject to update as assets are made
		indexEngIMG  = new Image[1];
		indexArmIMG = new Image[1];
		indexGunIMG = new Image[1];
		indexShotIMG = new Image[1];
		indexShipIMG = new Image[2];
		indexGFXIMG = new Image[1];
		indexLvlIMG = new Image[1];
		indexObjIMG = new Image[1];
	}
	
	//methods
	/**
	 * loadImages() - loads all iamges
	 * @throws SlickException
	 */
	public void loadImages() throws SlickException{
		//Ships
		merc = new Image("assets/images/ships/nasa/mercury/mercury.png");
		vost = new Image("assets/images/ships/russia/vostok/vostok1.png");
		
		//Engines
		engine1 = new Image("assets/images/engines/engine1.png");
		
		//Guns
		gun1 = new Image("assets/images/weapons/20mm.png");
		
		//thrust fx
		thrust1 = new Image("assets/images/fx/engine1thrust.png");
		
		//Levels
		level1 = new Image("assets/images/ScratchLevel.png");
		
		//Shots
		shot1 = new Image("assets/images/fx/shot1.png");
		
		//Asteroids
		ast1 = new Image("assets/images/doodads/ast1.png");
	}
	
	/**
	 * puts Images to indexShipIMG
	 */
	public void populateShipIMG(){
		indexShipIMG[0] = merc; 
		indexShipIMG[1] = vost;
	}
	/**
	 * get an Image from the indexShipIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getShipImage(int i){
		return indexShipIMG[i];
	}
	
	/**
	 * puts Images to indexEngIMG
	 */
	public void populateEngIMG(){
		indexEngIMG[0] = engine1;
	}
	/**
	 * get an Image from the indexEngIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getEngIMG(int i){
		return indexEngIMG[i];
	}
	
	/**
	 * puts Images to indexGunIMG
	 */
	public void populateGunIMG(){
		indexGunIMG[0] = gun1;
	}
	/**
	 * gets an Image from the indexGunIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getGunIMG(int i){
		return indexGunIMG[0];
	}
	
	/**
	 * put Images to indexArmIMG
	 */
	public void populateArmIMG(){
		indexArmIMG[0] = null;
	}
	/**
	 * gets an Image from the indexArmIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getArmIMG(int i){
		return indexArmIMG[i];
	}
	
	/**
	 * put Images to indexShotIMG
	 */
	public void populateShotIMG(){
		indexShotIMG[0] = shot1;
	}
	/**
	 * gets an Image from indexShotIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getShotIMG(int i){
		return indexShotIMG[i];
	}
	
	/**
	 * puts Images to indexGFXIMG
	 */
	public void populateGFXIMG(){
		indexGFXIMG[0] = null;
	}
	/**
	 * gets an Image from indexGFXIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getGFXIMG(int i){
		return indexGFXIMG[i];
	}
	
	/**
	 * puts Images to indexLvlIMG
	 */
	public void populateLvlIMG(){
		indexLvlIMG[0] = level1;
	}
	/**
	 * gets an Image from indexLvlIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getLvlIMG(int i){
		return indexLvlIMG[i];
	}
	
	/**
	 * puts Images to indexObjIMG
	 */
	public void populateObjIMG(){
		indexObjIMG[0] = ast1;
	}
	
	/**
	 * gets an Image from indexObjIMG
	 * @param i index pointer
	 * @return Image
	 */
	public Image getObjIMG(int i){
		return indexObjIMG[i];
	}
	
	/**
	 * XXX use me.
	 * @return
	 */
	public Image getThrust1() {
		return thrust1;
	}
	
}
