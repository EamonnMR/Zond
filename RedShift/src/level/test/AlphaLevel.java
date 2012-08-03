package level.test;

import level.BasicAction;
import level.BasicLevel;
import level.NavPoint;
import level.Objective;
import level.TriggerTypes;
import level.triggers.BasicTrigger;

import org.newdawn.slick.geom.Rectangle;

public class AlphaLevel {
	
	//core level
	private BasicLevel theLevel;
	
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
	
	//actions
	private BasicAction spawnShipsAtAlpha;
	
	//
	
	public AlphaLevel(){
		buildNavs();
		buildObjs();
		buildTrigs();
		buildActions();
		buildLevelClass();
	}


	private void buildActions() {
		
	}

	private void buildTrigs() {
		this.alphaHit  = new BasicTrigger(TriggerTypes.SHIP);
	}

	private void buildObjs() {
		this.checkAlpha = new Objective("Patrol Nav Alpha");
		this.clearBeta = new Objective("Clear Beta of Meteors");
		this.attackCappa = new Objective("Disable Enemy Installation");
		this.scanDelta = new Objective("Scan Enemy Satellite");
		this.returnEpsilon = new Objective("Return to Base");
	}

	private void buildNavs() {
		this.alpha = new NavPoint(0,0,"Alpha", true);
		this.beta = new NavPoint(0,0,"Beta", true);
		this.cappa = new NavPoint(0,0,"Cappa", true);
		this.delta = new NavPoint(0,0,"Delta", true);
		this.epsilon = new NavPoint(0,0,"Epsilon", true);
	}
	
	private void buildLevelClass(){
		this.theLevel = new BasicLevel("Alpha v3 Level");
		this.theLevel.setBounds(new Rectangle(0,0,0,0), new Rectangle(0,0,0,0));
	}
}
