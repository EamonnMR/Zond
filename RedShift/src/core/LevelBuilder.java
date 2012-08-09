package core;

import java.awt.Point;
import java.util.HashMap;

import level.BasicObjective;
import level.LevelDataModel;
import level.NavPoint;
import level.TriggerTypes;
import level.actions.BasicAction;
import level.actions.EnableNavPoint;
import level.actions.MultiObjSpawner;
import level.actions.MultiShipSpawner;
import level.actions.SpawnShipAction;
import level.triggers.BasicTrigger;
import level.triggers.CountTrigger;

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
	private NavPoint alpha;
	private NavPoint beta;
	private NavPoint cappa;
	private NavPoint delta;
	private NavPoint epsilon;
	
	//objectives
	private BasicObjective checkAlpha;
	private BasicObjective clearBeta;
	private BasicObjective attackCappa;
	private BasicObjective scanDelta;
	private BasicObjective returnEpsilon;
	
	
	//triggers
	private BasicTrigger alphaHit;
	private BasicTrigger enableBeta;
	private BasicTrigger betaClear;
	private CountTrigger counter;
	
	//actions
	private MultiShipSpawner spawnShipsAtAlpha;
	private EnableNavPoint enBeta;
	private MultiObjSpawner spawnAsteroids;
	private SpawnShipAction makeSatellite;
	
	public LevelBuilder(){
	}
	
	public HashMap<String, NavPoint> buildNavPoints(){
		HashMap<String, NavPoint> points = new HashMap<String, NavPoint>();
		
		this.alpha = new NavPoint(250,250,"Alpha", true);
		this.beta = new NavPoint(700,700,"Beta", false);
		this.cappa = new NavPoint(-2344,-1976,"Cappa", false);
		this.delta = new NavPoint(2224,-2672,"Delta", false);
		this.epsilon = new NavPoint(0,0,"Epsilon", false);
		
		points.put(alpha.getName(), alpha);
		points.put(beta.getName(), beta);
		points.put(cappa.getName(), cappa);
		points.put(delta.getName(), delta);
		points.put(epsilon.getName(), epsilon);
		
		return points;
	}
	
	public HashMap<String, BasicAction> buildActions(){
		HashMap<String, BasicAction> acts = new HashMap<String, BasicAction>();
		
		spawnShipsAtAlpha = new MultiShipSpawner("ShipsToAlpha", 0,0, getLocus(), getShips());
		enBeta = new EnableNavPoint("ActivateBeta",beta);
		
		
		acts.put(spawnShipsAtAlpha.getName(), spawnShipsAtAlpha);
		acts.put(enBeta.getName(), enBeta);
		return acts;
	}
	
	public HashMap<String, BasicTrigger> buildTriggers(){
		HashMap<String, BasicTrigger> trigs = new HashMap<String, BasicTrigger>();
		
		this.alphaHit  = new BasicTrigger(TriggerTypes.SHIP);
		this.alphaHit.setName("alphaHit");
		this.alphaHit.setTargetName(spawnShipsAtAlpha.getName());
		this.alphaHit.setX(250);
		this.alphaHit.setY(250);
		this.alphaHit.setCollider(new Circle(alphaHit.getX(),alphaHit.getY(), 24));
		
		this.enableBeta = new BasicTrigger(null);
		this.enableBeta.setName("enableBetaNav");
		this.enableBeta.setTargetName(enBeta.getName());
		this.enableBeta.setX(0);
		this.enableBeta.setY(0);
		this.enableBeta.setCollider(null);
		this.spawnShipsAtAlpha.setTriggerTargetName(enableBeta.getName());
		
		this.counter = new CountTrigger(TriggerTypes.SHOT, 10);
		this.counter.setName("ShotCounter");
		this.counter.setX(2272);
		this.counter.setY(2560);
		this.counter.setCollider(new Rectangle(counter.getX(), counter.getY(), 64, 64));
		
		
		trigs.put(alphaHit.getName(), alphaHit);
		trigs.put(enableBeta.getName(), enableBeta);
		trigs.put(counter.getName(), counter);
		return trigs;
	}
	
	public HashMap<String, BasicObjective> buildObjectives(){
		HashMap<String, BasicObjective> objs = new HashMap<String, BasicObjective>();
		
		this.checkAlpha = new BasicObjective("Patrol Nav Alpha",alphaHit,null );
		this.checkAlpha.setComplete(false);
		this.checkAlpha.setBlurb("Recon point Alpha.");
		this.checkAlpha.setFullDesc("Enemy activity has increased, we need you to recon point Alpha, make sure the area is clear.");
		this.checkAlpha.setTrigger(alphaHit);
		
		this.clearBeta = new BasicObjective("Clear Beta of Meteors", null,null);
		this.clearBeta.setComplete(false);
		this.clearBeta.setBlurb("Remove meteors from Point Beta.");
		this.clearBeta.setFullDesc("We want to install a new spy sat at point Beta, remove those meteors so we can move a sat to that area.");
		
		this.attackCappa = new BasicObjective("Disable Enemy Installation",null,null);
		this.attackCappa.setComplete(false);
		this.attackCappa.setBlurb("Destroy enemy orbital platform.");
		this.attackCappa.setFullDesc("A Russian Salyut class platform has been moved into the operational area for maneuvers, find it and kill it.");
		
		this.scanDelta = new BasicObjective("Scan Enemy Satellite",null,null);
		this.scanDelta.setComplete(false);
		this.scanDelta.setBlurb("Scan enemy satellite for critical data.");
		this.scanDelta.setFullDesc("We believe the Russians are planning an attack, scan their satellite for any data.");
		
		this.returnEpsilon = new BasicObjective("Return to Base",null,null);
		this.returnEpsilon.setComplete(false);
		this.returnEpsilon.setBlurb("Return home");
		this.returnEpsilon.setFullDesc("Once all objectivs are met, return to base.");
		
		objs.put(checkAlpha.getName(), checkAlpha);
		objs.put(clearBeta.getName(), clearBeta);
		objs.put(attackCappa.getName(), attackCappa);
		objs.put(scanDelta.getName(), scanDelta);
		objs.put(returnEpsilon.getName(), returnEpsilon);
		
		return objs;
	}
	
	public LevelDataModel buildLevel(){
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
