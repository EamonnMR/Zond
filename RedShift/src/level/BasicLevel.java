package level;

import java.util.HashMap;
import java.util.Queue;

/**
 * core level class that does all level things...ever
 * @author proohr
 * @verion 1.o
 */
public class BasicLevel {

	//variables
	private HashMap<String, BasicTrigger> levelTriggers;	//manage all the triggers
	private HashMap<String, BasicAction> levelActions;		//manage all the actions
	private String levelName;								//for record keepign
	private String outgoingMessage;							//tells clientgameplaystate something
	private boolean levelOver;								//are we done yet?
	private boolean levelUpdate;							//does the level need to update?
	private Queue<BasicTrigger> levelTriggerQueue;				//the trigger stack, execute these when updated
	
	

	//constructor
	public BasicLevel(){
		levelTriggers = new HashMap<String, BasicTrigger>();
		levelActions = new HashMap<String, BasicAction>();
	}
	
	//methods
	public void levelIni(){
		
	}
	
	public void update(int delta, Queue<BasicTrigger> trigs){
		//do triggers
			//add strings to queue
			//deactivate triggers
		
		
		//doActions
			//execute actions in order of queue
	}
	
	public void validateTargets(){
		//checks trigger targetnames to make sure they're correct
		
	}
	
	public void doTrigger(){

	}
	
	/**
	 * add a trigger to the level
	 * @param trig
	 */
	public void addTrigger(BasicTrigger trig){
		levelTriggers.put(trig.getTriggerName(), trig);
	}
	
	/**
	 * add an action to the level
	 * @param act
	 */
	public void addAction(BasicAction act){
		levelActions.put(act.getActionName(), act);
	}
	
	/**
	 * get a specific trigger from the level
	 * @param name
	 * @return BasicTrigger
	 */
	public BasicTrigger getTrigger(String name){
		return levelTriggers.get(name);
	}
	
	/**
	 * get a specific action from the level
	 * @param name
	 * @return
	 */
	public BasicAction getAction(String name){
		return levelActions.get(name);
	}
	
	/**
	 * gets the levels entire collection of Triggers
	 * @return HashMap
	 */
	public HashMap<String, BasicTrigger> getLevelTriggers() {
		return levelTriggers;
	}

	/**
	 * sets the levels entire collection of Triggers
	 * good for some sort of level builder
	 * @param levelTriggers
	 */
	public void setLevelTriggers(HashMap<String, BasicTrigger> levelTriggers) {
		this.levelTriggers = levelTriggers;
	}

	/**
	 * gets the levels entire collection of Actions
	 * @return HashMap
	 */
	public HashMap<String, BasicAction> getLevelActions() {
		return levelActions;
	}

	/**
	 * sets the levels entire collection of Actions
	 * good for some sort of level builder
	 * @param levelActions
	 */
	public void setLevelActions(HashMap<String, BasicAction> levelActions) {
		this.levelActions = levelActions;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * alpha test of some sort of messaging system
	 * @return String 
	 */
	public String getOutgoingMessage() {
		return outgoingMessage;
	}

	/**
	 * formality, setting the message is not too important right now
	 * @param outgoingMessage
	 */
	public void setOutgoingMessage(String outgoingMessage) {
		this.outgoingMessage = outgoingMessage;
	}

	/**
	 * aww is the level over already?
	 * @return boolean
	 */
	public boolean isLevelOver() {
		return levelOver;
	}

	/**
	 * set whether the level is over or not
	 * @param levelOver
	 */
	public void setLevelOver(boolean levelOver) {
		this.levelOver = levelOver;
	}
	
	public boolean isLevelUpdate() {
		return levelUpdate;
	}

	public void setLevelUpdate(boolean levelUpdate) {
		this.levelUpdate = levelUpdate;
	}
	
	public Queue<BasicTrigger> getLevelTriggerQueue() {
		return levelTriggerQueue;
	}

	public void setLevelTriggerQueue(Queue<BasicTrigger> levelTriggerQueue) {
		this.levelTriggerQueue = levelTriggerQueue;
	}


}
