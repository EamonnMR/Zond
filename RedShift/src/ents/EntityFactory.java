package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import core.GameDatabase;
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
	//	BasicShip(int id, Image im, double hp, double points, BasicArmor arm, BasicEngine eng, BasicGun gun, double gunPt, double engPt)
	/**
 	* generic ship maker, plug-in values, get a ship
 	* @return BasicShip
 	*/
	public BasicShip buildShip(int ID, Image im, double hp, double points, BasicArmor am, BasicEngine eng, BasicGun gn, double gunPt, double engPt, Shape collider){
		BasicShip someShip;
		someShip = new BasicShip(ID, im, hp, points, am, eng, gn, gunPt, engPt, collider);
		return someShip;
	}
	
	/**
	 * Builds a basic Mercury class BasicShip
	 * @return BasicShip
	 */
	public  BasicShip stockMercury(){
		Circle collider;
		collider = new Circle(0,0, 16, 24);
		Image img = gdb.getImage("mercury").copy();	
		return buildShip(0, img, 10, 1, null, null, null, -28, 24, collider);
	}
	
	/**
	 * Builds a basic Gemini class BasicShip
	 *@return BasicShip
	 */
	public BasicShip stockGem(){
		Polygon collider;
		collider = new Polygon();
		Image img = gdb.getImage("gemini").copy();
		return buildShip(1, img, 15,1,null,null,null,-30,24,collider);
	}
	
	/**
	 * Builds a basic Lunar Orbiter class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockLunar(){
		Polygon collider;
		collider = new Polygon();
		Image img = gdb.getImage("lunar").copy();
		return buildShip(2, img, 15,1,null,null,null,-30,24,collider);
	}
	
	/**
	 * Builds a basic Vostok class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVostok(){
		Circle collider;
		collider = new Circle(3,0, 16, 24);
		Image img = gdb.getImage("vostok").copy();
		return buildShip(1, img,15,1,null,null,null,-30,24, collider);
	}
	
	/**
	 * Builds a basic Voskhod class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVoskhod(){
		Circle collider;
		collider = new Circle(0,0, 16, 24);
		Image img = gdb.getImage("voskhod").copy();
		return buildShip(4,img,15,1,null,null,null,-30,24,collider);
	}

	/**
	 * Builds a basic Zond4 class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockZond(){
		Circle collider;
		collider = new Circle(0,0,16,24);
		Image img = gdb.getImage("zond4").copy();
		return buildShip(5, img, 15,1,null,null,null,-30,24,collider);
	}
	
	//=====GUN BUIDLERS=====
	//BasicGun(int i, int wt, int cst, int pid, int dmg, Image im)
	/**
	 * Builds a stock 20mm cannon
	 */
	public BasicGun stock20mm(){
		BasicGun twenty;
		Image img = gdb.getImage("gun1").copy();
		Image shot = gdb.getImage("shot1").copy();
		twenty = new BasicGun(1,0,0,0,2, img, shot, 75);
		return twenty;
	}
	
	
	//=====ENGINE BUILDERS=====
	//BasicEngine(int i, int wt, int cst, int pid, Image ingame, Image gui, Image sparks)
	/**
	 * Builds the basic engine
	 */
	public BasicEngine stockEngine(){
		BasicEngine thruster;
		Image img = gdb.getImage("eng1").copy();
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
	 * Make some sort of asteroid
	 */
	public BaseEnt smallAst(){
		BaseEnt smallAsteroid;
		Image img = gdb.getImage("asteroid").copy();
		Circle col = new Circle(0,0, img.getWidth(), img.getHeight());
		smallAsteroid = new BaseEnt(img, col);
		return smallAsteroid;
	}
	
	

}
