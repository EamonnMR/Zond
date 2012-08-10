package core;

import java.awt.Point;
import java.util.HashMap;

import level.BasicObjective;
import level.LevelDataModel;
import level.NavPoint;
import level.TriggerTypes;
import level.actions.BasicAction;
import level.triggers.BasicTrigger;
import level.triggers.CountTrigger;
import level.triggers.SpawnShip;
import level.triggers.ToggleNavPoint;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import ents.BasicShip;
import ents.EntityFactory;

/**
 * builds a level, used primarily by the gDB and entFac
 * @author Roohr
 *
 */
public class LevelBuilder {

	private EntityFactory entFac;
	
	//nav points
	private NavPoint navAlpha;
	private NavPoint navBeta;
	private NavPoint navCappa;
	private NavPoint navDelta;
	private NavPoint navEpsilon;
	
	//objectives
	private BasicObjective checkAlpha;
	private BasicObjective clearBeta;
	private BasicObjective attackCappa;
	private BasicObjective scanDelta;
	private BasicObjective returnEpsilon;
	
	//triggers
	private BasicTrigger alphaHit;
	private SpawnShip makeShip1;
	private SpawnShip makeShip2;
	private SpawnShip makeShip3;
	private ToggleNavPoint togNavBeta;
	
//	private BasicTrigger betaClear;
	private CountTrigger counter;
	
	//actions

	
	public LevelBuilder(){
	}
	
	public HashMap<String, NavPoint> buildNavPoints(){
		HashMap<String, NavPoint> points = new HashMap<String, NavPoint>();
		
		navAlpha = new NavPoint(0, 0, "Alpha", true);
		navBeta = new NavPoint(100, 100, "Beta", false);
		
		points.put(navAlpha.getName(), navAlpha);
		points.put(navBeta.getName(), navBeta );
		
		return points;
	}
	
	public HashMap<String, BasicAction> buildActions(){
		HashMap<String, BasicAction> acts = new HashMap<String, BasicAction>();
		return acts;
	}
	
	public HashMap<String, BasicTrigger> buildTriggers(){
		HashMap<String, BasicTrigger> trigs = new HashMap<String, BasicTrigger>();

//		private BasicTrigger alphaHit;
		alphaHit = new BasicTrigger(TriggerTypes.SHIP);
		alphaHit.setName("alphaHit");
		alphaHit.setX(0);
		alphaHit.setY(0);
		alphaHit.setCollider(new Circle(0,0,48));
		
		
//		private SpawnShip makeShip1;
		makeShip1 = new SpawnShip(null, entFac.stockVoskhod());
		makeShip1.setName("makeShip1");
		makeShip1.setX(-100);
		makeShip1.setY(-100);
		makeShip1.setCollider(null);
		alphaHit.setTargetName(makeShip1.getName());
		
//		private SpawnShip makeShip2;
		
//		private SpawnShip makeShip3;
		
//		private ToggleNavPoint togNavBeta;
		togNavBeta = new ToggleNavPoint(null, navBeta, true);
		togNavBeta.setName("enableBeta");
		togNavBeta.setX(0);
		togNavBeta.setY(0);
		togNavBeta.setCollider(null);
		makeShip1.setTargetName(togNavBeta.getName());
		
		trigs.put(alphaHit.getName(),alphaHit);
		trigs.put(makeShip1.getName(),makeShip1);
		trigs.put(togNavBeta.getName(), togNavBeta);
		return trigs;
	}
	
	public HashMap<String, BasicObjective> buildObjectives(){
		HashMap<String, BasicObjective> objs = new HashMap<String, BasicObjective>();
		
		return objs;
	}
	
	public LevelDataModel buildLevel(EntityFactory ef){
		entFac = ef;
		LevelDataModel ldm = new LevelDataModel("Alpha v4 Level");
		ldm.setBounds(new Rectangle(-3200,-3200,6400,6400), new Rectangle(-4800,-4800,9600,9600));
		ldm.setNeedUpdate(false);
		
		ldm.setNavMap(buildNavPoints());
		ldm.setActionMap(buildActions());
		ldm.setTriggerMap(buildTriggers());
		ldm.setObjectives(buildObjectives());
		
		return ldm;
	}
	
	//V-----------INTERNAL METHODS -------V
	private Point[] getLocus(){
		Point[] locus = new Point[3];
		locus[0] = new Point();
		locus[0].setLocation(2163, 2560);
		locus[1] = new Point();
		locus[1].setLocation(2184, 2400);
		locus[2] = new Point();
		locus[2].setLocation(2480, 2624.0);
		return locus;
	}
	
	private HashMap<String, BasicShip> getShips(){
		HashMap<String, BasicShip> ships = new HashMap<String, BasicShip>();
		
		BasicShip ship1 = entFac.stockGem();
		ships.put(ship1.getName(), ship1);
		
		BasicShip ship2 = entFac.stockVoskhod();
		ships.put(ship2.getName(), ship2);
		
		BasicShip ship3 = entFac.stockVostok();
		ships.put(ship3.getName(), ship3);

		return ships;
	}
	
	public void setEntFac(EntityFactory ef){
		this.entFac = ef;
	}
	
}
