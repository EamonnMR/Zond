package level;

import java.util.HashMap;
import java.util.Map;

/**
 * core level class that does all level things...ever
 * @author proohr
 * @verion 1.o
 */
public class BasicLevel {

	//variables
	private HashMap<String, BasicTrigger> levelTriggers;
	private HashMap<String, BasicAction> levelActions;
	private String levelName;
	private String outgoingMessage;
	private boolean levelOver;
	
	
	//constructor
	public BasicLevel(){}
	
	//methods
	public void levelIni(){
		
	}
	
	public void update(int delta, boolean trigState){

	}
	
	public void validateEnts(){
		
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

}
