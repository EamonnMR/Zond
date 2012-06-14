package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

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
	 */
	public void iniGDB() throws IOException{
		indexImages  = new HashMap<String, Image>();
		try {
			try {
				xloadImages();
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public void populateAll() throws FileNotFoundException, IOException{
		//populateArmor(); We have no armor types at this point
		xpopulateShot();
		//populateGun();    //Original method left to prevent freakouts
		xpopulateGun(); //Now you're cooking with external data!
		xpopulateEngine();
		populateShips();
	}
	
	/**
	 * loadImages() - loads all images
	 * Automate this before the beta
	 * @throws SlickException
	 */
	public void xloadImages() throws SlickException, FileNotFoundException, IOException{
		StringTree s = StringTree.fromStream(new FileInputStream("assets/text/images.rst"));
		for (String child : s.childSet()){
			ldImg(child, s.getValue(child));
			//System.out.println("Name ''" + child + "'' Location: ''" + s.getValue(child) + "''.");
		}
	}
	
	/**
	 * Loads gun stats from assets/text/guns.rst
	 * 
	 * See the comments at the beginning of guns.rst
	 * Make sure indexGuns() has been created!
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void xpopulateGun() throws FileNotFoundException, IOException{
		StringTree s = StringTree.fromStream(new FileInputStream("assets/text/guns.rst"));
		for (String child : s.childSet()){
			BasicGun current = new BasicGun();
			current.setCoolDown(Integer.parseInt(s.getValue(child, "cooldown")));
			current.setCost(Integer.parseInt(s.getValue(child, "cost")));
			current.setImg(indexImages.get(s.getValue(child, "img")).copy());
			current.setWeight(Integer.parseInt(s.getValue(child, "weight")));
			current.setProj(indexShot.get(s.getValue(child, "proj")));
			current.setName(child);
			indexGuns.put(child, current);
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
	public void xpopulateEngine() throws FileNotFoundException, IOException{
		StringTree s = StringTree.fromStream(new FileInputStream("assets/text/motors.rst"));
		for (String child : s.childSet()){
			BasicEngine e = new BasicEngine();
			e.setName(child);
			e.setCost(Integer.parseInt(s.getValue(child, "cost")));
			e.setWeight(Integer.parseInt(s.getValue(child, "weight")));
			e.setTurnrate(Float.parseFloat(s.getValue(child, "turnrate")));
			e.setThrustX(Float.parseFloat(s.getValue(child, "thrustx")));
			e.setThrustY(Float.parseFloat(s.getValue(child, "thrusty")));
			e.setStrafeRate(Float.parseFloat(s.getValue(child, "strafeRate")));
			e.setInGameImg(indexImages.get(s.getValue(child, "img")).copy());
			indexEng.put(child, e);
		}
		
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
	 * Load all shot types
	 * populate map with instances
	 */
	public void xpopulateShot() throws FileNotFoundException, IOException{
		StringTree s = StringTree.fromStream(new FileInputStream("assets/text/shots.rst"));
		for (String child : s.childSet()){
			BasicShot h = new BasicShot();
			h.setImg(indexImages.get(s.getValue(child, "img")).copy());
			h.setDamage(Integer.parseInt(s.getValue(child, "dmg")));
			h.setSpeed(Float.parseFloat(s.getValue(child, "speed")));
			h.setInterval(Integer.parseInt(s.getValue(child, "life")));
			h.setCollider(parseShape(s, child, "collider"));
			indexShot.put(child, h);
		}
	}
	/**
	 * simple get shot method
	 * @return BasicShot
	 */
	public BasicShot getShot(String index){
		return indexShot.get(index);
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
	
	/**
	 * Extract a shape from an rst.
	 * Note that this can be from anywhere inside a tree, just specify the path it's 'name' argument.
	 * 
	 * See shapes.txt
	 * 
	 * @param t    StringTree to read from
	 * @param name Node path to the node you want a shape from
	 */
	public static Shape parseShape(StringTree t, String... name){
		//No switch on string?  Pete, update your Java ffs
		String type = t.getValue(cat(name, "type"));
		if( type.equals("rect")){
			
		} else if(type.equals("line")){
			return new Rectangle(
					fft(t,"x",name),
					fft(t,"y",name),
					fft(t,"w",name),
					fft(t,"h",name));
		} else if(type.equals("circle")){
			if(t.childSet(name).contains("segments")){
				return new Circle(
						fft(t, "x", name),
						fft(t, "y", name),
						fft(t, "radius", name),
						Integer.parseInt(t.getValue(cat(name, "segments"))));
			} else {
				return new Circle(
						fft(t, "x", name),
						fft(t, "y", name),
						fft(t, "radius", name));
			}
		} else if(type.equals("poly")){
			Polygon toSender = new Polygon();
			String[] pointsList = cat(name, "points");
			for(String i : t.childSet(pointsList)){
				String[] current = cat(pointsList,i);
				toSender.addPoint(fft(t, "0",current),fft(t, "1", current));
			}
			return toSender;
		}
		System.out.print(" * DATA ERROR: SHAPE AT " + name.toString() + " has invalid type ''" + type + "''");
		return new Rectangle(0,0,0,0);
	}
	/**
	 * Add a single String to the end of an array of strings-
	 * complex enough to stuff into it's own method.
	 * 
	 * Named cat after the catenate command (and since it's so short)
	 */
	public static String[] cat(String[] train, String caboose){
		String[] toSender = new String[train.length + 1];
		System.arraycopy(train, 0, toSender, 0, train.length);
		toSender[train.length] = caboose; //This makes sense
		return toSender;
	}
	
	/**
	 * Float From Tree-
	 * Gets a float from a value from a path that it cats together.
	 * For brevity.
	 */
	private static float fft(StringTree t, String value, String... path){
		return Float.parseFloat(t.getValue(cat(path, value)));
	}
}
