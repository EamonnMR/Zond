package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import level.BasicLevel;
import level.Objective;
import level.TriggerTypes;
import level.actions.BasicAction;
import level.test.AlphaLevel;
import level.triggers.BasicTrigger;

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

import ui.hud.Hud;
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
public class ClientGameplayState extends BasicGameState{

	//vars
	private int id, entCount, objCount, shotCount, clientCount, taskCount;
	//Constants
	float radius = 350; //Distance to draw tags from player

	//play with these till the tags are centered
	float xoffset;
	float yoffset;
	private boolean gameOver, gamePlay, gameIni;

	int camX, camY, boundsCheck;
	PlayerClient pc, pc2, pc3, pc4;
	private BaseLevel level; //soon to be deprecated
	HashMap<Integer, BasicShip> ships;
	HashMap<Integer, BasicShot> shots;
	HashMap<Integer, BaseEnt> doodads;
	HashMap<Integer, PlayerClient> clients;
	HashMap<String, BasicShip> incomingClientShips;
	
	GameDatabase gdb;
	EntityFactory entFac;
	Hud playerHud;
	AlphaLevel test;
	
	private BasicLevel levelToUse;	//testing the level logic
	//constructor
	public ClientGameplayState(int i, PlayerClient PC, GameDatabase gDB, EntityFactory ef, BasicLevel lvl){
		this.id = i;
		this.gdb = gDB;
		this.entFac = ef;
		this.pc = PC;
//		this.levelToUse = lvl;	//keep this here for now.
		this.boundsCheck = 1;
		this.taskCount = 0;
		this.gameOver = false;		//TODO: make this part of intra CGS state system
		this.gameIni = true;		//Set to true here because it's not true anywhere else
		this.gamePlay = false;		//
	}
	
	//methods
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {	
		if(gameIni){ 
			test = new AlphaLevel(entFac);
			this.levelToUse = test.getLevel();
			this.ships = new HashMap<Integer, BasicShip>();
			this.shots = new HashMap<Integer, BasicShot>();
			this.doodads = new HashMap<Integer, BaseEnt>();
			this.clients = new HashMap<Integer, PlayerClient>();
			this.incomingClientShips = new HashMap<String, BasicShip>();
		
			incomingClientShips.put("mercury", entFac.stockMercury());
			pc.setClientShips(incomingClientShips);

			//TODO: clean this up
			level = new BaseLevel("Scratch", new Rectangle(0,0,1600,1600));
			level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));
		
			//create the client ship
			pc.setPlayShip(pc.retrieveShip("mercury"));
			pc.getPlayShip().ini(512, 250, 0.0f);
			pc.getPlayShip().setHealth(10);
		
			//this stuff will be superseded by a map
			pc2 = new PlayerClient(1);
			pc2.setPlayShip(entFac.stockGem());
			pc2.getPlayShip().ini((512), (384), 0.0f);
		
			pc3 = new PlayerClient(2);
			pc3.setPlayShip(entFac.stockSky());
			pc3.getPlayShip().ini(0, 0, 45.0f);
		
			pc4 = new PlayerClient(3);;
			pc4.setPlayShip(entFac.stockLunar());
			pc4.getPlayShip().ini(28,100, -45.0f);
		
			playerHud = new Hud(pc, 1023, 767);
		
			//add both ships to the Ship hashmap
			addShip(pc.getPlayShip());
			addShip(pc2.getPlayShip());
			addShip(pc3.getPlayShip());
			addShip(pc4.getPlayShip());
		
			//camera test
			setCamX(0);
			setCamY(0);
		
