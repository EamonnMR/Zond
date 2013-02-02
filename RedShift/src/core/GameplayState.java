package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import level.LevelDataModel;
import level.TriggerFactory;
import level.TriggerTypes;
import level.actions.BasicAction;
import level.triggers.BasicTrigger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

import ui.hud.Hud;
import ui.hud.HudDataModel;
import ai.ScanState;
import ents.AIShip;
import ents.BaseEnt;
import ents.BasicShip;
import ents.BasicShot;
import ents.Effect;
import ents.EntityFactory;

/**
 * 
 * @author proohr
 * @version 1.0
 * the core of the active game, handles an instance of play
 */
public class GameplayState extends BasicGameState{
	private static GameplayState IAM;
	
	public static GameplayState getME(){
		return IAM;
	}
	
	public static boolean shipExists(int ship){
		return IAM.ships.containsKey(ship);
	}
	/**
	 * Make sure the ship actually exists before calling this.
	 */
	public static BasicShip getShip(int ship){
		return IAM.ships.get(ship);
	}
	
	//INTERNAL VARIABLES AND DATA============================
	private int id, entCount, objCount, shotCount, eCount, clientCount, winLose;
	private boolean gameOver, gamePlay, gameIni;	//instance internal state
	int camX, camY, boundsCheck, deathReason;
	private LevelDataModel levelData;
	private HudDataModel hdm;
	private String lvlPointer;
	private ParticleSystem particleSys;
	
	//Constants	        //play with these till the tags are centered
	float radius = 350,xoffset,yoffset; //Distance to draw tags from player
	
	HashMap<Integer, BasicShip> ships;		//instance data
	HashMap<Integer, BasicShot> shots;
	HashMap<Integer, BaseEnt> doodads;
	HashMap<Integer, Effect> explosions;
	HashMap<Integer, PlayerClient> clients;
	HashMap<String, BasicShip> incomingClientShips;
	LevelHandler lh;
	
	private ParallaxStarField stars;
	private int lCamx, lCamy;
	//====================================================================
	
	//EXTERNAL VARIABLES AND DATA=========================================
	PlayerClient pc;
	private GameDatabase gdb;
	private EntityFactory entFac;
	private Hud playerHud;
	private Graphics gfx;
	//====================================================================
	
	//constructor
	public GameplayState(int i){
		
		this.id = i;
		this.boundsCheck = 1;
		this.gameOver = false;		
		this.gameIni = true;		
		this.gamePlay = false;
		this.winLose = 0;
		
		//I ARE SINGLETON NOW HURRRR
		IAM = this; //live in destructible times?
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {	
		gfx = arg0.getGraphics();
		stars = new ParallaxStarField(50,1024,768, 50,null, .7f,1);
	}
	
	//lame ini system is lame, so we have our own.
	private void customIni(){
		
		particleSys = new ParticleSystem(gdb.getIMG("basePrt"));
		particleSys.setRemoveCompletedEmitters(true);
		particleSys.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		particleSys.setUsePoints(false);
		
		this.levelData = buildGamePlayLevel();
		this.lh = new LevelHandler(levelData);
		this.ships = new HashMap<Integer, BasicShip>();
		this.shots = new HashMap<Integer, BasicShot>();
		this.doodads = new HashMap<Integer, BaseEnt>();
		this.explosions = new HashMap<Integer, Effect>();
		this.clients = new HashMap<Integer, PlayerClient>();
		this.incomingClientShips = new HashMap<String, BasicShip>();
		this.winLose=0;
		//create the client ship
		if(pc.getPlayShip()==null){
			pc.setPlayShip(entFac.buildShip("mercury", "20mm", "smallEngine", false, null));
		}else{
			pc.setPlayShip(entFac.buildShip(pc.getPlayShip().getName(), pc.getPlayShip().getWeapon().getName(), pc.getPlayShip().getEngine().getName(), false, null));
		}
		pc.getPlayShip().ini(levelData.getSpawn().x, levelData.getSpawn().y, 0.0f);
		playerHud = new Hud(pc, 1023, 767, hdm, gdb);
		pc.setRadarState(true);
		
		addShip(pc.getPlayShip());

		//camera test
		setCamX(0);
		setCamY(0);
	
		gameIni = false;
		gamePlay = true;
		if(!(gdb.getSound(levelData.getMusic())==null)){
			gdb.getSound(levelData.getMusic()).loop(1.0f, pc.getOptions().getMusevol());
		}
		
	}

	/**
	 * render method, everything in the instance is drawn through this method
	 */
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		if(gamePlay){
			//Star render stuff
			//This is 'cause it won't fit in the initializer
			stars.setImg(gdb.getIMG("starImg"));
			
			//Calculate the camera delta and render the stars
			stars.update(camX - lCamx, camY - lCamy, camX, camY);
			lCamx = camX; //Save the old camX and camY values
			lCamy = camY;
			stars.draw(gfx);
			
			if(pc.getOptions().getParticleStatus()){
				particleSys.setPosition(camX, camY);
				particleSys.render();
			}
			// draw all shots
			for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()) {
				entry.getValue().render(camX, camY);
			}
			// draw all ships and their components
			for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
				entry.getValue().render(camX, camY);
			}
			// draw all doodads
			for (Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()) {
				entry.getValue().render(camX, camY);
			}
			
