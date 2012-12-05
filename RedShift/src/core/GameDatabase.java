package core;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;

import level.LevelDataModel;
import level.TriggerFactory;
import level.triggers.BasicTrigger;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;
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
	private Map<String, SpriteSheetFont> indexFonts;
	private Map<String, File> indexLevelFiles;
	private Map<String, LevelDataModel> indexScenarios;
	private SpriteSheet greenAlphaNms;
	private SpriteSheet grayAlphaNms;
	private SpriteSheetFont greenFont;
	private SpriteSheetFont grayFont;
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
		indexLevelFiles = new HashMap<String, File>();
		try {
			try {
				xloadImages();
				loadSounds();
				loadLevelFiles();
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
		indexFonts = new HashMap<String, SpriteSheetFont>();
		indexScenarios = new HashMap<String, LevelDataModel>();
		populateAll();
	}
	
	public static StringTree loadRst(String from) throws FileNotFoundException, IOException{
		return StringTree.fromStream(new FileInputStream(from));
	}
	/**
	* Loads all sound files
	* @throws FileNotFoundException
	* @throws IOException
	* @throws SlickException
	*/
	private void loadSounds() throws FileNotFoundException, IOException, SlickException {
		StringTree s = loadRst("assets/text/sounds.rst");
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
		xpopulateEngine();
		xpopulateGun(); //Now you're cooking with external data!
		xpopulateShips();
//		populateLevels();
		populateFonts();
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
		StringTree s = loadRst("assets/text/images.rst");
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
		StringTree s = loadRst("assets/text/guns.rst");
		for (String child : s.childSet()){
			BasicGun current = new BasicGun();
			current.setCoolDown(Integer.parseInt(s.getValue(child, "cooldown")));
			current.setCost(Integer.parseInt(s.getValue(child, "cost")));
			current.setImg(indexImages.get(s.getValue(child, "img")).copy());
			current.setWeight(Integer.parseInt(s.getValue(child, "weight")));
			current.setProj(indexShot.get(s.getValue(child, "proj")));
			current.setName(child);
			current.setUiName(s.getValue(child,"name"));
			current.setToolTip(s.getValue(child,"tltip"));
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
	 * gets an images, whoopee
	 * @param s
	 * @return
	 */
	public Image getIMG(String s){
		return indexImages.get(s);
	}
	
	/**
	 *build all ship instances
	 * populate map with instances
	 */
	public void xpopulateShips() throws FileNotFoundException, IOException{
		StringTree s = loadRst("assets/text/ships.rst");
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
			m.setToolTip(s.getValue(child,"tltip"));
			m.setDeathSnd(indexSounds.get(s.getValue(child, "deadsnd")));
			m.setWireframe(indexImages.get(s.getValue(child, "wire")));
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
		StringTree s = loadRst("assets/text/motors.rst");
		for (String child : s.childSet()){
			BasicEngine e = new BasicEngine();
			e.setName(child);
			e.setUiName(s.getValue(child,"name"));
			e.setToolTip(s.getValue(child,"tltip"));
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
		StringTree s = loadRst("assets/text/shots.rst");
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
		//XXX: more like, is armor every going to be in here?
	}
	
	/**
	 * simple get armor method
	 * @return BasicArmor
	 */
	public BasicArmor getArmor(String index){
		return indexArmor.get(index);
	}
	
	
	/**
	 * creates the fonts out of sprite sheets
	 */
	public void populateFonts(){
		greenAlphaNms = new SpriteSheet(getIMG("grnAlphNm"), 12, 17);
		greenFont = new SpriteSheetFont(greenAlphaNms, ' ');
		indexFonts.put("green", greenFont);
		
		grayAlphaNms = new SpriteSheet(getIMG("graAlphNm"), 12, 17);
		grayFont = new SpriteSheetFont(grayAlphaNms, ' ');
		indexFonts.put("gray", grayFont);
	}

	/**
	 * get a font in the form of a sprite sheet font
	 * @param s
	 * @return
	 */
	public SpriteSheetFont getFont(String s){
		return indexFonts.get(s);
	}
	
	public LevelDataModel getScenario(String pointer){
		return indexScenarios.get(pointer);
	}
	
	public HashMap<String, LevelDataModel> getScenarios(){
		return (HashMap<String, LevelDataModel>) indexScenarios;
	}
	/**
	 * NOTE:: the trigFac references the entFac, which is out of load flow at this stage
	 * so dont call this internally in gdb, loader state will call it
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void populateLevels(TriggerFactory trigFac) throws FileNotFoundException, IOException {
		for(File f : indexLevelFiles.values()){
			StringTree s = loadRst(f.getAbsolutePath());
			LevelDataModel level = new LevelDataModel(s.getValue("name" ));
			//set the faction
			level.setFaction(Integer.valueOf(s.getValue("faction")));
			//set the play area
			level.setActiveArea(parseShape(s, "active"));
			//set the warning area
			level.setActiveArea(parseShape(s, "margin"));
			//set the spawn point
			level.setSpawn( new Point(Integer.valueOf(s.getValue("spawnX")),Integer.valueOf(s.getValue("spawnY"))));
			//Get the name of the music to use
			level.setMusic(s.getValue( "music"));

			//Create the trigger set
			level.setTriggerMap(parseTriggers(trigFac, s.getSubTree("triggers")));

			//create nav points
//			level.setNavMap(parseNavPoints());
			
			indexScenarios.put(level.getName(),level);
			System.out.println("Loaded ''" + f.getName() + "'' at location: ''" + f.getAbsolutePath() + "''.");
		}
	}
	
	private HashMap<String, ShipDesc> parseShipList(StringTree t) {
		HashMap<String, ShipDesc> toSender = new HashMap<String, ShipDesc>();
		for(String ch : t.childSet()){
			toSender.put(ch, getShipDesc(t.getSubTree(ch)));
		}
		return toSender;
	}

	private HashMap<String, BasicTrigger> parseTriggers(TriggerFactory trigFac, StringTree tr) {
		HashMap<String, BasicTrigger> trigs = new HashMap<String, BasicTrigger>();
		ShipDesc d = null; //Won't be used unless it's actually needed-the null
		//one is a placeholder that's sent to buildTrig.
		
		for(String s : tr.childSet()){
			//I'm a baaaaad person
			StringTree t = tr.getSubTree(s);
			//To understand this properly, look at TriggerFactory
			String typeClass = t.getValue("type"); //XXX: was "class" but does not match rst file
			String[] argList = Strings(t.getValue("trigtype"),
					t.getValue("name"),
					t.getValue("x"),
					t.getValue("y"),
					t.getValue("target") );
			if(typeClass.equals("spawn")){
				d = getShipDesc(t.getSubTree("toSpawn"));
			} else if(typeClass.equals("togglenav")){
				catEnMasse(argList,t.getValue("x"),
				t.getValue("y"),
				t.getValue("navPointName"),
				t.getValue("initialState"));
			}
			
			parseShape(t, "collider");
			//FIXME: Dyke any refrences to the entity factory out of the trigger factory
			trigs.put(argList[1], trigFac.buildTrigger(parseShape(t, "collider"), d, typeClass, argList));
		}
		return trigs;
	}

	public void loadLevelFiles()throws FileNotFoundException, IOException, SlickException{
		StringTree s = loadRst("assets/text/levellist.rst");
		for (String child : s.childSet()){
			ldLevelFile(child, s.getValue(child));
			System.out.println("Name ''" + child + "'' Location: ''" + s.getValue(child) + "''.");
		}
	}
	
	private void ldLevelFile(String name, String location) throws FileNotFoundException, IOException, SlickException {
		System.out.println("Loaded ''" + name + "'' at location: ''" + location + "''.");	
		File f = new File(location);
		indexLevelFiles.put(name, f);
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
					getFloatFromTree(t,"x",name),
					getFloatFromTree(t,"y",name),
					getFloatFromTree(t,"w",name),
					getFloatFromTree(t,"h",name));
		} else if(type.equals("line")){
			return new Rectangle(
					getFloatFromTree(t,"x",name),
					getFloatFromTree(t,"y",name),
					getFloatFromTree(t,"w",name),
					getFloatFromTree(t,"h",name));
		} else if(type.equals("circle")){
			if(t.childSet(name).contains("segments")){
				return new Circle(
						getFloatFromTree(t, "x", name),
						getFloatFromTree(t, "y", name),
						getFloatFromTree(t, "radius", name),
						Integer.parseInt(t.getValue(cat(name, "segments"))));
			} else {
				return new Circle(
						getFloatFromTree(t, "x", name),
						getFloatFromTree(t, "y", name),
						getFloatFromTree(t, "radius", name));
			}
		} else if(type.equals("poly")){
			Polygon toSender = new Polygon();
			String[] pointsList = cat(name, "points");
			for(String i : t.childSet(pointsList)){
				String[] current = cat(pointsList,i);
				toSender.addPoint(getFloatFromTree(t, "0",current),getFloatFromTree(t, "1", current));
			}
			return toSender;
		}
		System.out.print(" * SEMANTIC ERROR: SHAPE AT " + name.toString() + " has invalid type ''" + type + "''");
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
	
	public static void catIp(String[] train, String caboose){
		train = cat(train, caboose);
	}
	
	public static String[] catEnMasse(String[] train, String... caboose){
		String[] toSender = new String[train.length + caboose.length];
		System.arraycopy(train, 0, toSender,  0, train.length);
		System.arraycopy(caboose, 0, toSender,  train.length - 1, caboose.length);
		return toSender;
	}
	
	/**
	 * Float From Tree-
	 * Gets a float from a value from a path that it cats together.
	 * For brevity.
	 */
	private static float getFloatFromTree(StringTree t, String value, String... path){
		return Float.parseFloat(getValue(t, path, value));
	}
	
	/**
	 * Abbreviated Get value from tree
	 * Use when possible to make code readable.
	 */
	private static String getValue(StringTree sourceTree, String[] sourcePath, String last){
		return sourceTree.getValue(cat(sourcePath, last));
	}
	
	
	private static double[] parsePoint(String str){
		String[] numbers =  str.split(" ");
		double[] toSender = new double[ numbers.length ];
		if(numbers.length < 2){
			throw new SemanticError("Point ''" + str + "'' is mal-formed; not enough numbers.");
		}
		for (int i = 0; i == numbers.length - 1; i++){
			toSender[i] = Double.parseDouble(numbers[i]);
		}
		return toSender;
	}
	
	private static ShipDesc getShipDesc(StringTree t){
		//Extract each field from the RST
		//This should make sense by now
		String gun="", engine="";
		if(t.getValue("gun").equals("")){
			gun = null;
		}else {
			gun = t.getValue("gun");
		}
		
		if(t.getValue("engine").equals("")){
			engine = null;
		}else {
			engine = t.getValue("engine");
		}
		
		
		return new ShipDesc(t.getValue("kind"),
				gun,
				engine,
				parsePoint(t.getValue("loc")),
				//getEffect(t.getSubTree("deatheffects")),
				getBoolean(t.getValue("isAi")));
		
	}
	
	private static boolean getBoolean(String value){
		if (value.equals("t") || value.equals("true")){
			return true;
		} else if (value.equals("f") || value.equals("false")){
			return false;
		} else {
			throw new SemanticError("value ''" + value + "'' is not a boolean value.");
		}
		
	}
	
	/**
	 *  This class is only public because it's being tested.
	 *  It won't do you any good to use it.
	 */
	public static effects.Effect getEffect(StringTree t){
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
					getBoolean(t.getValue("done")));
			
		} else if(type.equals("navpoint")){
			return new effects.ModNavPoint(t.getValue("target"),
					getBoolean(t.getValue("newstate")));
			
		} else if(type.equals("objective")){
			return new effects.ModObjective(t.getValue("target"), 
					getBoolean(t.getValue("newstate")),
					getBoolean(t.getValue("newcompl")));
			
		}else if(type.equals("modtrig")){
			return new effects.ModTrig(t.getValue("target"));
			
		} else if(type.equals("multi")){
			//This one is clearly the most fun
			int numberOfFx = t.childSet("effects").size();
			//Get the highest number
			if( numberOfFx == 0){
				throw new SemanticError("Please don't use empty multi triggers.  It's counterproductive.");
			}
			effects.Effect[] fx = new effects.Effect[numberOfFx];
			
			for(int j = 0; j != numberOfFx; j++){
				//This relies on the fact that an anon list's names are 0, 1, 2, etc.
				fx[j] = getEffect(t.getSubTree("effects", Integer.toString(j)));
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
		throw new SemanticError("Effect at has type ''" + type
				+"'' which is not recognized and probably an error.");
	}
	
	public static cond.Condition parseCond(StringTree t){
		String type = t.getValue("type");
		String target = t.getValue("target");
		if (type.equals("counter")){
			return new cond.Counter(Integer.parseInt(t.getValue("total")), target);
		} else if (type.equals("timer")){
			return new cond.Timer(Integer.parseInt(t.getValue("max")), target);
		} else if (type.equals("ship")){
			return new cond.ShipTouch(parseShape(t, "shape"), target);
		} else if (type.equals("shot")){
			return new cond.ShotTouch(parseShape(t, "shape"), target);
		} else {
			throw new SemanticError("Could not recognize condition type ''" + type + "''.");
		}
	}
	
	public static class SemanticError extends RuntimeException{
		/**
		 * The RST formed a tree, but the values aren't in line with the spec.
		 */
		private static final long serialVersionUID = 1L;

		SemanticError(String s){
			super(s + "\n");
		}
	}
	
	//A constructor for string arrays.
	//I'm not proud of this...
	public String[] Strings(String... listOfStrings){
		return listOfStrings;
	}
}
