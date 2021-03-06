package core;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import level.LevelDataModel;
import level.LevelMetaData;
import level.LevelObjective;
import level.NavPoint;
import level.TriggerFactory;
import level.triggers.BasicTrigger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;

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
	private HashMap<String, BasicGun> indexGuns;
	private HashMap<String, BasicEngine> indexEng;
	private HashMap<String, BasicShot> indexShot;
	private HashMap<String, Sound> indexSounds;
	private HashMap<String, SpriteSheetFont> indexFonts;
	private HashMap<String, File> indexLevelFiles;
	private HashMap<String, LevelMetaData> indexLevelMeta;
	private HashMap<String, StringTree> indexScenarios;
	private HashMap<String, Animation> indexAnimations;
	private HashMap<String, ConfigurableEmitter> indexParticles;
	private SpriteSheet greenAlphaNms;
	private SpriteSheet grayAlphaNms;
	private SpriteSheetFont greenFont;
	private SpriteSheetFont grayFont;
	private ParticleIO prtclIO;
	private static GameDatabase GAMEDB;
	
	public static GameDatabase get(){
		//NOW ITS A SINGLETON!!!
		return GAMEDB;
	}
	//constructor
	public GameDatabase(){
		GAMEDB = this;
	}
	
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
		indexLevelMeta = new HashMap<String, LevelMetaData>();
		indexScenarios = new HashMap<String, StringTree>();
		indexParticles = new HashMap<String, ConfigurableEmitter>();
		indexAnimations = new HashMap<String, Animation>();
		indexShot = new HashMap<String, BasicShot>();
		indexGuns = new HashMap<String, BasicGun>();
		indexEng = new HashMap<String, BasicEngine>();
		indexShip = new HashMap<String, BasicShip>();
		indexFonts = new HashMap<String, SpriteSheetFont>();
		try {
			try {
				xloadImages();
				loadSounds();
				loadLevelFiles();
				loadParticles();
				loadAnimations();
			} catch (SlickException e) {
				System.out.println("Problem loading image/sound)");
				e.printStackTrace();
			}
		} catch (IOException e){
			System.out.println("Problem opening images/sounds file)");
			e.printStackTrace();
		}
		populateGameObjects();
	}

	public static StringTree loadRst(String from) throws FileNotFoundException, IOException{
		return StringTree.fromStream(new FileInputStream(from));
	}
	
	/**
	 * populates all indices with their respective resources
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public void populateGameObjects() throws FileNotFoundException, IOException{
		xpopulateShot();
		xpopulateEngine();
		xpopulateGun(); //Now you're cooking with external data!
		xpopulateShips();
		populateFonts();
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
		}
	}
	
	private void ldSnd(String name, String location) {
		try{
		indexSounds.put(name, new Sound(location));
			System.out.println("Loaded Sound:: "+ name);
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
	
	private void loadAnimations()  throws SlickException, FileNotFoundException, IOException{
		StringTree s = loadRst("assets/text/anims.rst");
		for(String child : s.childSet()){
			ldAnim(child, s.getSubTree(child));
		}
	}
	
	private void ldAnim(String child, StringTree value) {
		try{
			SpriteSheet s = new SpriteSheet(new Image(value.getValue("img")),
											Integer.valueOf(value.getValue("w")),
											Integer.valueOf(value.getValue("h")));
			Animation a = new Animation(s, Integer.valueOf(value.getValue("rate")));
			a.setLooping(false);
			indexAnimations.put(child, a);
			System.out.println("Loaded Animation:: "+ a);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Animation getAnimation(String pointer){
		return indexAnimations.get(pointer);
	}

	private void loadParticles() throws SlickException, FileNotFoundException, IOException{
		StringTree s = loadRst("assets/text/particles.rst");
		for(String child : s.childSet()){
			File xmlFile = new File(s.getValue(child));
			ConfigurableEmitter tEmitter = ParticleIO.loadEmitter(xmlFile);
			tEmitter.setEnabled(false);
			indexParticles.put(child, tEmitter);
			System.out.println("Loaded Particle:: "+ tEmitter.name);
		}
		
	}
	
	public HashMap<String,ConfigurableEmitter> getAllParticles(){
		return indexParticles;
	}
	
	public ConfigurableEmitter getParticle(String pointer){
		return indexParticles.get(pointer);
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
		StringTree s = loadRst("assets/text/items/guns.rst");
		for (String child : s.childSet()){
			BasicGun current = new BasicGun();
			current.setCoolDown(Integer.parseInt(s.getValue(child, "cooldown")));
			current.setImg(indexImages.get(s.getValue(child, "img")).copy());
			current.setWeight(Integer.parseInt(s.getValue(child, "weight")));
			current.setProj(indexShot.get(s.getValue(child, "proj")));
			current.setName(child);
			current.setUiName(s.getValue(child,"name"));
			current.setToolTip(s.getValue(child,"tltip"));
			current.setFireSnd(indexSounds.get(s.getValue(child, "fireSnd")));
			current.setWireframe(indexImages.get(s.getValue(child, "wire")));
			current.setMzlImg(indexImages.get(s.getValue(child, "firespr")).copy());
			current.setSpread(Integer.valueOf(s.getValue(child, "spread")));
			indexGuns.put(child, current);
			System.out.println("Loaded Item:: Gun - "+current.getName());
		}
	}
	
	/**
	 * simple get gun method
	 * @return BasicGun
	 */
	public BasicGun getGun(String index){
		return indexGuns.get(index);
	}
	
	/**
	 * Load an image into the database
	 * @param name       It's name in the DB
	 * @param location   Where to load the file from 
	 */
	public void ldImg(String name, String location) throws SlickException{
		try{
			indexImages.put(name, new Image(location) );
			
			System.out.println("Loaded Image:: "+name);
		} catch (SlickException e){
			System.out.println("Failed on Image:: "+name);
			e.printStackTrace();
		}
	}

	
	/**
	 *build all ship instances
	 * populate map with instances
	 */
	public void xpopulateShips() throws FileNotFoundException, IOException{
		StringTree s = loadRst("assets/text/items/ships.rst");
		for (String child : s.childSet()){
			BasicShip m = new BasicShip();
			m.setImg(indexImages.get(s.getValue(child, "img")).copy());
			m.setHealth(Integer.parseInt(s.getValue(child, "health")));
			m.setTotalWeight(Integer.parseInt(s.getValue(child, "weight")));
			m.setGunPtLength(Integer.parseInt(s.getValue(child, "gunPtLen")));
			m.setEngPtLength(Integer.parseInt(s.getValue(child, "engPtLen")));
			m.setFaction(Integer.parseInt(s.getValue(child, "faction")));
			m.setCollider(parseShape(s, child, "collider"));
			m.setRadarShape((Circle)parseShape(s, child, "radar"));
			m.setName(child);
			m.setToolTip(s.getValue(child,"tltip"));
			m.setDeathSnd(indexSounds.get(s.getValue(child, "deadsnd")));
			m.setWireframe(indexImages.get(s.getValue(child, "wire")));
			indexShip.put(child, m);
			System.out.println("Loaded Item:: Ship - " + m.getName());
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
		StringTree s = loadRst("assets/text/items/motors.rst");
		for (String child : s.childSet()){
			BasicEngine e = new BasicEngine();
			e.setName(child);
			e.setUiName(s.getValue(child,"name"));
			e.setToolTip(s.getValue(child,"tltip"));
			e.setWeight(Integer.parseInt(s.getValue(child, "weight")));
			e.setTurnrate(Float.parseFloat(s.getValue(child, "turnrate")));
			e.setThrustX(Float.parseFloat(s.getValue(child, "thrustx")));
			e.setThrustY(Float.parseFloat(s.getValue(child, "thrusty")));
			e.setStrafeRate(Float.parseFloat(s.getValue(child, "strafeRate")));
			e.setInGameImg(indexImages.get(s.getValue(child, "img")).copy());
			e.setPrimeThrust(indexSounds.get(s.getValue(child, "primeThrst")));
			e.setSideThrust(indexSounds.get(s.getValue(child, "sideThrst")));
			e.setThrstPrtcl(indexParticles.get(s.getValue(child, "thrstprtcl")));
			e.setWireFrame(indexImages.get(s.getValue(child, "wireframe")));
			indexEng.put(child, e);
			System.out.println("Loaded Item:: Engine - " + e.getName());
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
		StringTree s = loadRst("assets/text/items/shots.rst");
		for (String child : s.childSet()){
			BasicShot h = new BasicShot();
			h.setImg(indexImages.get(s.getValue(child, "img")).copy());
			h.setDamage(Integer.parseInt(s.getValue(child, "dmg")));
			h.setSpeed(Float.parseFloat(s.getValue(child, "speed")));
			h.setInterval(Integer.parseInt(s.getValue(child, "life")));
			h.setSnd(indexSounds.get(s.getValue(child, "snd")));
			h.setCollider(parseShape(s, child, "collider"));
			h.setImpactPrtl(s.getValue(child,"impFx"));
			indexShot.put(child, h);
			System.out.println("Loaded Item:: Shot - " + h.getImg().getName());
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
	
	public Set<String> getScenarios(){
		return indexScenarios.keySet();
	}
	
	
	/**
	 * Opens LevelList.rst and walks through, parsing files into StringTrees
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SlickException
	 */
	public void loadLevelFiles()throws FileNotFoundException, IOException, SlickException{
		StringTree s = loadRst("assets/text/levellist.rst");
		for (String child : s.childSet()){
			parseLevelFile(child, s.getValue(child));
		}
	}
	
	/**
	 * one-off method that loads a StringTree from a file and puts it into the indexScenarios
	 * method also creates an associated LevelMetaData class for ui
	 * @param name
	 * @param location
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SlickException
	 */
	private void parseLevelFile(String name, String location) throws FileNotFoundException, IOException, SlickException {
		StringTree level = loadRst(location);
		indexScenarios.put(name, level);
		System.out.println("Loaded LevelTree:: "+ level.getValue("uiname"));
		indexLevelMeta.put(name, buildMetaData(name,level));
	}
	
	/**
	 * constructs a LevelMetaData class for the associated level 
	 * @param level
	 * @return
	 */
	private LevelMetaData buildMetaData(String filename,StringTree level) {
		LevelMetaData lmd = new LevelMetaData();
		lmd.setName(filename);
		lmd.setUiname(level.getValue("uiname"));
		lmd.setFaction(Integer.valueOf(level.getValue("faction")));
		lmd.setTltip(level.getValue("tltip"));
		lmd.setDescrip(level.getValue("desc"));
		lmd.setLevelType(level.getValue("levelType"));
		lmd.setClient_Ships(parseClientInfo(level.getSubTree("plrShips")));
		lmd.setClient_Guns(parseClientInfo(level.getSubTree("plrGuns")));
		lmd.setClient_Engines(parseClientInfo(level.getSubTree("plrMotors")));
		//FIXME: get level objectives to copy over correctly
		HashMap<String, LevelObjective> tempObjs = parseObjectives(level.getSubTree("objectives"));
		lmd.setObjectives(tempObjs);
		System.out.println("Loaded LevelMetaData:: "+ lmd.getUiname() + " MetaData");
		return lmd;
	}
	
	public HashMap<String, LevelMetaData> getAllLevelMetaData(){
		return indexLevelMeta;
	}
	
	public LevelMetaData getSingleMetaData(String s){
		return indexLevelMeta.get(s);
	}
	
	/**
	 * here's the problem, because of pass by reference gameplay is not resetting the levels properly
	 * i found that if we just call a single BuildLevel every time we reset the gameplay state, then
	 * we're good to go. This results however in loading level data twice, essentially.
	 * @param trigFac
	 * @param name
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public LevelDataModel buildLevel(TriggerFactory trigFac, String name) throws FileNotFoundException, IOException{
//		StringTree s = loadRst(indexLevelFiles.get(name).getPath());
		StringTree s = indexScenarios.get(name);
		LevelDataModel level = new LevelDataModel(s.getValue("filename"));
		
		//set the file name
		level.setName(s.getValue("uiname"));
		
		//set the faction
		level.setFaction(Integer.valueOf(s.getValue("faction")));
		
		//set the tooltip for the ui
		level.setToolTip(s.getValue("tltip"));
		
		//short description for briefing
		level.setUIDesc(s.getValue("desc"));
		
		//Get the name of the music to use
		level.setMusic(s.getValue( "music"));
		
		//set the spawn point
		level.setSpawn( new Point(Integer.valueOf(s.getValue("spawnX")),Integer.valueOf(s.getValue("spawnY"))));
		
		//set the play area
		level.setActiveArea(parseShape(s, "active"));
		
		//set the warning area
		level.setWarnArea(parseShape(s, "margin"));
		
		//create nav points
		level.setNavMap(parseNavPoints(s.getSubTree("navpoints")));
		
		//create objectives
		level.setObjectives(parseObjectives(s.getSubTree("objectives")));
		
		//Create the trigger set
		level.setTriggerMap(parseTriggers(trigFac, s.getSubTree("triggers")));
		
		System.out.println("Loaded Scenario: " + indexScenarios.get(name));
		return level;
	}
	
	private String[] parseClientInfo(StringTree t) {
		String[] toSender = new String[t.childSet().size()];
		for(int i=0; i< toSender.length; i++){
				toSender[i] = t.getValue("item"+i);
		}
		return toSender;
	}

	/**
	 * parses the listed nav points in the rust file into NavPoint objects
	 * @param topLevel
	 * @return HashMap String, NavPoint
	 */
	private HashMap<String, NavPoint> parseNavPoints(StringTree topLevel){
		HashMap<String, NavPoint> points = new HashMap<String, NavPoint>();
		for(String s : topLevel.childSet()){
			StringTree node = topLevel.getSubTree(s);
			points.put(node.getValue("name"), new NavPoint(Integer.valueOf(node.getValue("x")),
															Integer.valueOf(node.getValue("y")),
															node.getValue("name"),
															parseBool(node.getValue("state"))));
		}
		return points;
	}
	
	/**
	 * parses the listed objectives in the level rust file into actual LevelObjective objects
	 * @param tr
	 * @return HashMap String, LevelObjective
	 */
	private HashMap<String, LevelObjective> parseObjectives(StringTree tr) {
		HashMap<String, LevelObjective> objs = new HashMap<String, LevelObjective>();
		
		for(String s : tr.childSet()){
			StringTree sub = tr.getSubTree(s);
			LevelObjective lo = new LevelObjective(sub.getValue("name"), 
													sub.getValue("tltip"), 
													sub.getValue("desc"),
													checkStringNull(sub.getValue("target")),
													parseBool(sub.getValue("state")));
			objs.put(lo.getName(), lo);
		}
		
		return objs;
	}

	private HashMap<String, ShipDesc> parseShipList(StringTree t) {
		HashMap<String, ShipDesc> toSender = new HashMap<String, ShipDesc>();
		for(String ch : t.childSet()){
			toSender.put(ch, getShipDesc(t.getSubTree(ch)));
		}
		return toSender;
	}

	/**
	 * parses the listed triggers into actual trigger objects
	 * @param trigFac
	 * @param tr
	 * @return HashMap String, BasicTrigger
	 */
	private HashMap<String, BasicTrigger> parseTriggers(TriggerFactory trigFac, StringTree tr) {
		HashMap<String, BasicTrigger> trigs = new HashMap<String, BasicTrigger>();
		ShipDesc d = null; //Won't be used unless it's actually needed-the null
		//one is a placeholder that's sent to buildTrig.
		
		for(String s : tr.childSet()){
			//I'm a baaaaad person
			StringTree t = tr.getSubTree(s);
			//To understand this properly, look at TriggerFactory
			String typeClass = t.getValue("type");
			String[] argList = Strings(t.getValue("trigtype"),
					t.getValue("name"),
					t.getValue("x"),
					t.getValue("y"),
					t.getValue("target"),
					t.getValue("trigstate"));
			if(typeClass.equals("spawn")){
				d = getShipDesc(t.getSubTree("toSpawn"));
			} else if(typeClass.equals("togglenav")){
				argList = catEnMasse(argList,
							t.getValue("navPointName"),
							t.getValue("setstate"));
			}else if(typeClass.equals("count")){
				argList = cat(argList,
							t.getValue("total"));
			}else if(typeClass.equals("multrig")){
				//subtree this
				argList = catEnMasse(argList, 
							parseTrigTargs(t.getSubTree("targets")));
			}else if(typeClass.equals("endobj")){
				argList = cat(argList, t.getValue("objective"));
			}else if(typeClass.equals("togobj")){
				argList = catEnMasse(argList, 
									t.getValue("objective"),
									t.getValue("state"));
			}else if(typeClass.equals("iwin")){
				argList = cat(argList, t.getValue("state"));
			}
			
			parseShape(t, "collider");
			trigs.put(argList[1], trigFac.buildTrigger(parseShape(t, "collider"), d, typeClass, argList));
		}
		return trigs;
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
	 * Named cat after the concatenate command (and since it's so short)
	 */
	public static String[] cat(String[] train, String caboose){
		String[] toSender = new String[train.length + 1];
		System.arraycopy(train, 0, toSender,  0, train.length);
		toSender[train.length] = caboose; //This makes sense
		return toSender;
	}
	
	/**
	 * takes a string array and a string, and places the string at the end of the array
	 * @param train
	 * @param caboose
	 */
	public static void catIp(String[] train, String caboose){
		train = cat(train, caboose);
	}
	
	/**
	 * takes two string arrays can puts them together sequentially
	 * @param train
	 * @param caboose
	 * @return
	 */
	public static String[] catEnMasse(String[] train, String... caboose){
		String[] toSender = new String[train.length + caboose.length];
		System.arraycopy(train, 0, toSender,  0, train.length);
//		System.arraycopy(caboose, 0, toSender,  train.length - 1, caboose.length);	//i get hung up on array positions too sometimes, but i dont think its len-1
		System.arraycopy(caboose, 0, toSender,  train.length, caboose.length);
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
	
	/**
	 * creates a <link>java.awt.Point</link> from a strings
	 * @param str
	 * @return
	 */
	private static double[] parsePoint(String str){
		String[] numbers =  str.split(" ");
		double[] toSender = new double[ numbers.length ];
		if(numbers.length < 2){
			throw new SemanticError("Point ''" + str + "'' is mal-formed; not enough numbers.");
		}
		for (int i = 0; i < numbers.length; i++){
			toSender[i] = Double.parseDouble(numbers[i]);
		}
		return toSender;
	}
	
	/**
	 * builds a ship desc
	 * @param t
	 * @return
	 */
	private static ShipDesc getShipDesc(StringTree t){
		//Extract each field from the RST
		//This should make sense by now
		return new ShipDesc(t.getValue("kind"),
				checkStringNull(t.getValue("gun")),
				checkStringNull(t.getValue("engine")),
				parsePoint(t.getValue("loc")),
				//getEffect(t.getSubTree("deatheffects")),
				parseBool(t.getValue("isAi")),
				checkStringNull(t.getValue("deathtrig")));
	}
	
	/**
	 * returns a bool primitive based on incoming string
	 * @param value
	 * @return
	 */
	private static boolean parseBool(String value){
		if (value.equals("t") || value.equals("true")){
			return true;
		} else if (value.equals("f") || value.equals("false")){
			return false;
		} else {
			throw new SemanticError("value ''" + value + "'' is not a boolean value.");
		}
		
	}
	
	/**
	 * creates a null if a blank string is found
	 * @param toCheck
	 * @return
	 */
	private static String checkStringNull(String toCheck){
		if(toCheck.equals("")){
			return null;
		}else{
			return toCheck;
		}
	}
	
	/**
	 * a one-off method that populates a multitrigger's targetname list with targets
	 * @param t
	 * @return
	 */
	private static String[] parseTrigTargs(StringTree t) {
		String[] toSender = new String[t.childSet().size()];
		for(int i=0; i< toSender.length; i++){
				toSender[i] = t.getValue("targ"+i);
		}
		return toSender;
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
