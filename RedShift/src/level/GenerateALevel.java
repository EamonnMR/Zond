package level;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import level.actions.MessageAction;
import level.actions.SpawnShipAction;

/**
 * simply generates a populated level that ClientGameplayState can use
 * @author Roohr
 * @version 1.5
 */
public class GenerateALevel {

	private BasicLevel theLevel;
	private BasicTrigger tellMe;
	private BasicTrigger askMe;		//simple trigger
	private BasicTrigger spwn;
	private MessageAction say1;		//message actions
	private MessageAction ask1;		//
	private SpawnShipAction spawn;	//spawn a ship!
	private SpawnShipAction respawn;//respawns a player
	TriggerTypes trigTypes;
	
	public GenerateALevel(){
		this.theLevel = new BasicLevel("Alpha v2 Level");
		this.tellMe = new BasicTrigger(trigTypes.SHIP);
		this.askMe = new BasicTrigger(trigTypes.SHOT);
		this.spwn = new BasicTrigger(trigTypes.SHOT);
		this.say1 = new MessageAction("MessageOut", 10, 75, "greetings", 2000);
		this.ask1 = new MessageAction("Queston", 10, 90, "hello world?", 1000);
		this.spawn = new SpawnShipAction("spawnShip",900,700,"lunar", "20mm","smallEngine");
	}
	
	public BasicLevel build(){
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
		return this.theLevel;
	}
}
