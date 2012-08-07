package level.test;

import level.BasicLevel;
import level.LevelDataModel;
import level.NavPoint;
import level.Objective;
import level.TriggerTypes;
import level.actions.MessageAction;
import level.actions.SpawnShipAction;
import level.triggers.BasicTrigger;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

/**
 * simply generates a populated level that ClientGameplayState can use
 * @author Roohr
 * @version 1.5
 */
public class GenerateALevel {

	private LevelDataModel theLevel;
	private BasicTrigger tellMe;
	private BasicTrigger askMe;		//simple trigger
	private BasicTrigger spwn;
	private MessageAction say1;		//message actions
	private MessageAction ask1;		//
	private SpawnShipAction spawn;	//spawn a ship!
	private NavPoint alpha;
	
	public GenerateALevel(){
		this.theLevel = new LevelDataModel("Alpha v2 Level");
		this.tellMe = new BasicTrigger(TriggerTypes.SHIP);
		this.askMe = new BasicTrigger(TriggerTypes.SHOT);
		this.spwn = new BasicTrigger(TriggerTypes.SHOT);
		this.say1 = new MessageAction("MessageOut", 10, 75, "greetings", 2000);
		this.ask1 = new MessageAction("Queston", 10, 90, "hello world?", 1000);
		this.spawn = new SpawnShipAction("spawnShip",900,700,"zond4", "20mm","smallEngine");
		this.alpha = new NavPoint(0,0,"Alpha",true);
	}
	
	public LevelDataModel build(){
		theLevel.setBounds(new Rectangle(-1400,-1400,2800,2800), new Rectangle(-3200,-3200,6400,6400));
		
		tellMe.setName("TellMe");
		tellMe.setTargetName("MessageOut");
		tellMe.setX(151);
		tellMe.setY(50);
		tellMe.setCollider(new Rectangle(tellMe.getX(),tellMe.getY(),100,100));
		
		askMe.setName("AskMe");
		askMe.setTargetName("Queston");
		askMe.setX(50);
		askMe.setY(50);
		askMe.setCollider(new Rectangle(askMe.getX(),askMe.getY(), 100,100));
		
		spwn.setName("spawn ship!");
		spwn.setTargetName("spawnShip");
		spwn.setX(800);
		spwn.setY(400);
		spwn.setCollider(new Circle(spwn.getX(), spwn.getY(), 64));
		
		
		
		theLevel.addTrigger(tellMe);
		theLevel.addTrigger(askMe);
		theLevel.addTrigger(spwn);
		theLevel.addAction(say1);
		theLevel.addAction(ask1);
		theLevel.addAction(spawn);
		theLevel.addNavPoint(alpha);
//		theLevel.addNavPoint(beta);
		return this.theLevel;
	}
}
