package core;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Master Database for media resources
 * @author proohr
 * @version 1.0
 */
public class GameDatabase {

	//vars
	private Image merc, gem, lunar, vost, vosk, zond4, engine1, gun1, level1, thrust1, shot1, ast1;
	private Map<String, Image> indexImages;
	
	//constructor
	public GameDatabase(){
		//subject to update as assets are made
		indexImages  = new HashMap<String, Image>();
	}
	
	//methods
	/**
	 * loadImages() - loads all images
	 * @throws SlickException
	 */
	public void loadImages() throws SlickException{
		//Ships
		merc = new Image("assets/images/ships/nasa/mercury.png");
		gem = new Image("assets/images/ships/nasa/gemini.png");
		lunar = new Image("assets/images/ships/nasa/lunar.png");
		
		vost = new Image("assets/images/ships/russia/vostok1.png");
		vosk = new Image("assets/images/ships/russia/voskhod.png");
		zond4 = new Image("assets/images/ships/russia/zond4.png");
		
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
	 * puts Images into the index hashmap
	 */
	public void populateImages(){
		//Ships
		indexImages.put("mercury", merc);
		indexImages.put("gemini", gem);
		indexImages.put("lunar", lunar);
		
		indexImages.put("vostok", vost);
		indexImages.put("voskhod", vosk);
		indexImages.put("zond4", zond4);
		
		//Engines
		indexImages.put("eng1", engine1);
		
		//Guns
		indexImages.put("gun1", gun1);
		
		//Levels
		indexImages.put("level1field",level1);
		
		//Shots
		indexImages.put("shot1",shot1);
		
		//Asteroids
		indexImages.put("asteroid", ast1);
	}
	
	/**
	 * 
	 * @return Image
	 */
	public Image getImage(String key){
		return indexImages.get(key);
	}
}
