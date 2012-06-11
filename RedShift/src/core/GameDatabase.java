package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	 * loadImages() - always first!   (?)
	 * indexImages() - always second! (?)
	 * indexShot() - always BEFORE guns
	 * indexShip() - always LAST
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void iniGDB() throws FileNotFoundException{
		indexImages  = new HashMap<String, Image>();
		try {
			try {
				XloadImages();
				//loadImages();
			} catch (SlickException e) {
				System.out.println("Problem loading image)");
				e.printStackTrace();
			}
		} catch (IOException e){
			System.out.println("Problem opening images file)");
			e.printStackTrace();
		}
		
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
		populateArmor();
		populateShot();
		populateGun();
		populateEngine();
		populateShips();
	}
	
	/**
	 * loadImages() - loads all images
	 * Automate this before the beta
	 * @throws SlickException
	 */
	public void XloadImages() throws SlickException, FileNotFoundException, IOException{
		StringTree s = StringTree.fromStream(new FileInputStream("assets/text/images.rst"));
		for (String child : s.childSet()){
			ldImg(child, s.getValue(child));
			//System.out.println("Name ''" + child + "'' Location: ''" + s.getValue(child) + "''.");
		}
	}
	
	/**
	 * Load an image into the database
	 * @param name       It's name in the DB
	 * @param location   Where to load the file from 
	 */
	public void ldImg(String name, String location) throws SlickException{
		try{
			indexImages.put(name, new Image(location) );
			System.out.println("Loaded ''" + name + "'' at location: ''" + location + "''.");
		} catch (SlickException e){
			System.out.println("Failed ''" + name + "'' at location: ''" + location + "''.");
			e.printStackTrace();
		}
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
		merc.setName("mercury");
		indexShip.put(merc.getName(), merc);
		
		//Gemini-------------------------
		BasicShip gem = new BasicShip();
		gem.setImg(indexImages.get("gemini").copy());
		gem.setHealth(5);
		gem.setPoints(5);
		gem.setTotalWeight(5);
		gem.setGunPtLength(28);
		gem.setEngPtLength(-24);
		gem.setCollider(new Circle(0,0,16,28));
		gem.setName("gemini");
		indexShip.put(gem.getName(), gem);		
		
		//Apollo-------------------------
		BasicShip apollo = new BasicShip();
		apollo.setImg(indexImages.get("lunar").copy());
		apollo.setHealth(5);
		apollo.setPoints(5);
		apollo.setTotalWeight(5);
		apollo.setGunPtLength(-28);
		apollo.setEngPtLength(24);
		apollo.setCollider(new Circle(0,0,16,24));
		apollo.setName("lunar");
		indexShip.put(apollo.getName(), apollo);	
		
		//Voskhod-------------------------
		BasicShip voskhod = new BasicShip();
		voskhod.setImg(indexImages.get("voskhod").copy());
		voskhod.setHealth(5);
		voskhod.setPoints(5);
		voskhod.setTotalWeight(5);
		voskhod.setGunPtLength(-28);
		voskhod.setEngPtLength(24);
		voskhod.setCollider(new Circle(0,0,16,24));
		voskhod.setName("voskhod");
		indexShip.put(voskhod.getName(), voskhod);	
		
		//Vostok-------------------------
		BasicShip vost = new BasicShip();
		vost.setImg(indexImages.get("vostok").copy());
		vost.setHealth(5);
		vost.setPoints(5);
		vost.setTotalWeight(5);
		vost.setGunPtLength(-28);
		vost.setEngPtLength(24);
		vost.setCollider(new Circle(0,0,16,24));
		vost.setName("vostok");
		indexShip.put(vost.getName(), vost);	
		
		//Zond4-------------------------
		BasicShip zond4 = new BasicShip();
		zond4.setImg(indexImages.get("zond4").copy());
		zond4.setHealth(5);
		zond4.setPoints(5);
		zond4.setTotalWeight(5);
		zond4.setGunPtLength(-28);
		zond4.setEngPtLength(24);
		zond4.setCollider(new Circle(0,0,16,24));
		zond4.setName("zond4");
		indexShip.put(zond4.getName(), zond4);	
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
		smallEng.setName("smallEngine");
		indexEng.put(smallEng.getName(), smallEng);
		
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
		twentyShot.setSpeed(0.15f);
		twentyShot.setInterval(10000);
		twentyShot.setCollider(new Circle(0,0,4));
		indexShot.put("twentyShot", twentyShot);
		
		//60mm shot
		BasicShot sixtyShot = new BasicShot();
		sixtyShot.setImg(indexImages.get("shot2").copy());
		sixtyShot.setDamage(5);
		sixtyShot.setSpeed(0.12f);
		sixtyShot.setInterval(7500);
		sixtyShot.setCollider(new Circle(0,0,4));
		indexShot.put("sixtyShot", sixtyShot);
		
		//105mm shot
		BasicShot oneFiveShot = new BasicShot();
		oneFiveShot.setImg(indexImages.get("shot3").copy());
		oneFiveShot.setDamage(5);
		oneFiveShot.setSpeed(0.1f);
		oneFiveShot.setInterval(5000);
		oneFiveShot.setCollider(new Circle(0,0,4));
		indexShot.put("oneFiveShot", oneFiveShot);
		
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
		twenty.setCoolDown(200);
		twenty.setCost(0);
		twenty.setImg(indexImages.get("gun1").copy());
		twenty.setWeight(0);
		twenty.setProj(indexShot.get("twentyShot"));
		twenty.setName("20mm");
		indexGuns.put(twenty.getName(), twenty);
		
		//60mm cannon-------------------------
		BasicGun sixty = new BasicGun();
		sixty.setCoolDown(300);
		sixty.setCost(0);
		sixty.setImg(indexImages.get("gun2").copy());
		sixty.setWeight(0);
		sixty.setName("60mm");
		sixty.setProj(indexShot.get("sixtyShot"));
		indexGuns.put(sixty.getName(), sixty);
		
		//105mm-------------------------
		BasicGun oneOhfive = new BasicGun();
		oneOhfive.setCoolDown(400);
		oneOhfive.setCost(0);
		oneOhfive.setImg(indexImages.get("gun3").copy());
		oneOhfive.setWeight(0);
		oneOhfive.setName("105mm");
		oneOhfive.setProj(indexShot.get("oneFiveShot"));
		indexGuns.put(oneOhfive.getName(), oneOhfive);
		
		//Small Plasma
		BasicGun smallPlas = new BasicGun();
		smallPlas.setCoolDown(150);
		smallPlas.setCost(0);
		smallPlas.setImg(indexImages.get("gun1").copy());
		smallPlas.setWeight(0);
		smallPlas.setName("smallPlas");
//		smallPlas(proj)
		indexGuns.put(smallPlas.getName(), smallPlas);
		
		//Small Laser
		BasicGun smallLaser = new BasicGun();
		smallLaser.setCoolDown(150);
		smallLaser.setCost(0);
		smallLaser.setImg(indexImages.get("gun1").copy());
		smallLaser.setWeight(0);
		smallLaser.setName("smallLaser");
//		smallPlas(proj)
		indexGuns.put(smallLaser.getName(), smallLaser);
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
