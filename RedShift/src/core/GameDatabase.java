package core;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import ents.BasicArmor;
import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;
import ents.BasicShot;

/**
 * Master Database for media resources
 * loads resources, populates maps with images
 * ultimately a batch load approach is warranted, but a procedural will work
 * for development
 * @author proohr
 * @version 1.0
 */
public class GameDatabase {

	//vars
//	private static GameDatabase instance;
	private Image merc, gem, lunar, vost, vosk, zond4, engine1, gun1, level1, shot1, shot2,shot3, ast1;
	private Map<String, Image> indexImages;
	private Map<String, BasicShip> indexShip;
	private Map<String, BasicGun> indexGuns;
	private Map<String, BasicEngine> indexEng;
	private Map<String, BasicShot> indexShot;
	private Map<String, BasicArmor> indexArmor;
	
	//constructor
	public GameDatabase(){}

	/**
	 * initialize the gamedatabase for this instance of the game
	 * !ORDER OF THESE METHODS MATTER!
	 * loadImages() - always first!
	 * indexImages() - always second!
	 * indexShot() - always BEFORE guns
	 * indexShip() - always LAST
	 */
	public void iniGDB(){
		try {
			loadImages();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		indexImages  = new HashMap<String, Image>();
		indexShot = new HashMap<String, BasicShot>();
		indexGuns = new HashMap<String, BasicGun>();
		indexEng = new HashMap<String, BasicEngine>();
		indexArmor = new HashMap<String, BasicArmor>();
		indexShip = new HashMap<String, BasicShip>();
		populateAll();
	}
	
	/**
	 * populates all indices with their respective resources
	 * 
	 */
	public void populateAll(){
		populateImages();
		populateArmor();
		populateShot();
		populateGun();
		populateEngine();
		populateShips();
	}
	
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
//		thrust1 = new Image("assets/images/fx/engine1thrust.png");
		
		//Levels
		level1 = new Image("assets/images/ScratchLevel.png");
		
		//Shots
		shot1 = new Image("assets/images/fx/shot1.png");
		shot2 = new Image("assets/images/fx/shot2.png");
		shot3 = new Image("assets/images/fx/laz1.png");
		
		//Asteroids
		ast1 = new Image("assets/images/doodads/ast1.png");
	}
	
	/**
	 * puts Images into the index hashmap
	 * procedural now, but automate before beta
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
		indexImages.put("shot2", shot2);
		indexImages.put("shot3", shot3);
		
		//Asteroids
		indexImages.put("asteroid", ast1);
	}
	
	/**
	 * simple Image get method.
	 * @return Image
	 */
	public Image getImage(String key){
		return indexImages.get(key);
	}
	
	/**
	 *build all ship instances
	 * populate map with instances
	 */
	public void populateShips(){
		//Mercury-------------------------
		BasicShip merc = new BasicShip();
		merc.setImg(indexImages.get("mercury").copy());
		merc.setHealth(5);
		merc.setPoints(5);
		merc.setTotalWeight(5);
		merc.setGunPtLength(26);
		merc.setEngPtLength(-24);
		merc.setCollider(new Circle(0,0,16,24));
		merc.getImg().setRotation(0);
		indexShip.put("mercury", merc);
		
		//Gemini-------------------------
		BasicShip gem = new BasicShip();
		gem.setImg(indexImages.get("gemini").copy());
		gem.setHealth(5);
		gem.setPoints(5);
		gem.setTotalWeight(5);
		gem.setGunPtLength(-28);
		gem.setEngPtLength(24);
		gem.setCollider(new Circle(0,0,16,24));
		indexShip.put("gemini", gem);		
		
		//Apollo-------------------------
		BasicShip apollo = new BasicShip();
		apollo.setImg(indexImages.get("lunar").copy());
		apollo.setHealth(5);
		apollo.setPoints(5);
		apollo.setTotalWeight(5);
		apollo.setGunPtLength(-28);
		apollo.setEngPtLength(24);
		apollo.setCollider(new Circle(0,0,16,24));
		indexShip.put("lunar", apollo);	
		
		//Voskhod-------------------------
		BasicShip voskhod = new BasicShip();
		voskhod.setImg(indexImages.get("voskhod").copy());
		voskhod.setHealth(5);
		voskhod.setPoints(5);
		voskhod.setTotalWeight(5);
		voskhod.setGunPtLength(-28);
		voskhod.setEngPtLength(24);
		voskhod.setCollider(new Circle(0,0,16,24));
		indexShip.put("voskhod", voskhod);	
		
		//Vostok-------------------------
		BasicShip vost = new BasicShip();
		vost.setImg(indexImages.get("vostok").copy());
		vost.setHealth(5);
		vost.setPoints(5);
		vost.setTotalWeight(5);
		vost.setGunPtLength(-28);
		vost.setEngPtLength(24);
		vost.setCollider(new Circle(0,0,16,24));
		indexShip.put("vostok", vost);	
		
		//Zond4-------------------------
		BasicShip zond4 = new BasicShip();
		zond4.setImg(indexImages.get("zond4").copy());
		zond4.setHealth(5);
		zond4.setPoints(5);
		zond4.setTotalWeight(5);
		zond4.setGunPtLength(-28);
		zond4.setEngPtLength(24);
		zond4.setCollider(new Circle(0,0,16,24));
		indexShip.put("zond4", zond4);	
	}
	
