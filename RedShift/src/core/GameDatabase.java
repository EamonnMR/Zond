package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import ents.BasicArmor;
import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;
import ents.BasicShot;
import ents.ShipDesc;

/**
 * Master Database for media resources
 * loads resources, populates maps with images
 * ultimately a batch load approach is warranted, but a procedural will work
 * for development
 * 
 * Update:
 * Batch loading implemented.
 * This class has evolved into a monster.  It would probably be sane to break this into
 * smaller classes somewhere down the line.  Probably staying a monster for RedShift 1 though.
 * 
 * Also, StringTree now does most of the work of lexing an RST, this parses from the actual tree
 * it creates.  You'll notice that early on, it uses the tree in a fairly simple way, but by 
 * the time it reaches the level loader, it starts to warrant a different approach.
 * 
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
	private Map<String, Sound> indexSounds;
	
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
		indexSounds = new HashMap<String, Sound>();
		try {
			try {
				xloadImages();
				loadSounds();
			} catch (SlickException e) {
				System.out.println("Problem loading image/sound)");
				e.printStackTrace();
			}
		} catch (IOException e){
			System.out.println("Problem opening images/sounds file)");
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
	* Loads all sound files
	* @throws FileNotFoundException
	* @throws IOException
	* @throws SlickException
	*/
	private void loadSounds() throws FileNotFoundException, IOException, SlickException {
		StringTree s = StringTree.fromStream(new FileInputStream("assets/text/sounds.rst"));
		for (String child : s.childSet()){
			ldSnd(child, s.getValue(child));
			System.out.println("Name ''" + child + "'' Location: ''" + s.getValue(child) + "''.");
		}
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
		xpopulateShips();
	}

	private void ldSnd(String name, String location) {
		try{
		indexSounds.put(name, new Sound(location));
			System.out.println("Loaded ''" + name + "'' at location: ''" + location + "''.");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	public Sound getSound(String s){
		return indexSounds.get(s);
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
			current.setFireSnd(indexSounds.get(s.getValue(child, "fireSnd")));
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
	public void xpopulateShips() throws FileNotFoundException, IOException{
		StringTree s = StringTree.fromStream(new FileInputStream("assets/text/ships.rst"));
		for (String child : s.childSet()){
			BasicShip m = new BasicShip();
			m.setImg(indexImages.get(s.getValue(child, "img")).copy());
			m.setHealth(Integer.parseInt(s.getValue(child, "health")));
			m.setPoints(Integer.parseInt(s.getValue(child, "points")));
			m.setTotalWeight(Integer.parseInt(s.getValue(child, "weight")));
			m.setGunPtLength(Integer.parseInt(s.getValue(child, "gunPtLen")));
			m.setEngPtLength(Integer.parseInt(s.getValue(child, "engPtLen")));
			m.setFaction(Integer.parseInt(s.getValue(child, "faction")));
			m.setCollider(parseShape(s, child, "collider"));
			m.setRadarRadius((Circle)parseShape(s, child, "radar"));
			m.setName(child);
			m.setDeathSnd(indexSounds.get(s.getValue(child, "deadsnd")));
			indexShip.put(child, m);
		}
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
			e.setPrimeThrust(indexSounds.get(s.getValue(child, "primeThrst")));
			e.setSideThrust(indexSounds.get(s.getValue(child, "sideThrst")));
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
			h.setSnd(indexSounds.get(s.getValue(child, "snd")));
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
			return new Rectangle(
					fft(t,"x",name),
					fft(t,"y",name),
					fft(t,"w",name),
					fft(t,"h",name));
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
		System.arraycopy(train, 0, toSender,  0, train.length);
		toSender[train.length] = caboose; //This makes sense
		return toSender;
	}
	
	/**
	 * Float From Tree-
	 * Gets a float from a value from a path that it cats together.
	 * For brevity.
	 */
	private static float fft(StringTree t, String value, String... path){
		return Float.parseFloat(gv(t, path, value));
	}
	
	/**
	 * Abbreviated Get value from tree
	 * Use when possible to make code readable.
	 */
	private static String gv(StringTree sourceTree, String[] sourcePath, String last){
		return sourceTree.getValue(cat(sourcePath, last));
	}
	
	
	private static double[] parsePoint(String str){
		String[] numbers =  str.split("[[:space:]]");
		double[] toSender = new double[ numbers.length ];
		for (int i = 0; i == numbers.length - 1; i++){
			toSender[i] = Double.parseDouble(numbers[i]);
		}
		return toSender;

	}
	
	private static boolean parseBool(String from, String... path) {
		String truth = "1 true TRUE True yes YES Yes y T t";
		String falsehood = "0 false FALSE False no NO n No F f";
		if (from.equals("")){
			System.out.print("SEMANTIC ERROR: ");
			System.out.print(path);
			System.out.print("IS BLANK.\n");
			return false;
		} else if (falsehood.contains(from)) return false;
		else if (truth.contains(from)) return true;
		else{
			System.out.print("SEMANTIC ERROR: ");
			System.out.print(from);
			System.out.print("IS NITHER TRUE NOR FALSE.");
			return false;
		}
	}
	
	private static ShipDesc getShipDesc(StringTree t){
		//Extract each field from the RST
		//This should make sense by now
		return new ShipDesc(t.getValue("kind"),
				t.getValue("gun"),
				t.getValue("engine"),
				parsePoint(t.getValue("loc")),
				getEffect(t.getSubTree("deatheffects")));
	}
	
	private static boolean getBool(StringTree t) {
		//SURGEON GENERAL'S WARNING:
		//THAT STRINGTREE IS AT A LEAF NODE
		//DO NOT CALL GETVALUE WITH ARGUMENTS!
		return parseBool(t.getValue(), t.getPath());
	}
	
	private static effects.Effect getEffect(StringTree t){
		//We need to upgrade to Java 7
		//It can do a switch(string)
		//Which would make this code much less ugly.
		String type = t.getValue("type");
		if(type.equals("defeat")){
			return new effects.Defeat();
			
		} else if (type.equals("action")){
			String flags = t.getValue("flags"); //These are tested in the below "contains" calls
			return new effects.ModAction(t.getValue("target"),
					flags.contains("ini"), 
					flags.contains("fire"),
					getBool(t.getSubTree("done")));
			
		} else if(type.equals("navpoint")){
			return new effects.ModNavPoint(t.getValue("target"),
					getBool(t.getSubTree("newstate")));
			
		} else if(type.equals("objective")){
			return new effects.ModObjective(t.getValue("target"), 
					getBool(t.getSubTree("newstate")),
					getBool(t.getSubTree("newcompl")));
			
		}else if(type.equals("modtrig")){
			return new effects.ModTrig(t.getValue("target"),
					getBool(t.getSubTree("newstate")));
			
		} else if(type.equals("multi")){
			//This one is clearly the most fun
			int numberOfFx = t.childSet("effects").size();
			effects.Effect[] fx = new effects.Effect[numberOfFx];
			for(int i = 0; i == numberOfFx; i++){
				//This relies on the fact that an anon list's names are 0, 1, 2, etc.
				fx[i] = getEffect(t.getSubTree("effects", Integer.toString(i)));
				//Note that the args to getSubTree are parsed from (a, b, c) to {a, b, c}
			}
			return new effects.Multi(fx);
			
		} else if(type.equals("noop")){
			return new effects.NoOp();
			
		} else if(type.equals("spawn")){
			return new effects.Spawn(getShipDesc(t.getSubTree("ship")));
			
		} else if(type.equals("victory")){
			return new effects.Victory();
			
		}
		//Unrecognized type.  Returns a no-op
		//just to try and survive, but prints an error.
		System.out.print("SEMANTIC ERROR: EFFECT TYPE ''");
		System.out.print(type);
		System.out.print("UNRECOGNIZED.  RETURNING No-Op.");
		return new effects.NoOp();
	}

}
