package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import level.BasicAction;
import level.BasicLevel;
import level.BasicTrigger;
import level.actions.MessageAction;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
public class ClientGameplayState extends BasicGameState {

	//vars
	private int id, entCount, objCount, shotCount, clientCount, timer;
	float camX, camY;
	PlayerClient pc, pc2;
	BaseLevel level; //soon to be deprecated
	HashMap<Integer, BasicShip> ships;
	HashMap<Integer, BasicShot> shots;
	HashMap<Integer, BaseEnt> doodads;
	HashMap<Integer, PlayerClient> clients;
	HashMap<String, BasicShip> incomingClientShips;

	GameDatabase gdb;
	EntityFactory entFac;
	
	private BasicLevel levelTest;	//testing the level logic
	private BasicTrigger tellMe;	//simple trigger
	private BasicTrigger askMe;		//simple trigger
	private MessageAction say1;		//simple action
	private MessageAction ask1;		//simple action
	private boolean triggerHit;		//...?
	private Controller joystick;
	
	//constructor
	public ClientGameplayState(int i, PlayerClient PC, GameDatabase gDB, EntityFactory ef){
		this.id = i;
		this.gdb = gDB;
		this.entFac = ef;
		this.pc = PC;
		this.timer = 0;
		this.ships = new HashMap<Integer, BasicShip>();
		this.shots = new HashMap<Integer, BasicShot>();
		this.doodads = new HashMap<Integer, BaseEnt>();
		this.clients = new HashMap<Integer, PlayerClient>();
		this.incomingClientShips = new HashMap<String, BasicShip>();
		
		this.levelTest = new BasicLevel("Test Level");
		
		this.tellMe = new BasicTrigger();
		this.askMe = new BasicTrigger();
		this.say1 = new MessageAction("MessageOut", 10, 75, "greetings", 2000);
		this.ask1 = new MessageAction("Queston", 10, 90, "hello world?", 1000);
		this.triggerHit = false;
	}
	
