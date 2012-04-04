package core;

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

public class ClientGameplayState extends BasicGameState {

	//vars
	private int id, entCount, objCount, shotCount;
	private EntityFactory entFac;
	private PlayerClient pc, pc2;
	private BaseLevel level;
	private BaseEnt asteroid;
	private HashMap<Integer, BasicShip> ships;
	private HashMap<Integer, BasicShot> shots;
	private HashMap<Integer, BaseEnt> doodads;
	
	
	//const
	public ClientGameplayState(int i, EntityFactory ef, PlayerClient PC){
		id = i;
		entFac = ef;
		pc = PC;
		pc2 = new PlayerClient(1);
		ships = new HashMap<Integer, BasicShip>();
		shots = new HashMap<Integer, BasicShot>();
		doodads = new HashMap<Integer, BaseEnt>();
	}
	//methods
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
//		level = new BaseLevel("Scratch",1600,1600);
		level = new BaseLevel("Scratch", new Rectangle(0,0,1600,1600));
		level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));
		
		
		pc.setShip(entFac.stockMercury());
		pc.getShip().setEngine(entFac.stockEngine());
		pc.getShip().setWeapon(entFac.stock20mm());
		pc.getShip().setX((arg0.getWidth()/2));
		pc.getShip().setY((arg0.getHeight()/2));
		
		pc2.setShip(entFac.stockVostok());
		pc2.getShip().setEngine(entFac.stockEngine());
		pc2.getShip().setWeapon(entFac.stock20mm());
		pc2.getShip().setX(800);
		pc2.getShip().setY(400);
		
		addShip(pc.getShip());
		addShip(pc2.getShip());

		asteroid = entFac.smallAst();
		asteroid.setX(250);
		asteroid.setY(250);
		addObject(asteroid);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		level.render(arg2, 0, 0);
//		pc.getShip().render(arg2);
//		pc2.getShip().render(arg2);
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().render();
		}
		for (Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().render();
		}
		
		for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()){
			entry.getValue().render();
		}
		
		arg2.draw(pc.getShip().getCollider());
		arg2.draw(pc2.getShip().getCollider());
		
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
		// TODO Auto-generated method stub
		Input p = arg0.getInput();
		
		if(p.isKeyDown(pc.getKey(0))){
			pc.getShip().moveForward(delta);
		}
		if(p.isKeyDown(pc.getKey(1))){
			pc.getShip().moveBackward(delta);
		}
		if(p.isKeyDown(pc.getKey(2))){
			pc.getShip().rotateLeft(delta);
		}
		if(p.isKeyDown(pc.getKey(3))){
			pc.getShip().rotateRight(delta);
		}
		if(p.isKeyDown(pc.getKey(4))){
			pc.getShip().strafeLeft(delta);
		}
		if(p.isKeyDown(pc.getKey(5))){
			pc.getShip().strafeRight(delta);
		}
		if(p.isKeyDown(pc.getKey(6))){
			addShot(pc.getShip().getWeapon().makeShot());
		}
		
		doCollisions();
		
		for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
			entry.getValue().update(delta, entry.getValue().getX(), entry.getValue().getY());
		}
		
		for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()) {
			entry.getValue().update(delta);
		}
		for(Map.Entry<Integer, BasicShot> entry : shots.entrySet()){
			if(entry.getValue().getLifetime()<=0){
				shots.remove(entry);
			}
		}
		
	
		for(Map.Entry<Integer, BaseEnt> entry : doodads.entrySet()){
			entry.getValue().update(delta);
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
		// TODO Auto-generated method stub
		return id;
	}
	
	public void setEntFac(EntityFactory ef){
		entFac = ef;
	}
	
	public void setPlayerClient(PlayerClient PC){
		pc = PC;
	}
	

}
