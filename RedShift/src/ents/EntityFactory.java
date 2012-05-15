package ents;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import core.GameDatabase;
/**
 * Factory design pattern, builds all necessary game classes
 * @author Roohr
 * @version 1.0
 */
public class EntityFactory {

	//variables
	private static EntityFactory instance;
	
	//constructor
	protected EntityFactory(){
	}
	
	//methods
	public static EntityFactory getInstance(){
		
	    if (instance == null){
	    	instance = new EntityFactory();
		return instance;
	    }
	    return instance;
	}

	//TODO: when RedShift package is up and running, move the builder methods to RedShift's instantiated EntFac
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
		BasicShip temp = GameDatabase.getInstance().getShip("mercury");
		return temp;
	}
	
	/**
	 * Builds a basic Gemini class BasicShip
	 *@return BasicShip
	 */
	public BasicShip stockGem(){
		BasicShip temp = GameDatabase.getInstance().getShip("gemini");
		return temp;
	}
	
	/**
	 * Builds a basic Lunar Orbiter class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockLunar(){
		BasicShip temp = GameDatabase.getInstance().getShip("lunar");
		return temp;
	}
	
	/**
	 * Builds a basic Vostok class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVostok(){
		BasicShip temp = GameDatabase.getInstance().getShip("vostok");
		return temp;
	}
	
	/**
	 * Builds a basic Voskhod class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockVoskhod(){
		BasicShip temp = GameDatabase.getInstance().getShip("voskhod");
		return temp;
	}

	/**
	 * Builds a basic Zond4 class BasicShip
	 * @return BasicShip
	 */
	public BasicShip stockZond(){
		BasicShip temp = GameDatabase.getInstance().getShip("zond4");
		return temp;
	}
	
	//=====GUN BUIDLERS=====
	//BasicGun(int i, int wt, int cst, int pid, int dmg, Image im)
	/**
	 * Builds a stock 20mm cannon
	 */
	public BasicGun stock20mm(){
		BasicGun temp = GameDatabase.getInstance().getGun("20mm");
		BasicShot ts = GameDatabase.getInstance().getShot("twentyShot");
		temp.setProj(ts);
		return temp;
	}
	
	
	//=====ENGINE BUILDERS=====
	//BasicEngine(int i, int wt, int cst, int pid, Image ingame, Image gui, Image sparks, float turnrate, float thrustX, float thrustY)
	/**
	 * Builds the basic engine
	 */
	public BasicEngine stockEngine(){
		BasicEngine temp = GameDatabase.getInstance().getEngine("smallEngine");
		return temp;
	}
	
	/**
	 * builds a test engine that implements thrustX, thrustY, and turnrate
	 * using this will invalidate stockEngine in the BasicShip move methods
	 */
	public BasicEngine smallEngine(){
		BasicEngine temp = GameDatabase.getInstance().getEngine("smallEngine");
		return temp;
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
	 * doodle doo doo
	 */
	public BaseEnt smallAst(){
		BaseEnt smallAsteroid;
		Image img = GameDatabase.getInstance().getImage("asteroid").copy();
		Circle col = new Circle(0,0, img.getWidth(), img.getHeight());
		smallAsteroid = new BaseEnt(img, col);
		return smallAsteroid;
	}

}