	/**
	 * simple get ship method
	 * @return BasicShip
	 */
	public BasicShip getShip(String index){
		return indexShip.get(index);
	}

	/**
	 * build all engine instances
	 * populate map with instances
	 */
	public void populateEngine(){
		//Small Engine
		BasicEngine smallEng = new BasicEngine();
		smallEng.setCost(0);
		smallEng.setWeight(0);
		smallEng.setTurnrate(0.25f);
		smallEng.setThrustX(0.3f);
		smallEng.setThrustY(0.1f);
		smallEng.setStrafeRate(0.1f);
		smallEng.setInGameImg(indexImages.get("eng1").copy());
		indexEng.put("smallEngine", smallEng);
		
		//Medium Engine
		
		//Large Engine
		
	}
	
	/**
	 * simple get engine method
	 * @return BasicEngine
	 */
	public BasicEngine getEngine(String index){
		return indexEng.get(index);
	}
	
	/**
	 * build all shot instances
	 * populate map with instances
	 */
	public void populateShot(){
		//20mm shot
		BasicShot twentyShot = new BasicShot();
		twentyShot.setImg(indexImages.get("shot1").copy());
		twentyShot.setDamage(5);
		twentyShot.setSpeed(0.4f);
		twentyShot.setInterval(1000);
		twentyShot.setCollider(new Circle(0,0,4));
		indexShot.put("twentyShot", twentyShot);
	}
	
	/**
	 * simple get shot method
	 * @return BasicShot
	 */
	public BasicShot getShot(String index){
		return indexShot.get(index);
	}
	
	/**
	 * build all gun instances
	 * populate map with instances
	 */
	public void populateGun(){
		//20mm cannon-------------------------
		BasicGun twenty = new BasicGun();
		twenty.setCoolDown(150);
		twenty.setCost(0);
		twenty.setImg(indexImages.get("gun1").copy());
		twenty.setWeight(0);
		twenty.setProj(indexShot.get("twentyShot"));
		indexGuns.put("20mm", twenty);
		
		//60mm cannon-------------------------
		BasicGun sixty = new BasicGun();
		sixty.setCoolDown(150);
		sixty.setCost(0);
		sixty.setImg(indexImages.get("gun1").copy());
		sixty.setWeight(0);
//		sixty.setProj(proj)
		indexGuns.put("60mm", sixty);
		
		//105mm-------------------------
		BasicGun oneOhfive = new BasicGun();
		oneOhfive.setCoolDown(150);
		oneOhfive.setCost(0);
		oneOhfive.setImg(indexImages.get("gun1").copy());
		oneOhfive.setWeight(0);
//		oneOhfive(proj)
		indexGuns.put("105mm", oneOhfive);
		
		//Small Plasma
		BasicGun smallPlas = new BasicGun();
		smallPlas.setCoolDown(150);
		smallPlas.setCost(0);
		smallPlas.setImg(indexImages.get("gun1").copy());
		smallPlas.setWeight(0);
//		smallPlas(proj)
		indexGuns.put("smallPlas", smallPlas);
		
		//Small Laser
		BasicGun smallLaser = new BasicGun();
		smallLaser.setCoolDown(150);
		smallLaser.setCost(0);
		smallLaser.setImg(indexImages.get("gun1").copy());
		smallLaser.setWeight(0);
//		smallPlas(proj)
		indexGuns.put("smallLaser", smallLaser);
	}
	
	/**
	 * simple get gun method
	 * @return BasicGun
	 */
	public BasicGun getGun(String index){
		return indexGuns.get(index);
	}
	
	/**
	 * build all armors 
	 * populate map with instances
	 */
	public void populateArmor(){
		//TODO: armor types are phase 2
	}
	
	/**
	 * simple get armor method
	 * @return BasicArmor
	 */
	public BasicArmor getArmor(String index){
		return indexArmor.get(index);
	}
	
}