			taskCount = levelToUse.getTotalObjectives();
			gameIni = false;
			gamePlay = true;
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
		if(gamePlay){
			level.render(arg2, 0, 0);
		//draw all shots
		for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()){
			entry.getValue().render(camX, camY);
		}
		//draw all ships and their components
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().render(camX, camY);
		}
		//draw all doodads
		for (Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().render(camX, camY);
		}

		//actions
		for(BasicAction acts : levelToUse.getExecuteActions()){
			if(acts.isUpdate()){
				acts.render(arg2);
			}
		}
		
		playerHud.render(arg2, arg0, levelToUse, camX, camY);
		levelToUse.render(arg2, camX, camY);
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		if(gamePlay){
		//placed at the top for ubiquitousness
		ArrayList<Integer> removeShots = new ArrayList<Integer>();
		ArrayList<Integer> removeShips = new ArrayList<Integer>();
		ArrayList<Integer> removeDoodads = new ArrayList<Integer>();
		ArrayList<Integer> removeObjective = new ArrayList<Integer>();
		
		
		Input p = arg0.getInput();
			if(p.isKeyDown(Input.KEY_UP)){
				pc.getPlayShip().moveForward(delta);
			}
			if(p.isKeyDown(Input.KEY_DOWN)){
				pc.getPlayShip().moveBackward(delta);
			}
			if(p.isKeyDown(Input.KEY_LEFT)){
				pc.getPlayShip().rotateLeft(delta);
			}
			if(p.isKeyDown(Input.KEY_RIGHT)){
				pc.getPlayShip().rotateRight(delta);
			}
			if(p.isKeyDown(Input.KEY_Z)){
				pc.getPlayShip().strafeLeft(delta);
			}
			if(p.isKeyDown(Input.KEY_X)){
				pc.getPlayShip().strafeRight(delta);
			}
			if(p.isKeyDown(Input.KEY_LCONTROL)){
				if(pc.tryShot()){
					addShot(pc.getPlayShip().getWeapon().makeShot());
				}
				pc.tryShot();
			}
			if(p.isKeyPressed(Input.KEY_Q)){
				if(playerHud.getDevGogState()==false){
					playerHud.setDevGog(true);
				}else if(playerHud.getDevGogState()==true){
					playerHud.setDevGog(false);
				}	
			}
			if(p.isKeyPressed(Input.KEY_C)){
				if(pc.getRadarState()==true){
					pc.setRadarState(false);
				}else{
					pc.setRadarState(true);
				}
				playerHud.setRadarOn(pc.getRadarState());
			}
			
		
		//======Begin updates!
		//update ships
		updateEntities(delta, removeShots, removeShips, removeObjective);
		
		//run collisions
		checkCollisions(removeShots);
		
		//level update
		//this check is so any action still active will also be updated
		for(BasicAction act : levelToUse.getExecuteActions()){
			if(act.isUpdate()==true){
				levelToUse.setNeedsUpdate(true);
			}
		}
		
		//finally, if the level needs to be updated; do it
		if(levelToUse.isNeedsUpdate()==true){
			System.out.println("Level updating");
			levelToUse.update(delta, this);
		}
		
		playerHud.update(pc, this);
		cleanEntities(removeShots,removeShips,removeDoodads, removeObjective);
		
		int boundsCheck = levelToUse.checkBounds(pc.getPlayShip().getCollider());
		if(boundsCheck==1){
			playerHud.setWarn(false);
		}
		if(boundsCheck==0){
			playerHud.setWarn(true);
		}
		if(boundsCheck==-1){
			gameOver = true;
		}
		
		pc.updateCamera(this);
	
		
		//check for all objectives complete //FIXME: Uncomment this for objectives
		//if(taskCount == levelToUse.getTotalObjectives()){
		//	gameOver = true;
		//}
		
		//game over!? you idiot
		if(gameOver){
			cleanEntities(removeShots,removeShips,removeDoodads, removeObjective);
			gameOver = false;
			gamePlay = false;
			gameIni = true;
			arg1.enterState(-1);
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
			entry.getValue().update(delta);
			if(entry.getValue().getHealth()<=0){
				removeShips.add(entry.getKey());
				if(entry.getValue().equals(pc.getPlayShip())){
					gameOver = true;
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
		
		for(Map.Entry<Integer, Objective> tasks : levelToUse.getObjectiveList().entrySet()){
			if(tasks.getValue().getComplete()){
				removeObjs.add(tasks.getKey());
				taskCount += 1;
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
					shot.getValue().onHit();
					if(ship.getValue().equals(pc.getPlayShip())){
						pc.setAlive(false);
					}
				}
			}
		}
		
		//check Shot/Trigger
		for(BasicTrigger trig : levelToUse.getLevelTriggerMap().values()){
			for(Map.Entry<Integer, BasicShot> shot : shots.entrySet()){
				if(trig.getCollider().intersects(shot.getValue().getCollider())){
					if(trig.getTriggerType()==TriggerTypes.SHOT){
						//if shot hits trigger, set trigger to true; tell the game the level needs
						//to be updated
						trig.trigger(true);
						System.out.println("Trigger: "+trig.getName()+" has been fired");
						levelToUse.setNeedsUpdate(true);
					}
				}
			}
		}
			
		//check shot/doodad collision
		for(Map.Entry<Integer, BasicShot> shot : shots.entrySet()){
			for(Map.Entry<Integer, BaseEnt> dood : doodads.entrySet()){
				if(dood.getValue().getCollider().intersects(shot.getValue().getCollider())){
					removeShots.add(shot.getKey());
				}
			}
		}
		
		//check triggers and ships
		for(BasicTrigger trig : levelToUse.getLevelTriggerMap().values()){
			for(Map.Entry<Integer, BasicShip> ship : ships.entrySet()){
				if(trig.getCollider().intersects(ship.getValue().getCollider())){
					if(trig.getTriggerType()==TriggerTypes.SHIP){
					//if ship hits trigger, set trigger to true; tell the game the level needs
					//to be updated
					trig.trigger(true);
					System.out.println("Trigger: "+trig.getName()+" has been fired");
					levelToUse.setNeedsUpdate(true);
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
			levelToUse.getObjectiveList().remove(i);
		}
	}
	
	public int addShip(BasicShip e){
		entCount++;
		ships.put(entCount, e);
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
	
	public Vector2f circularFunction(float angle){
	       return new Vector2f((float) (Math.cos(angle+Math.PI) * radius + 512), (float)(Math.sin(angle+Math.PI) * radius + 384));
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame arg1){
		try {
			this.gameIni=true;
			this.init(container, arg1);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
