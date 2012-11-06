package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import level.LevelDataModel;
import level.TriggerTypes;
import level.actions.BasicAction;
import level.triggers.BasicTrigger;
import level.triggers.DeathTrigger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

import ui.hud.Hud;
import ui.hud.HudDataModel;
import ents.BaseEnt;
import ents.BaseLevel;
import ents.BasicShip;
import ents.BasicShot;
import ents.EntityFactory;

/**
 * 
 * @author proohr
 * @version 1.0
 * the core of the active game, handles an instance of play
 */
public class GameplayState extends BasicGameState{

	//INTERNAL VARIABLES AND DATA============================
	private int id, entCount, objCount, shotCount, clientCount, winLose;
	private boolean gameOver, gamePlay, gameIni;	//instance internal state
	int camX, camY, boundsCheck, deathReason;
	LevelDataModel levelData;
	private HudDataModel hdm;
	
	//Constants	        //play with these till the tags are centered
	float radius = 350,xoffset,yoffset; //Distance to draw tags from player
	
	HashMap<Integer, BasicShip> ships;		//instance data
	HashMap<Integer, BasicShot> shots;
	HashMap<Integer, BaseEnt> doodads;
	HashMap<Integer, PlayerClient> clients;
	HashMap<String, BasicShip> incomingClientShips;
	LevelHandler lh;
	private effects.Stack fxStack;
	//====================================================================
	
	//EXTERNAL VARIABLES AND DATA=========================================
	PlayerClient pc;
	private BaseLevel level; //soon to be deprecated
	private GameDatabase gdb;
	private EntityFactory entFac;
	private Hud playerHud;
	private LevelBuilder lb; //Soon to be deprecated
	//====================================================================
	
	//constructor
	public GameplayState(int i, PlayerClient PC, GameDatabase gDB, EntityFactory ef, LevelBuilder lvl, HudDataModel h){
		
		this.id = i;
		this.hdm = h;
		this.gdb = gDB;
		this.entFac = ef;
		this.pc = PC;
		this.lb = lvl;
//		lvl.setEntFac(entFac);
//		this.levelData = lvl.buildLevel();	//keep this here for now.
		this.boundsCheck = 1;
		this.gameOver = false;		
		this.gameIni = true;		
		this.gamePlay = false;
//		this.lh = new LevelHandler();
		this.winLose = 0;
		this.fxStack = new effects.Stack();
	}
	
