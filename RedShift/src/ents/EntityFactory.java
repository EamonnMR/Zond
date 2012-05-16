package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import core.GameDatabase;
/**
 * Factory design pattern, builds all necessary game classes
 * @author Roohr
 * @version 1.0
 */
public class EntityFactory {

	//variables
//	private static EntityFactory instance;
	GameDatabase gdb;
	
	//constructor
	public EntityFactory(){}
	
	//methods

	//TODO: when RedShift package is up and running, move the builder methods to RedShift's instantiated EntFac
	public void ini(GameDatabase gDB){
		this.gdb = gDB;
	}
	
	//=====SHIP BUILDERS=====
	/**
	 * Builds a basic Mercury class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockMercury(){
		return buildShip("mercury","20mm","smallEngine");
	}
	
	/**
	 * Builds a basic Gemini class BasicShip
	 *@return BasicShip
	 */
	public BasicShip stockGem(){
		BasicShip temp = new BasicShip();
		temp = gdb.getShip("gemini");
		return temp;
	}
	
	/**
	 * Builds a basic Lunar Orbiter class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockLunar(){
		BasicShip temp;
		temp = gdb.getShip("lunar");
		return temp;
	}
	
	/**
	 * Builds a basic Vostok class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVostok(){
		BasicShip temp;
		 temp = gdb.getShip("vostok");
		return temp;
	}
	
	/**
	 * Builds a basic Voskhod class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVoskhod(){
		BasicShip temp;
		temp = gdb.getShip("voskhod");
		return temp;
	}

	/**
	 * Builds a basic Zond4 class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockZond(){
		BasicShip temp;
		temp = gdb.getShip("zond4");
		return temp;
	}
	
	/**
	 * Builds a basicship given the stock ship to use, the gun to use, and the engine
	 * @param shipPointer pointer to stock ship in indexShip from GameDatabase
	 * @param gunPointer pointer to gun in indexGuns from GDB
	 * @param engPointer pointer to engine in indexEng from GDB
	 * @return BasicShip
	 */
	public BasicShip buildShip(String shipPointer, String gunPointer, String engPointer){
		BasicShip build = new BasicShip();
		build.setImg(gdb.getShip(shipPointer).getImg().copy());
		build.setHealth(gdb.getShip(shipPointer).getHealth());
		build.setPoints(gdb.getShip(shipPointer).getPoints());
		build.setTotalWeight(gdb.getShip(shipPointer).getTotalWeight());
		build.setGunPtLength(gdb.getShip(shipPointer).getGunPtLength());
		build.setEngPtLength(gdb.getShip(shipPointer).getEngPtLength());
		build.setCollider(new Circle(0,0,16,24));
		build.getImg().setRotation(0);

		BasicGun g = buildGun(gunPointer);
		BasicEngine e = buildEngine(engPointer);
		
		build.setWeapon(g);
		build.setEngine(e);
		
		return build;
	}
	//=====GUN BUILDER=====
	/**
	 * builds a gun instance based on the pointer to the GameDatabase index
	 * @param gunPointer pointer to the indexGun in GameDatabase
	 * @return BasicGun
	 */
	public BasicGun buildGun(String gunPointer){
		BasicGun gun = new BasicGun();
		gun.setImg(gdb.getGun(gunPointer).getImg().copy());
		gun.setCoolDown(gdb.getGun(gunPointer).getCoolDown());
		gun.setCost(gdb.getGun(gunPointer).getCost());
		gun.setWeight(gdb.getGun(gunPointer).getWeight());
		gun.setProj(gdb.getGun(gunPointer).getProj());
		return gun;
	}
	
	//=====ENGINE BUILDERS=====
	/**
	 * builds an engine instance based on the pointer to the GameDatabase index
	 * @param engPointer pointer to the indexEng in GameDatabase
	 * @return BasicEngine
	 */
	public BasicEngine buildEngine(String engPointer){
		BasicEngine engine = new BasicEngine();
		engine.setCost(gdb.getEngine(engPointer).getCost());
		engine.setWeight(gdb.getEngine(engPointer).getWeight());
		engine.setTurnrate(gdb.getEngine(engPointer).getTurnrate());
		engine.setThrustX(gdb.getEngine(engPointer).getThrustX());
		engine.setThrustY(gdb.getEngine(engPointer).getThrustY());
		engine.setInGameImg(gdb.getEngine(engPointer).getInGameImg().copy());
		return engine;
	}
	
	//=====ARMOR BUILDERS=====
	//BasicArmor(int i, int val, Image ico, int cst)
	/**
	 * Builds the basic Armor
	 */
	public BasicArmor stockArmor(){ //TODO:: Armor is a \\Phase2// Operation
		BasicArmor armor;
		armor = new BasicArmor(1, 0, null, 1);
		return armor;
	}
	
	//=====PROJECTILE BUILDERS=====
	//Stock 20mm shot
	/**
	 * builds a shot instance based on the pointer to the GameDatabase index
	 * @param shotPointer pointer to the indexShot in GameDatabase
	 * @return BasicShot
	 */
	public BasicShot buildShot(String shotPointer){
		BasicShot shot = new BasicShot();
		shot.setImg(gdb.getShot(shotPointer).getImg().copy());
		shot.setDamage(gdb.getShot(shotPointer).getDamage());
		shot.setSpeed(gdb.getShot(shotPointer).getSpeed());
		shot.setInterval(gdb.getShot(shotPointer).getInterval());
		return shot;
	}
	//=====MISC STUFF=====
	//
	/**
	 * Make some sort of asteroid
	 * doodle doo doo
	 */
	public BaseEnt smallAst(){
		BaseEnt smallAsteroid;
		Image img = gdb.getImage("asteroid").copy();
		Circle col = new Circle(0,0, img.getWidth(), img.getHeight());
		smallAsteroid = new BaseEnt(img, col);
		return smallAsteroid;
	}
	
	

}
