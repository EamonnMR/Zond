package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private int id, entCount, objCount, shotCount, timer;
	private PlayerClient pc, pc2;
	private BaseLevel level;
	private HashMap<Integer, BasicShip> ships;
	private HashMap<Integer, BasicShot> shots;
	private HashMap<Integer, BaseEnt> doodads;
	GameDatabase gdb;
	EntityFactory entFac;
	
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
	}
	
	//methods
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {	
		
		level = new BaseLevel("Scratch", new Rectangle(0,0,1600,1600));
		level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));
		
		//create the client ship
		pc.setShip(entFac.stockMercury());
		pc.getShip().ini((arg0.getWidth()/2), (arg0.getHeight()/2), 0.0f);
		
		pc2 = new PlayerClient(1);
		pc2.setShip(entFac.stockGem());
		pc2.getShip().ini((200), (200), 0.0f);
		
		//add both ships to the Ship hashmap
		addShip(pc.getShip());
		addShip(pc2.getShip());
		
		//make a doodad, in this case an asteroid

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		level.render(arg2, 0, 0);
		
		//draw all ships and their components
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().render();
		}
		//draw all doodads
		for (Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().render();
		}
		//draw all shots
		for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()){
			entry.getValue().render();
//			arg2.draw(entry.getValue().getCollider());
		}
		
		//all this below is for the DevGog system!
		arg2.draw(pc.getShip().getCollider());
		
		String x = String.valueOf(arg0.getInput().getMouseX());
		arg2.drawString(x, 25, 700);
		x = String.valueOf(arg0.getInput().getMouseY());
		arg2.drawString(x, 25, 725);
		
		x = String.valueOf(pc.getShip().getHealth());
		arg2.drawString("Players Health: "+x, 10, 35);
			
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		Input p = arg0.getInput();
		
		//TODO:there's something wrong with the input, multiple keys jamming
		if(p.isKeyDown(Input.KEY_UP)){
			pc.getShip().moveForward(delta);
		}
		if(p.isKeyDown(Input.KEY_DOWN)){
			pc.getShip().moveBackward(delta);
		}
		if(p.isKeyDown(Input.KEY_LEFT)){
			pc.getShip().rotateLeft(delta);
		}
		if(p.isKeyDown(Input.KEY_RIGHT)){
			pc.getShip().rotateRight(delta);
		}
		if(p.isKeyDown(Input.KEY_Z)){
			pc.getShip().strafeLeft(delta);
		}
		if(p.isKeyDown(Input.KEY_X)){
			pc.getShip().strafeRight(delta);
		}
		if(p.isKeyDown(Input.KEY_SPACE)){
			timer +=delta;
			if(timer > pc.getShip().getWeapon().getRof()){
				timer -= pc.getShip().getWeapon().getRof();
				addShot(pc.getShip().getWeapon().makeShot());
			}
		}
		
		//Begin update loop
		ArrayList<Integer> removeShots = new ArrayList<Integer>();
		ArrayList<Integer> removeShips = new ArrayList<Integer>();
		ArrayList<Integer> removeDoodads = new ArrayList<Integer>();
		
		//update ships
		updateEntities(delta, removeShots);
		
		//run collisions
		checkCollisions(removeShots);
		
		//entity cleanup time
		cleanEntities(removeShots, removeShips, removeDoodads);
		
	}

	/**
	 * adds entity to the hashmap
	 */
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
	public void updateEntities(int delta, ArrayList<Integer> removeShots){
		//update shots
		for (Map.Entry<Integer, BasicShot> shot : shots.entrySet()) {
			shot.getValue().update(delta);
			if(shot.getValue().getTimer()==shot.getValue().getInterval()){
				removeShots.add(shot.getKey());
			}
		}
		
		//update ships
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().update(delta, entry.getValue().getX(), entry.getValue().getY());
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
		for(Map.Entry<Integer, BasicShot> shot : shots.entrySet()){
			for(Map.Entry<Integer, BasicShip> ship : ships.entrySet()){
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
	

}