	//methods
	/**
	 * init method, instantiates everything needed for a gameplay instance.
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {	
		
		if(gameIni){
			this.lb = new LevelBuilder();
			lb.setEntFac(entFac);
			this.levelData = lb.buildLevel(entFac);
			this.lh = new LevelHandler(levelData);
			this.ships = new HashMap<Integer, BasicShip>();
			this.shots = new HashMap<Integer, BasicShot>();
			this.doodads = new HashMap<Integer, BaseEnt>();
			this.clients = new HashMap<Integer, PlayerClient>();
			this.incomingClientShips = new HashMap<String, BasicShip>();
		
//			incomingClientShips.put("mercury", entFac.stockMercury());
//			pc.setClientShips(incomingClientShips);

			level = new BaseLevel("Scratch", new Rectangle(0,0,1600,1600));
			level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));
		
			//create the client ship
			if(pc.getPlayShip()==null){
				pc.setPlayShip(entFac.stockMercury());
			}else{
				pc.setPlayShip(entFac.buildShip(pc.getPlayShip().getName(), pc.getPlayShip().getWeapon().getName(), pc.getPlayShip().getEngine().getName()));
			}
			pc.getPlayShip().ini(512, 250, 0.0f);
			
			playerHud = new Hud(pc, 1023, 767, hdm, gdb);
		
			//add both ships to the Ship hashmap
			addShip(pc.getPlayShip());
		
			//camera test
			setCamX(0);
			setCamY(0);
		
//			taskCount = levelToUse.getTotalObjectives();
			gameIni = false;
			gamePlay = true;
		}
	}

	/**
	 * render method, everything in the instance is drawn through this method
	 */
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		if(gamePlay){
			level.render(arg2, 0, 0);
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

			playerHud.render(arg2, arg0, levelData, camX, camY);
			lh.render(arg2, camX, camY);
		}
	}

	/**
	 * the core of the state, all instance relative items are updated through this method
	 */
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		if (gamePlay) {
			// placed at the top for ubiquitousness
			ArrayList<Integer> removeShots = new ArrayList<Integer>();
			ArrayList<Integer> removeShips = new ArrayList<Integer>();
			ArrayList<Integer> removeDoodads = new ArrayList<Integer>();
			ArrayList<Integer> removeObjective = new ArrayList<Integer>();

			Input p = arg0.getInput();
			if (p.isKeyDown(Input.KEY_UP)) {
				pc.getPlayShip().moveForward(delta);
				
			}
			if (p.isKeyDown(Input.KEY_DOWN)) {
				pc.getPlayShip().moveBackward(delta);
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
					addShot(pc.getPlayShip().getWeapon().makeShot());
				}
				pc.tryShot();
			}
			if (p.isKeyPressed(Input.KEY_Q)) {
				if (playerHud.getDevGogState() == false) {
					playerHud.setDevGog(true);
				} else if (playerHud.getDevGogState() == true) {
					playerHud.setDevGog(false);
				}
			}
			if (p.isKeyPressed(Input.KEY_C)) {
				if (pc.getRadarState() == true) {
					pc.setRadarState(false);
				} else {
					pc.setRadarState(true);
				}
				playerHud.setRadarOn(pc.getRadarState());
			}
			if(p.isKeyPressed(Input.KEY_A)){
				if(playerHud.getShowNav()==false){
					playerHud.setShowNav(true);
				}else if(playerHud.getShowNav()){
					playerHud.setShowNav(false);
				}
			}
			if(p.isKeyPressed(Input.KEY_ESCAPE)){
				arg1.enterState(CoreStateManager.HANGARBAYSTATE);
			}

			// ======Begin updates!
			// update ships
			updateEntities(delta, removeShots, removeShips, removeObjective);

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
					removeObjective);

			int boundsCheck = lh.checkBounds(pc.getPlayShip().getCollider());
			if (boundsCheck == 1) {
				playerHud.setWarn(false);
			}
			if (boundsCheck == 0) {
				playerHud.setWarn(true);
			}
			if (boundsCheck == -1) {
				gameOver = true;
				winLose = -1;
				deathReason = 0;
			}

			pc.updateCamera(this);

			fxStack.unwind(this); //Unwind the effects stack.

			// Check for win conditions
			if (winLose== -1 ) {
				cleanEntities(removeShots, removeShips, removeDoodads,
						removeObjective);
				gameOver = false;
				gamePlay = false;
				gameIni = true;
				winLose = 0;
				levelData = null;
				GameOverState gameO = (GameOverState)arg1.getState(CoreStateManager.GAMEOVERSTATE);
				gameO.setReason(deathReason);
//				gameO.init(arg0, arg1);
				arg1.enterState(CoreStateManager.GAMEOVERSTATE, new FadeOutTransition(Color.red) , null);
			}
			if(winLose== 1 ){
				cleanEntities(removeShots, removeShips, removeDoodads,
						removeObjective);
				gameOver = false;
				gamePlay = false;
				gameIni = true;
				levelData = null;
				winLose = 0;
				arg1.getState(2).init(arg0, arg1);
				arg1.enterState(2, new FadeOutTransition(Color.white) , null);
			}
		}
	}
	
	
	/**
	 * run updates on all entity lists
	 * @param delta
	 * @param removeShots
	 */
	public void updateEntities(int delta, ArrayList<Integer> removeShots, ArrayList<Integer> removeShips, ArrayList<Integer> removeObjs){
		
		//update ships
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			BasicShip ship = entry.getValue();
			ship.update(delta);
			if(ship.isDead()){
				removeShips.add(entry.getKey());
				ship.onDie(this);
				if(ship.getTriggerTargetName()!=null){
					if(levelData.getTrigger(ship.getTriggerTargetName()).getClass().equals(DeathTrigger.class)){
						levelData.getTrigger(ship.getTriggerTargetName()).trigger(true);
						levelData.setNeedUpdate(true);
					}
				}
				if(ship.equals(pc.getPlayShip())){
					gameOver = true;
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
					shot.getValue().onHit();
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
		
		//check ship to ship collision
		for(Map.Entry<Integer, BasicShip> ship : ships.entrySet()){
			for(Map.Entry<Integer, BasicShip> s : ships.entrySet()){
				if(s.getValue().getCollider().intersects(ship.getValue().getCollider())){
					if(ship.getValue()!=pc.getPlayShip() && !ship.getValue().equals(s.getValue())){
					double sHp = s.getValue().getHealth();
					s.getValue().setHealth(sHp -0.5);
					double shHp = ship.getValue().getHealth();
					ship.getValue().setHealth(shHp -0.5);
					deathReason = 2;
					}
				}
			}
		}
		
		//check shot/doodad collision
		for(Map.Entry<Integer, BasicShot> shot : shots.entrySet()){
			for(Map.Entry<Integer, BaseEnt> dood : doodads.entrySet()){
				if(dood.getValue().getCollider().intersects(shot.getValue().getCollider())){
					removeShots.add(shot.getKey());
					//TODO: play shot collision sound
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
	public void cleanEntities(ArrayList<Integer> removeShots, ArrayList<Integer> removeShips,ArrayList<Integer> removeDoodads, ArrayList<Integer>  removeTask ){
		
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
	}
	
	public int addShip(BasicShip baseEnt){
		entCount++;
		ships.put(entCount, baseEnt);
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
	
	@Override
	public void enter(GameContainer container, StateBasedGame arg1){
		try {
			this.gameIni=true;
			this.init(container, arg1);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public LevelDataModel getLevel(){
		return levelData;
	}
	
	public void setWinLose(int i){
		this.winLose = i;
		if (i == -1){
			gameOver = true; //It might make out lives easier if we get rid of gameOver...
		}
	}
	
	public int getWinLose(){
		return this.winLose;
	}
	
	public BasicShip getPlayerShip(){
		return pc.getPlayShip();
	}
	
	public void pushEffect(effects.Effect e){
		fxStack.push(e);
	}
	
	public void tripTrigger(String trigName){
		Dbg.line("Trigger ''" + trigName + "'' fired off.");
		levelData.getTrigger(trigName).trigger(true);
		levelData.setNeedUpdate(true);
	}
	
	public void spawnShip(ents.ShipDesc s){
		addShip(entFac.shipFromDesc(s));
	}
}