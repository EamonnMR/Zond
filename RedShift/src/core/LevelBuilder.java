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
import level.triggers.DeathTrigger;
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
//	
//	//objectives
//	private BasicObjective checkAlpha;
//	private BasicObjective clearBeta;
//	private BasicObjective attackCappa;
//	private BasicObjective scanDelta;
//	private BasicObjective returnEpsilon;
	
	//actions

	
	public LevelBuilder(){
	}
	
	public HashMap<String, NavPoint> buildNavPoints(){
		HashMap<String, NavPoint> points = new HashMap<String, NavPoint>();
		
		navAlpha = new NavPoint(2584, 2088, "Alpha", true);
		navBeta = new NavPoint(-2336, 5670, "Beta", true);
		navCappa = new NavPoint(-2816, -2592, "Cappa", true);
		navDelta = new NavPoint(1928, -2104, "Delta", true);
		navEpsilon = new NavPoint(0, 0, "Epsilon", true);
		
		points.put(navAlpha.getName(), navAlpha);
		points.put(navBeta.getName(), navBeta );
		points.put(navCappa.getName(), navCappa );
		points.put(navDelta.getName(), navDelta );
		points.put(navEpsilon.getName(), navEpsilon );
		
		return points;
	}
	
	public HashMap<String, BasicAction> buildActions(){
		HashMap<String, BasicAction> acts = new HashMap<String, BasicAction>();
		return acts;
	}
	
	public HashMap<String, BasicTrigger> buildTriggers(){
		HashMap<String, BasicTrigger> trigs = new HashMap<String, BasicTrigger>();
		
		//INI TRIGGERS
		BasicTrigger fireINI = new BasicTrigger(TriggerTypes.SHIP);
		fireINI.setName("fireINI");
		fireINI.setX(512);
		fireINI.setY(396);
		fireINI.setCollider(new Rectangle(0,0, 1024,768));
		
		SpawnShip iniSkyLab = new SpawnShip(null, entFac.buildShip("skylab", null, null));
		iniSkyLab.setName("iniSkyLab");
		iniSkyLab.setX(0);
		iniSkyLab.setY(0);
		iniSkyLab.setCollider(null);
		fireINI.setTargetName(iniSkyLab.getName());
		
		SpawnShip iniLunar = new SpawnShip(null, entFac.stockZond());
		iniLunar.setName("iniLunar");
		iniLunar.setX(0);
		iniLunar.setY(256);
		iniLunar.setCollider(null);
		iniSkyLab.setTargetName(iniLunar.getName());
		
		trigs.put(fireINI.getName(), fireINI);
		trigs.put(iniSkyLab.getName(), iniSkyLab);
		trigs.put(iniLunar.getName(), iniLunar);
		
		//ALPHA OBJECTIVE
//		private BasicTrigger alphaHit;
		BasicTrigger alphaHit = new BasicTrigger(TriggerTypes.SHIP);
		alphaHit.setName("alphaHit");
		alphaHit.setX(2584);
		alphaHit.setY(2088);
		alphaHit.setCollider(new Circle(2584,2088,64));
		
		
//		private SpawnShip makeShip1;
		SpawnShip makeShip1 = new SpawnShip(null, entFac.stockVoskhod());
		makeShip1.setName("makeShip1");
		makeShip1.setX(2000);
		makeShip1.setY(2088);
		makeShip1.setCollider(null);
		alphaHit.setTargetName(makeShip1.getName());
		
//		private SpawnShip makeShip2;
		SpawnShip makeShip2 = new SpawnShip(null, entFac.stockVoskhod());
		makeShip2.setName("makeShip2");
		makeShip2.setX(2584);
		makeShip2.setY(1500);
		makeShip2.setCollider(null);
		makeShip1.setTargetName(makeShip2.getName());
		
//		private SpawnShip makeShip3;
		SpawnShip makeShip3 = new SpawnShip(null, entFac.stockVoskhod());
		makeShip3.setName("makeShip3");
		makeShip3.setX(3000);
		makeShip3.setY(2088);
		makeShip3.setCollider(null);
		makeShip2.setTargetName(makeShip3.getName());

		//alpha counter trigger
		CountTrigger killAllatAlpha = new CountTrigger(null, 3, "killAllatAlpha");
		
		//alpha death triggers
		DeathTrigger ship1Death = new DeathTrigger(null, "ship1Death");
		ship1Death.setTargetName(killAllatAlpha.getName());
		makeShip1.getShip().setTriggerTargetName(ship1Death.getName());
		
		DeathTrigger ship2Death = new DeathTrigger(null, "ship2Death");
		ship2Death.setTargetName(killAllatAlpha.getName());
		makeShip2.getShip().setTriggerTargetName(ship2Death.getName());
		
		DeathTrigger ship3Death = new DeathTrigger(null, "ship3Death");
		ship3Death.setTargetName(killAllatAlpha.getName());
		makeShip3.getShip().setTriggerTargetName(ship3Death.getName());
		
		//complete alpha objective
		ToggleNavPoint togAlpha = new ToggleNavPoint(null, navAlpha, false);
		togAlpha.setName("togAlpha");
		killAllatAlpha.setTargetName(togAlpha.getName());
		
		
		
		trigs.put(alphaHit.getName(),alphaHit);
		trigs.put(makeShip1.getName(),makeShip1);
		trigs.put(makeShip2.getName(),makeShip2);
		trigs.put(makeShip3.getName(),makeShip3);
		trigs.put(ship1Death.getName(), ship1Death);
		trigs.put(ship2Death.getName(), ship2Death);
		trigs.put(ship3Death.getName(), ship3Death);
		trigs.put(killAllatAlpha.getName(), killAllatAlpha);
		trigs.put(togAlpha.getName(), togAlpha);
		
		//BETA OBJECTIVE
		
		
		
		
		return trigs;
	}
	
	public HashMap<String, BasicObjective> buildObjectives(){
		HashMap<String, BasicObjective> objs = new HashMap<String, BasicObjective>();
		
		return objs;
	}
	
	public LevelDataModel buildLevel(EntityFactory ef){
		entFac = ef;
		LevelDataModel ldm = new LevelDataModel("Alpha v4 Level");
		ldm.setBounds(new Rectangle(-6400,-6400,12800,12800), new Rectangle(-7000,-7000,14000,14000));
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
