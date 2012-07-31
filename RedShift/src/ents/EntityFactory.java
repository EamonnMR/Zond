package ents;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;

import core.GameDatabase;
/**
 * Factory design pattern(sorta), builds all necessary game classes
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
	
	//=====GENERIC BUILDERS
	
	/**
	 * Builds a basicship given the stock ship to use, the gun to use, and the engine
	 * @param shipPointer pointer to stock ship in indexShip from GameDatabase
	 * @param gunPointer pointer to gun in indexGuns from GDB
	 * @param engPointer pointer to engine in indexEng from GDB
	 * @return BasicShip
	 */
	public BasicShip buildShip(String shipPointer, String gunPointer, String engPointer){
		BasicShip build = new BasicShip();
		build.setName(shipPointer);
		build.setImg(gdb.getShip(shipPointer).getImg().copy());
		build.setHealth(gdb.getShip(shipPointer).getHealth());
		build.setPoints(gdb.getShip(shipPointer).getPoints());
		build.setTotalWeight(gdb.getShip(shipPointer).getTotalWeight());
		build.setGunPtLength(gdb.getShip(shipPointer).getGunPtLength());
		build.setEngPtLength(gdb.getShip(shipPointer).getEngPtLength());
		build.setCollider(new Circle(gdb.getShip(shipPointer).getCollider().getX(),gdb.getShip(shipPointer).getCollider().getY(),gdb.getShip(shipPointer).getCollider().getHeight(),24));
		
		build.setRadarRadius(new Circle(gdb.getShip(shipPointer).getRadarRadius().getX(),gdb.getShip(shipPointer).getRadarRadius().getY(),gdb.getShip(shipPointer).getRadarRadius().getWidth(),24));
		build.getImg().setRotation(0);

		BasicGun g = buildGun(gunPointer);
		BasicEngine e = buildEngine(engPointer);
		
		build.setWeapon(g);
		build.setEngine(e);
		
		//XXX:hacky hack hack
		if(shipPointer=="lunar"){
			Polygon p = new Polygon();
			p.addPoint(-64, 42);
			p.addPoint(64, 42);
			p.addPoint(64, -42);
			p.addPoint(-64, -42);
			build.setCollider(p);
		}
		
		return build;
	}
	
	/**
	 * builds a gun instance based on the pointer to the GameDatabase index
	 * @param gunPointer pointer to the indexGun in GameDatabase
	 * @return BasicGun
	 */
	public BasicGun buildGun(String gunPointer){
		BasicGun gun = new BasicGun();
		gun.setName(gunPointer);
		gun.setImg(gdb.getGun(gunPointer).getImg().copy());
		gun.setCoolDown(gdb.getGun(gunPointer).getCoolDown());
		gun.setCost(gdb.getGun(gunPointer).getCost());
		gun.setWeight(gdb.getGun(gunPointer).getWeight());
		gun.setProj(gdb.getGun(gunPointer).getProj());
		return gun;
	}
	
	/**
	 * builds an engine instance based on the pointer to the GameDatabase index
	 * @param engPointer pointer to the indexEng in GameDatabase
	 * @return BasicEngine
	 */
	public BasicEngine buildEngine(String engPointer){
		BasicEngine engine = new BasicEngine();
		engine.setName(engPointer);
		engine.setCost(gdb.getEngine(engPointer).getCost());
		engine.setWeight(gdb.getEngine(engPointer).getWeight());
		engine.setTurnrate(gdb.getEngine(engPointer).getTurnrate());
		engine.setThrustX(gdb.getEngine(engPointer).getThrustX());
		engine.setThrustY(gdb.getEngine(engPointer).getThrustY());
		engine.setStrafeRate(gdb.getEngine(engPointer).getStrafeRate());
		engine.setInGameImg(gdb.getEngine(engPointer).getInGameImg().copy());
		return engine;
	}
	
	/**
	 * builds a shot instance based on the pointer to the GameDatabase index
	 * @param shotPointer pointer to the indexShot in GameDatabase
	 * @return BasicShot
	 */
	public BasicShot buildShot(String shotPointer){
		BasicShot shot = new BasicShot();
		BasicShot original = gdb.getShot(shotPointer);
		shot.setImg(original.getImg().copy());
		shot.setDamage(original.getDamage());
		shot.setSpeed(original.getSpeed());
		shot.setInterval(original.getInterval());
		//shot.setSnd(original.getSnd()); FIXME: Fix sound!
		return shot;
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
		return buildShip("gemini","60mm","smallEngine");
	}
	
	/**
	 * Builds a basic Lunar Orbiter class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockLunar(){
		return buildShip("lunar","105mm","smallEngine");
	}
	
	
	public BasicShip stockSky(){
		return buildShip("skylab", "105mm", "smallEngine");
	}
	/**
	 * Builds a basic Vostok class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVostok(){
		return buildShip("vostok","20mm","smallEngine");
	}
	
	/**
	 * Builds a basic Voskhod class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVoskhod(){
		return buildShip("voskhod","20mm","smallEngine");
	}

	/**
	 * Builds a basic Zond4 class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockZond(){
		return buildShip("zond4","20mm","smallEngine");
	}

	//=====GUN BUILDER=====

	
	//=====ENGINE BUILDERS=====

	
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

	public BasicShot stock20Shot(String pointer){
		return buildShot(pointer);
	}
	//=====MISC STUFF=====
	//
	/**
	 * Make some sort of asteroid
	 * doodle doo doo
	 */
//	public BaseEnt smallAst(){
//		BaseEnt smallAsteroid;
//		Image img = gdb.getImage("asteroid").copy();
//		Circle col = new Circle(0,0, img.getWidth(), img.getHeight());
//		smallAsteroid = new BaseEnt(img, col);
//		return smallAsteroid;
//	}
	
	

}
