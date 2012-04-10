package com.foo.redshift.ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import com.foo.redshift.core.GameDatabase;

/**
 * Factory design pattern, builds all necessary game classes
 * @author Roohr
 * @version 1.0
 */
public class EntityFactory {

	//vars
	private GameDatabase gdb;
	
	//const
	public EntityFactory(GameDatabase gdbx){
		gdb = gdbx;
	}
	
	//methods
	/**
	 * Give EntFac a GameDatabase
	 * @param GameDatabase incoming database
	 */
	public void setDatabase(GameDatabase newDB){
		gdb = newDB;
	}
	/**
	 * get GameDatabase from EntFac
	 * @return GameDatabase 
	 */
	public GameDatabase getDatabase(){
		return gdb;
	}
	
	//=====SHIP BUILDERS=====
	/**
	 * Builds a basic Mercury class BasicShip
	 * @return BasicShip
	 */
	public  BasicShip stockMercury(){
//		BasicShip(int id, Image im, double hp, double points, BasicArmor arm, BasicEngine eng, BasicGun gun, double gunPt, double engPt)
		Circle collider;
		collider = new Circle(0,0, 16, 24);
		Image img = gdb.getShipImage(0).copy();
		BasicShip merc;
		merc = new BasicShip(0, img, 10, 1, null, null, null, -28, 24, collider);
		return merc;
	}
	
	/**
	 * Builds a basic Vostok class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVostok(){
		Circle collider;
		collider = new Circle(0,0, 16, 24);
		Image img = gdb.getShipImage(1).copy();
		BasicShip vost;
		vost = new BasicShip(1, img,15,1,null,null,null,-30,24, collider);
		return vost;
	}
	
	//=====GUN BUIDLERS=====
	//BasicGun(int i, int wt, int cst, int pid, int dmg, Image im)
	/**
	 * Builds a stock 20mm cannon
	 */
	public BasicGun stock20mm(){
		BasicGun twenty;
		Image img = gdb.getGunIMG(0).copy();
		Image shot = gdb.getShotIMG(0).copy();
		twenty = new BasicGun(1,0,0,0,2, img, shot);
		return twenty;
	}
	
	
	//=====ENGINE BUILDERS=====
	//BasicEngine(int i, int wt, int cst, int pid, Image ingame, Image gui, Image sparks)
	/**
	 * Builds the basic engine
	 */
	public BasicEngine stockEngine(){
		BasicEngine thruster;
		Image img = gdb.getEngIMG(0).copy();
		thruster = new BasicEngine(1, 0, 0,0, img,null,null, 0.4f );
		return thruster;
	}
	
	
	//=====ARMOR BUILDERS=====
	//BasicArmor(int i, int val, Image ico, int cst)
	/**
	 * Builds the basic Armor
	 */
	public BasicArmor stockArmor(){
		BasicArmor armor;
		armor = new BasicArmor(1, 0, null, 1);
		return armor;
	}
	
	//=====PROJECTILE BUILDERS=====
	//Stock 20mm shot
	/**
	 * Builds a stock 20mm shot
	 */
	
	//=====MISC STUFF=====
	//
	/**
	 * 
	 */
	public BaseEnt smallAst(){
		BaseEnt smallAsteroid;
		Image img = gdb.getObjIMG(0).copy();
		Circle col = new Circle(0,0, img.getWidth(), img.getHeight());
		smallAsteroid = new BaseEnt(img, col);
		return smallAsteroid;
	}
	
}