	//methods
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {	
		//==========ship swapping test
		incomingClientShips.put("mercury", entFac.stockMercury());
		incomingClientShips.put("gemini", entFac.stockGem());
		incomingClientShips.put("lunar", entFac.stockLunar());
		pc.setClientShips(incomingClientShips);
		//=============================
		
		
		//=============================
		tellMe.setName("TellMe");
		tellMe.setTargetName("MessageOut");
		tellMe.setX(151);
		tellMe.setY(50);
		tellMe.setCollider(new Rectangle(tellMe.getX(),tellMe.getY(),100,100));
		
		askMe.setName("AskMe");
		askMe.setTargetName("Queston");
		askMe.setX(50);
		askMe.setY(50);
		askMe.setCollider(new Rectangle(askMe.getX(),askMe.getY(), 100,100));
		
		levelTest.addTrigger(tellMe);
		levelTest.addTrigger(askMe);
		levelTest.addAction(say1);
		levelTest.addAction(ask1);
		//=============================
		
		
		level = new BaseLevel("Scratch", new Rectangle(0,0,1600,1600));
		level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));
		
		//create the client ship
		pc.setPlayShip(pc.retrieveShip("mercury"));
		pc.getPlayShip().ini(190, 103, 0.0f);
			
		pc2 = new PlayerClient(1);
		pc2.setPlayShip(entFac.stockGem());
		pc2.getPlayShip().ini((300), (300), 0.0f);
		
		//add both ships to the Ship hashmap
		addShip(pc.getPlayShip());
		addShip(pc2.getPlayShip());
		//camera test
		setCamX(0);
		setCamY(0);
		
		//camera test
		setCamX(0);
		setCamY(0);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		level.render(arg2, 0, 0);
		
		//draw all shots
		for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()){
			entry.getValue().render();
//			arg2.draw(entry.getValue().getCollider());
		}
		//draw all ships and their components
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().render();
		}
		//draw all doodads
		for (Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().render();
		}

		//actions
		for(BasicAction acts : levelTest.getExecuteActions()){
			if(acts.isUpdate()){
				acts.render(arg2);
			}
		}
		
		//all this below is for the DevGog system!
		arg2.draw(pc.getPlayShip().getCollider());
		
		String x = String.valueOf(arg0.getInput().getMouseX());
		arg2.drawString(x, 25, 700);
		x = String.valueOf(arg0.getInput().getMouseY());
		arg2.drawString(x, 25, 725);
		
		x = String.valueOf(pc.getPlayShip().getHealth());
		arg2.drawString("Players Health: "+x, 10, 35);
		
		for(BasicTrigger trig : levelTest.getLevelTriggerMap().values()){
			arg2.draw(trig.getCollider());
			float tx = trig.getCollider().getCenterX();
			float ty = trig.getCollider().getCenterY();
			arg2.drawString(trig.getName(), tx, ty);
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		//placed at the top for ubiquitousness
		ArrayList<Integer> removeShots = new ArrayList<Integer>();
		ArrayList<Integer> removeShips = new ArrayList<Integer>();
		ArrayList<Integer> removeDoodads = new ArrayList<Integer>();
		
		
		Input p = arg0.getInput();
		//TODO:there's something wrong with the input, multiple keys jamming
//		if(p.isKeyPressed(Input.KEY_1)){
//			if(pc.getPlayShip()!=pc.retrieveShip("mercury")){
//				double x = pc.getPlayShip().getX();
//				double y = pc.getPlayShip().getY();
//				float rot = pc.getPlayShip().getImg().getRotation();
//				for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
//					if(entry.getValue()==pc.getPlayShip()){
//						removeShips.add(entry.getKey());
//					}
//				}
//				pc.setPlayShip(pc.retrieveShip("mercury"));
//				pc.getPlayShip().ini(x, y, rot);
//				addShip(pc.getPlayShip());
//			}
//		}
//		if(p.isKeyPressed(Input.KEY_2)){
//			if(pc.getPlayShip()!=pc.retrieveShip("gemini")){
//				double x = pc.getPlayShip().getX();
//				double y = pc.getPlayShip().getY();
//				float rot = pc.getPlayShip().getImg().getRotation();
//				for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
//					if(entry.getValue()==pc.getPlayShip()){
//						removeShips.add(entry.getKey());
//					}
//				}
//				pc.setPlayShip(pc.retrieveShip("gemini"));
//				pc.getPlayShip().ini(x, y, rot);
//				addShip(pc.getPlayShip());
//			}
//		}
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
				timer +=delta;
				if(timer > pc.getPlayShip().getWeapon().getRof()){
					timer -= pc.getPlayShip().getWeapon().getRof();
					addShot(pc.getPlayShip().getWeapon().makeShot());
				}
			}
			
		
		//======Begin updates!
		//update ships
		updateEntities(delta, removeShots, removeShips);
		
		//run collisions
		checkCollisions(removeShots);

		//level update
		//this check is so any action still active will also be updated
		for(BasicAction act : levelTest.getExecuteActions()){
			if(act.isUpdate()==true){
				levelTest.setNeedsUpdate(true);
//				System.out.println("Action: "+act.getName()+" needs update");
			}
		}
		
		//finally, if the level needs to be updated; do it
		if(levelTest.isNeedsUpdate()==true){
			System.out.println("Level updating");
			levelTest.update(delta);
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
		for(BasicTrigger trig : levelTest.getLevelTriggerMap().values()){
			for(Map.Entry<Integer, BasicShip> ship : ships.entrySet()){
				if(trig.getCollider().intersects(ship.getValue().getCollider())){
					//if ship hits trigger, set trigger to true; tell the game the level needs
					//to be updated
					trig.trigger(true);
					System.out.println("Trigger: "+trig.getName()+" has been fired");
					levelTest.setNeedsUpdate(true);
				}
			}
		}
	}
		//check triggers and ships
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
	 * run updates on all entity lists
	 * @param delta
	 * @param removeShots
	 */
	public void updateEntities(int delta, ArrayList<Integer> removeShots, ArrayList<Integer> removeShips){
		
		//update ships
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().update(delta);
			if(entry.getValue().getHealth()<=0){
				removeShips.add(entry.getKey());
			}
		}
		//update shots
		for (Map.Entry<Integer, BasicShot> shot : shots.entrySet()) {
			shot.getValue().update(delta);
			if(shot.getValue().getTimer()>=shot.getValue().getInterval()){
				removeShots.add(shot.getKey());
			}
		}
		//Update Doodads
		for(Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().update(delta);
		}
	}
	
	/**
	 * remove all dead entities from the scope
	 * @param removeShots
	 * @param removeShips
	 * @param removeDoodads
	 */
	public void cleanEntities(ArrayList<Integer> removeShots, ArrayList<Integer> removeShips,ArrayList<Integer> removeDoodads){
		
		for(int i : removeShots){
			shots.remove(i);
		}
		
		for(int i : removeShips){
			ships.remove(i);
		}
		
		for(int i : removeDoodads){
			doodads.remove(i);
		}
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
	
	public float getCamX() {
		return camX;
	}

	public void setCamX(float camX) {
		this.camX = camX;
	}

	public float getCamY() {
		return camY;
	}

	public void setCamY(float camY) {
		this.camY = camY;
	}
}
