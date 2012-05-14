package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
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
	private EntityFactory entFac;
	private PlayerClient pc, pc2;
	private BaseLevel level;
	private BaseEnt asteroid;
	private HashMap<Integer, BasicShip> ships;
	private HashMap<Integer, BasicShot> shots;
	private HashMap<Integer, BaseEnt> doodads;
	
	
	//constructor
	public ClientGameplayState(int i, EntityFactory ef, PlayerClient PC){
		id = i;
		entFac = ef;
		pc = PC;
		timer = 0;
		pc2 = new PlayerClient(1);
		ships = new HashMap<Integer, BasicShip>();
		shots = new HashMap<Integer, BasicShot>();
		doodads = new HashMap<Integer, BaseEnt>();
	}
	
	//methods
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
//		level = new BaseLevel("Scratch",1600,1600);
		level = new BaseLevel("Scratch", new Rectangle(0,0,1600,1600));
		level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));
		
		//create the client ship
		pc.setShip(entFac.stockMercury());
		pc.getShip().setEngine(entFac.buildFullEngine());
		pc.getShip().setWeapon(entFac.stock20mm());
		pc.getShip().setX((arg0.getWidth()/2));
		pc.getShip().setY((arg0.getHeight()/2));
		
		//make a target dummy
		pc2.setShip(entFac.stockVostok());
		pc2.getShip().setEngine(entFac.buildFullEngine());
		pc2.getShip().setWeapon(entFac.stock20mm());
		pc2.getShip().setX(800);
		pc2.getShip().setY(400);
		
		//add both ships to the Ship hashmap
		addShip(pc.getShip());
		addShip(pc2.getShip());

		//make a doodad, in this case an asteroid
		asteroid = entFac.smallAst();
		asteroid.setX(250);
		asteroid.setY(250);
		asteroid.setCollider(new Circle((float)asteroid.getX(),(float)asteroid.getY(),32));
		addObject(asteroid);
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
		arg2.draw(pc2.getShip().getCollider());
		arg2.draw(asteroid.getCollider());
		
		String x = String.valueOf(arg0.getInput().getMouseX());
		arg2.drawString(x, 25, 700);
		x = String.valueOf(arg0.getInput().getMouseY());
		arg2.drawString(x, 25, 725);
		
		x = String.valueOf(pc.getShip().getHealth());
		arg2.drawString("Players Health: "+x, 10, 35);
		
		x = String.valueOf(pc2.getShip().getHealth());
		arg2.drawString("Dummy Health: "+x, 10, 50);
			
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
		if(p.isKeyDown(Input.KEY_Q)){
			pc.getShip().strafeLeft(delta);
		}
		if(p.isKeyDown(Input.KEY_E)){
			pc.getShip().strafeRight(delta);
		}
		if(p.isKeyDown(Input.KEY_SPACE)){
			timer +=delta;
			if(timer > pc.getShip().getWeapon().getRof()){
				timer -= pc.getShip().getWeapon().getRof();
				addShot(pc.getShip().getWeapon().makeShot());
			}
		}
		
//		doCollisions();
		//TODO: organize these entity checks
		ArrayList<Integer> removeShots = new ArrayList<Integer>();
		ArrayList<Integer> removeShips = new ArrayList<Integer>();
		ArrayList<Integer> removeDoodads = new ArrayList<Integer>();
		
		//update ships
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().update(delta, entry.getValue().getX(), entry.getValue().getY());
		}
		
		//update shots
		for (Map.Entry<Integer, BasicShot> shot : shots.entrySet()) {
			shot.getValue().update(delta);
			if(shot.getValue().getTimer()==shot.getValue().getInterval()){
				removeShots.add(shot.getKey());
			}
		}
		
		//Update Doodads
		for(Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().update(delta);
		}
		
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
		
		//entity cleanup time
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
	 * 
	 * @param s 
	 * @return
	 */
	public int addShot(BasicShot s){
		shotCount++;
		shots.put(shotCount, s);
		return shotCount;
	}
	
	/**
	 * run collsion checks
	 * this method is on hold atm
	 */
	public void doCollisions(){
		for(Map.Entry<Integer, BasicShip> currentShip : ships.entrySet()){
			  for(Map.Entry<Integer, BasicShot> currentShot : shots.entrySet()){ 

				  if( specHitCheck( currentShip.getValue(), currentShot.getValue() )==true ){
					  double tempHp = currentShip.getValue().getHealth() - currentShot.getValue().getDamage();
					  currentShip.getValue().setHealth(tempHp);
				  }

			  }
		}
	}
	
	/**
	 * @deprecated
	 * makes a specific collison check between a ship, and a shot
	 * @param a BasicShip
	 * @param b BasicShot
	 * @return boolean
	 */
	public boolean specHitCheck(BasicShip a, BasicShot b){
		if(a.getCollider().intersects(b.getCollider())){
			return true;
		}
		else if(b.getCollider().intersects(a.getCollider())){
			return true;
		}
		return false;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	/**
	 * give this state an entityFactory to build ents from
	 * @param EntityFactory
	 */
	public void setEntFac(EntityFactory ef){
		entFac = ef;
	}
	
	/**
	 * give this state a root player client for the primary player
	 * @param PlayerClient
	 */
	public void setPlayerClient(PlayerClient PC){
		pc = PC;
	}
	

}