			//draw effects
			for (Effect e : explosions.values()){
				e.render(camX, camY);
			}
			
			playerHud.render(arg2, arg0, levelData, camX, camY);
			lh.render(arg2, camX, camY);
//			gfx.drawString("Screen Mouse X:"+String.valueOf(arg0.getInput().getMouseX()), 100, 10);
//			gfx.drawString("Screen Mouse Y:"+String.valueOf(arg0.getInput().getMouseY()), 400, 10);
//			gfx.drawString("Cam Mouse X:"+String.valueOf(arg0.getInput().getMouseX()+camX), 100, 25);
//			gfx.drawString("Cam Mouse Y:"+String.valueOf(arg0.getInput().getMouseY()+camY), 400, 25);
		}
	}

	/**
	 * the core of the state, all instance relative items are updated through this method
	 */
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		if(gameIni){
			customIni();
		}
		
		if (gamePlay) {
			// placed at the top for ubiquitousness
			ArrayList<Integer> removeShots = new ArrayList<Integer>();
			ArrayList<Integer> removeShips = new ArrayList<Integer>();
			ArrayList<Integer> removeDoodads = new ArrayList<Integer>();
			ArrayList<Integer> removeObjective = new ArrayList<Integer>();
			ArrayList<Integer> removeEffect = new ArrayList<Integer>();
			procInput(arg0, arg1, delta);
			
			// ======Begin updates!
			// update ships
			
			updateEntities(delta, removeShots, removeShips, removeObjective, removeEffect);
			
			// run collisions
			checkCollisions(removeShots);

			// level update
			// this check is so any action still active will also be updated
			for (BasicAction act : lh.getExecuteActions()) {
				if (act.isUpdate() == true) {
					levelData.setNeedUpdate(true);
				}
			}

			// finally, if the level needs to be updated; do it
			if (levelData.isNeedUpdate() == true) {
				System.out.println("Level updating");
				lh.update(delta, this);
			}

			playerHud.update(pc, this);
			cleanEntities(removeShots, removeShips, removeDoodads,
					removeObjective, removeEffect);

			int boundsCheck = lh.checkBounds(pc.getPlayShip().getCollider());
			if (boundsCheck == 1) {
				playerHud.setWarn(false);
			}
			if (boundsCheck == 0) {
				playerHud.setWarn(true);
			}
			if (boundsCheck == -1) {
				gameOver = true;
				winLose = -2;
				deathReason = 0;
			}
			
			if(pc.getOptions().getParticleStatus()){
				for(int i = 0; i < particleSys.getEmitterCount(); i++){
					particleSys.update(i);
				}
			}

			pc.updateCamera(this);
			// Check for win conditions
			checkForWin(arg1,removeShots,removeShips,removeDoodads,removeObjective, removeEffect);
		}
	}

	private void checkForWin(StateBasedGame arg1,ArrayList<Integer> removeShots,
							ArrayList<Integer> removeShips, 
							ArrayList<Integer> removeDoodads,
							ArrayList<Integer> removeObjective,
							ArrayList<Integer> removeEffect) {
		if (winLose < 0) {
			cleanEntities(removeShots, removeShips, removeDoodads,
					removeObjective, removeEffect);
			shutDown();
			GameOverState gameO = (GameOverState)arg1.getState(CoreStateManager.GAMEOVERSTATE);
			gameO.setReason(deathReason);
			if(winLose == -1){
				arg1.enterState(CoreStateManager.GAMEOVERSTATE, new FadeOutTransition(Color.red) , null);
			}else if(winLose == -2){
				arg1.enterState(CoreStateManager.GAMEOVERSTATE, new FadeOutTransition(Color.black) , null);
			}else if(winLose == -3){
				arg1.enterState(CoreStateManager.GAMEOVERSTATE, new FadeOutTransition(Color.black) , null);
			}
		}
		
		
		if(winLose== 1 ){
			cleanEntities(removeShots, removeShips, removeDoodads,
					removeObjective, removeEffect);
			shutDown();
			arg1.enterState(CoreStateManager.GAMEWINSTATE, new FadeOutTransition(Color.white) , null);
		}
	}

	private void procInput(GameContainer arg0, StateBasedGame arg1, int delta) {
		Input p = arg0.getInput();
		if (p.isKeyDown(Input.KEY_UP)) {
			pc.getPlayShip().moveForward(delta, getParticleSys());
			
		}
		if (p.isKeyDown(Input.KEY_DOWN)) {
			pc.getPlayShip().moveBackward(delta,getParticleSys());
		}
		if (p.isKeyDown(Input.KEY_LEFT)) {
			pc.getPlayShip().rotateLeft(delta);
		}
		if (p.isKeyDown(Input.KEY_RIGHT)) {
			pc.getPlayShip().rotateRight(delta);
		}
		if (p.isKeyDown(Input.KEY_Z)) {
			pc.getPlayShip().strafeLeft(delta);
		}
		if (p.isKeyDown(Input.KEY_X)) {
			pc.getPlayShip().strafeRight(delta);
		}
		if (p.isKeyDown(Input.KEY_LCONTROL)) {
			if (pc.tryShot()) {
				addShot(pc.getPlayShip().getWeapon().makeShot(getSFXVol()));
			}
			pc.tryShot();
		}
		if(p.isKeyPressed(Input.KEY_W)){
			if(playerHud.getShowMap()){
				playerHud.setShowMap(false);
			}else{
				playerHud.setShowMap(true);
			}
		}
		if (p.isKeyPressed(Input.KEY_Q)) {
			if (playerHud.getDevGogState()) {
				playerHud.setDevGog(false);
			} else {
				playerHud.setDevGog(true);
			}
		}
		if (p.isKeyPressed(Input.KEY_C)) {
			if (pc.getPlayShip().isRadarState()) {
				pc.getPlayShip().setRadarState(false);
			} else {
				pc.getPlayShip().setRadarState(true);
			}
			playerHud.setRadarOn(pc.getPlayShip().isRadarState());
		}
		if(p.isKeyPressed(Input.KEY_A)){
			if(playerHud.getShowNav()==false){
				playerHud.setShowNav(true);
			}else if(playerHud.getShowNav()){
				playerHud.setShowNav(false);
			}
		}
		if(p.isKeyPressed(Input.KEY_TAB)){
			if(playerHud.getShowObjs()){
				playerHud.setShowObjs(false);
			}else{
				playerHud.setShowObjs(true);
			}
		}
		if(p.isKeyPressed(Input.KEY_ESCAPE)){
			gdb.getSound(levelData.getMusic()).stop();
			arg1.enterState(CoreStateManager.PAUSE);
		}
	}

	/**
	 * run updates on all entity lists
	 * @param delta
	 * @param removeShots
	 */
	public void updateEntities(int delta, ArrayList<Integer> removeShots, ArrayList<Integer> removeShips, ArrayList<Integer> removeObjs, ArrayList<Integer> removeEffect){
		
		//update ships
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			BasicShip ship = entry.getValue();
			ship.update(delta, this);
			if(ship.isDead()){
				removeShips.add(entry.getKey());
				ship.onDie(this);
				makeExplosion((float)ship.getX(), (float) ship.getY(), "explo1");
				if(ship.getOnDeathTriggerName()!=null){
					levelData.getTrigger(ship.getOnDeathTriggerName()).trigger(true);
					levelData.setNeedUpdate(true);
				}
				if(ship.equals(pc.getPlayShip())){
					winLose = -1;
				}
			}
		}
		
		//update shots
		for (Map.Entry<Integer, BasicShot> shot : shots.entrySet()) {
			shot.getValue().update(delta);
			if(shot.getValue().getTimer()>shot.getValue().getInterval()){
				removeShots.add(shot.getKey());
			}
		}
		//Update Doodads
		for(Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().update(delta);
		}
		
		//effects
		for(Map.Entry<Integer, Effect> e : explosions.entrySet()){
			e.getValue().update(delta);
			if(e.getValue().getIsDone()){
				removeEffect.add(e.getKey());
			}
		}
	}

	/**
	 * check all collisions
	 * @param removeShots
	 */
	public void checkCollisions(ArrayList<Integer> removeShots){
		//check Shot/Ship collision
		for(Map.Entry<Integer, BasicShip> ship : ships.entrySet()){
			for(Map.Entry<Integer, BasicShot> shot : shots.entrySet()){
				if(ship.getValue().getCollider().intersects(shot.getValue().getCollider())){
					double tempHP =ship.getValue().getHealth();
					ship.getValue().setHealth(tempHP -shot.getValue().getDamage());
					removeShots.add(shot.getKey());
					makeExplosion((float)shot.getValue().getX(), (float)shot.getValue().getY(), shot.getValue().getImpactPrtl());
					shot.getValue().onHit(pc.getOptions());
					if(ship.getValue().equals(pc.getPlayShip())){
						pc.setAlive(false);
						deathReason = 1;
					}
				}
			}
		}
		
		//check Shot/Trigger
		for(BasicTrigger trig : levelData.getTriggerMap().values()){
			for(Map.Entry<Integer, BasicShot> shot : shots.entrySet()){
				if(trig.getCollider()!=null){
					if (trig.getCollider().intersects(shot.getValue().getCollider())) {
						if (trig.getTriggerType() == TriggerTypes.SHOT) {
							// if shot hits trigger, set trigger to true; tell
							// the game the level needs
							// to be updated
							removeShots.add(shot.getKey());
							trig.trigger(true);
							System.out.println("Trigger: " + trig.getName()
									+ " has been fired");
							levelData.setNeedUpdate(true);
						}
					}
				}
			}
		}
		
		//check ship to ship collision - is out for now because it's not fun
//		for(Map.Entry<Integer, BasicShip> ship : ships.entrySet()){
//			for(Map.Entry<Integer, BasicShip> s : ships.entrySet()){
//				if(s.getValue().getCollider().intersects(ship.getValue().getCollider())){
//					if(ship.getValue()!=pc.getPlayShip() && !ship.getValue().equals(s.getValue())){
//					double sHp = s.getValue().getHealth();
//					s.getValue().setHealth(sHp -0.5);
//					double shHp = ship.getValue().getHealth();
//					ship.getValue().setHealth(shHp -0.5);
//					deathReason = 2;
//					}
//				}
//			}
//		}
		
		//check shot/doodad collision
		for(Map.Entry<Integer, BasicShot> shot : shots.entrySet()){
			for(Map.Entry<Integer, BaseEnt> dood : doodads.entrySet()){
				if(dood.getValue().getCollider().intersects(shot.getValue().getCollider())){
					removeShots.add(shot.getKey());
					shot.getValue().onHit(pc.getOptions());
				}
			}
		}
		
		//check triggers and ships
		for(BasicTrigger trig : levelData.getTriggerMap().values()){
			for(Map.Entry<Integer, BasicShip> ship : ships.entrySet()){
				if(trig.getCollider()!=null){
					if(trig.getCollider().intersects(ship.getValue().getCollider())){
						if(trig.getTriggerType()==TriggerTypes.SHIP){
						//if ship hits trigger, set trigger to true; tell the game the level needs
						//to be updated
						trig.trigger(true);
						System.out.println("Trigger: "+trig.getName()+" has been fired");
						levelData.setNeedUpdate(true);
						}
					}
				}
			}
		}
	}
	
	/**
	 * remove all dead entities from the scope
	 * @param removeShots
	 * @param removeShips
	 * @param removeDoodads
	 */
	public void cleanEntities(ArrayList<Integer> removeShots,
								ArrayList<Integer> removeShips,
								ArrayList<Integer> removeDoodads,
								ArrayList<Integer>  removeTask,
								ArrayList<Integer> removeEffect){
		
		for(int i : removeShots){
			shots.remove(i);
		}
		
		for(int i : removeShips){
			ships.remove(i);
		}
		
		for(int i : removeDoodads){
			doodads.remove(i);
		}
		
		for(int i : removeTask){
			levelData.getObjectives().remove(i);
		}
		
		for(int i : removeEffect){
			explosions.remove(i);
		}
	}
	
	public int addShip(BasicShip ship){
		entCount++;
		if(ship.getClass().equals(AIShip.class)){
			AIShip s = (AIShip) ship;
			s.setState(new ScanState((AIShip)ship), this);
			ships.put(entCount, ship);
		}else{
			ships.put(entCount, ship);
		}
		if(ship.mainThrusterEmitter!=null){
			particleSys.addEmitter(ship.mainThrusterEmitter);
		}
		return entCount;
		
	}
	
	/**
	 * add a BaseEnt to the doodad hashmap
	 * @param e
	 * @return int objCount
	 */
	public int addObject(BaseEnt e){
		objCount++;
		doodads.put(objCount, e);
		return objCount;
	}
	
	public int addEffect(Effect e){
		eCount++;
		explosions.put(eCount, e);
		return eCount;
	}
	
	/**
	 * toss a BasicShot onto the update list
	 * @param s BasicShot
	 * @return new shot total (int)
	 */
	public int addShot(BasicShot s){
		shotCount++;
		shots.put(shotCount, s);
		return shotCount;
	}
	
	/**
	 * adds a client to the list of clients in-game
	 * @param client
	 * @return int
	 */
	public int addClient(PlayerClient client){
		clientCount++;
		clients.put(clientCount, client);
		return clientCount;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	/**
	 * give this state a root player client for the primary player
	 * @param PlayerClient
	 */
	public void setPlayerClient(PlayerClient PC){
		pc = PC;
	}
	
	public int getCamX() {
		return camX;
	}
	
	/**
	 * Center camera's X position
	 * @param camX
	 */
	public void setCamX(int camX) {
		this.camX = 512 - camX;
	}

	public int getCamY() {
		return camY;
	}

	/**
	 * Center camera's Y position.
	 * @param camY
	 */
	public void setCamY(int camY) {
		this.camY = 384 - camY;
	}
	
	public EntityFactory getEntFac(){
		return this.entFac;
	}
	
	public static Shape offsetShape(Shape s, int dx, int dy){
	    float  x = s.getCenterX();
	    float y = s.getCenterY();
	    Shape toSender;
	    toSender = s.transform(new Transform() );
	    toSender.setCenterX( x + dx);
	    toSender.setCenterY( y + dy);
	    return toSender;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public HashMap<Integer, BasicShip> getShips() {
		return ships;
	}

	public HashMap<Integer, BasicShot> getShots() {
		return shots;
	}

	public HashMap<Integer, BaseEnt> getDoodads() {
		return doodads;
	}
	
	public HashMap<Integer, PlayerClient> getClients() {
		return clients;
	}
	
	public Vector2f circularFunction(float angle, int rad){
	       return new Vector2f((float) (Math.cos(angle+Math.PI) * rad + 512), (float)(Math.sin(angle+Math.PI) * rad + 384));
	}

	private LevelDataModel buildGamePlayLevel() {
		TriggerFactory tf = new TriggerFactory();
		try {
			levelData = gdb.buildLevel(tf, lvlPointer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return levelData;
	}
	
	public void setLevelPointer(String s) {
		this.lvlPointer = s;
	}
	
	public LevelDataModel getLevel(){
		return levelData;
	}
	
	public void setWinLose(int i){
		this.winLose = i;
	}
	
	public int getWinLose(){
		return this.winLose;
	}
	
	public BasicShip getPlayerShip(){
		return pc.getPlayShip();
	}

	public void tripTrigger(String trigName){
		Dbg.line("Trigger:: " + trigName + " fired.");
		levelData.getTrigger(trigName).trigger(true);
		levelData.setNeedUpdate(true);
	}
	
	public void spawnShip(ents.ShipDesc s){
		addShip(entFac.shipFromDesc(s));
	}
	
	public Graphics getGfx(){
		return gfx;
	}
	
	public void setGameDataBase(GameDatabase g){
		this.gdb = g;
	}
	
	public GameDatabase getGameDataBase(){
		return this.gdb;
	}
	
	public Float getSFXVol(){
		return pc.getOptions().getFxvol();
	}
	
	public Float getMusicVol(){
		return pc.getOptions().getMusevol();
	}
	
	public Float getVoiceVol(){
		return pc.getOptions().getVoicevol();
	}
	
	public LevelDataModel getLevelData(){
		return levelData;
	}
	
	public void setLevelData(LevelDataModel ldm){
		levelData = ldm;
	}
	
	public void shutDown(){
		gdb.getSound(levelData.getMusic()).stop();
		pc.setClientEngines(null);
		pc.setClientGuns(null);
		pc.setClientShips(null);
		levelData = null;
		gameIni = true;
		gamePlay = false;
	}
	
	public void customInit(PlayerClient PC, GameDatabase gDB, EntityFactory ef, HudDataModel h){
		this.hdm = h;
		this.gdb = gDB;
		this.entFac = ef;
		this.pc = PC;
	}
	
	public ParticleSystem getParticleSys(){
		return particleSys;
	}
	private void makeExplosion(float x, float y, String type) {
		if((type!="")){
			Effect e = new Effect(gdb, type, x, y);
			e.setX((double)x);
			e.setY((double)y);
			addEffect(e);
		}
	}
}
