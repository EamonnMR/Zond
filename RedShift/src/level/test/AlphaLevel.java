package level.test;

import java.awt.Point;
import java.util.HashMap;

import level.BasicLevel;
import level.NavPoint;
import level.Objective;
import level.TriggerTypes;
import level.actions.MultiObjSpawner;
import level.actions.MultiShipSpawner;
import level.actions.SpawnShipAction;
import level.triggers.BasicTrigger;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import ents.BasicShip;
import ents.EntityFactory;

public class AlphaLevel {
	
	//core level
	private BasicLevel theLevel;
	private EntityFactory entFac;
	
	//nav points
	private NavPoint alpha;
	private NavPoint beta;
	private NavPoint cappa;
	private NavPoint delta;
	private NavPoint epsilon;
	
	//objectives
	private Objective checkAlpha;
	private Objective clearBeta;
	private Objective attackCappa;
	private Objective scanDelta;
	private Objective returnEpsilon;
	
	
	//triggers
	private BasicTrigger alphaHit;
	private BasicTrigger betaClear;
	
	
	//actions
	private MultiShipSpawner spawnShipsAtAlpha;
	private MultiObjSpawner spawnAsteroids;
	private SpawnShipAction makeSatellite;
	
	public AlphaLevel(EntityFactory ef){
		entFac = ef;
		buildLevelClass();
		buildNavs();
		buildObjs();
		buildActions();
		buildTrigs();
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
	
	private Point[] getLocus(){
		//2272,2560
		Point[] locus = new Point[3];
		locus[0] = new Point();
		locus[0].setLocation(2163, 2560);
		locus[1] = new Point();
		locus[1].setLocation(2184, 2400);
		locus[2] = new Point();
		locus[2].setLocation(2480, 2624.0);
		return locus;
	}
	
	private void buildActions() {
		spawnShipsAtAlpha = new MultiShipSpawner("ShipsToAlpha", 0,0, getLocus(), getShips());
		
		this.theLevel.addAction(spawnShipsAtAlpha);
	}
	
	private void buildTrigs() {
		this.alphaHit  = new BasicTrigger(TriggerTypes.SHIP);
		this.alphaHit.setName("alphaHit");
		this.alphaHit.setTargetName(spawnShipsAtAlpha.getName());
		this.alphaHit.setCollider(new Circle(2272,2560, 24));
		this.alphaHit.setX(2272);
		this.alphaHit.setY(2560);
		
		this.theLevel.addTrigger(alphaHit);
	}
	
	private void buildObjs() {
		this.checkAlpha = new Objective("Patrol Nav Alpha");
		this.checkAlpha.setComplete(false);
		this.checkAlpha.setBlurb("Recon point Alpha.");
		this.checkAlpha.setFullDesc("Enemy activity has increased, we need you to recon point Alpha, make sure the area is clear.");
		
		this.clearBeta = new Objective("Clear Beta of Meteors");
		this.clearBeta.setComplete(false);
		this.clearBeta.setBlurb("Remove meteors from Point Beta.");
		this.checkAlpha.setFullDesc("We want to install a new spy sat at point Beta, remove those meteors so we can move a sat to that area.");
		
		this.attackCappa = new Objective("Disable Enemy Installation");
		this.attackCappa.setComplete(false);
		this.attackCappa.setBlurb("Destroy enemy orbital platform.");
		this.attackCappa.setFullDesc("A Russian Salyut class platform has been moved into the operational area for maneuvers, find it and kill it.");
		
		this.scanDelta = new Objective("Scan Enemy Satellite");
		this.scanDelta.setComplete(false);
		this.scanDelta.setBlurb("Scan enemy satellite for critical data.");
		this.scanDelta.setFullDesc("We believe the Russians are planning an attack, scan their satellite for any data.");
		
		this.returnEpsilon = new Objective("Return to Base");
		this.returnEpsilon.setComplete(false);
		this.returnEpsilon.setBlurb("Return home");
		this.returnEpsilon.setFullDesc("Once all objectivs are met, return to base.");
		
		theLevel.addObjective(checkAlpha);
		theLevel.addObjective(clearBeta);
		theLevel.addObjective(attackCappa);
		theLevel.addObjective(scanDelta);
		theLevel.addObjective(returnEpsilon);
	}

	private void buildNavs() {

		this.alpha = new NavPoint(2272,2560,"Alpha", true);
		this.beta = new NavPoint(-2216,2288,"Beta", true);
		this.cappa = new NavPoint(-2344,-1976,"Cappa", true);
		this.delta = new NavPoint(2224,-2672,"Delta", true);
		this.epsilon = new NavPoint(0,0,"Epsilon", true);
		
//		theLevel.addNavPoint(alpha);
//		theLevel.addNavPoint(beta);
//		theLevel.addNavPoint(cappa);
//		theLevel.addNavPoint(delta);
//		theLevel.addNavPoint(epsilon);
	}
	
	private void buildLevelClass(){
		this.theLevel = new BasicLevel("Alpha v3 Level");
		this.theLevel.setBounds(new Rectangle(-3200,-3200,6400,6400), new Rectangle(-4800,-4800,9600,9600));
	}
	
	public BasicLevel getLevel(){
		return this.theLevel;
	}
}
