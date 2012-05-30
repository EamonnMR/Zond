package level;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * core level class that does all level things...ever
 * @author proohr
 * @verion 1.0
 * the level class is primarily a data class that holds a collection of triggers
 * and actions. It handles the calling of triggers to actions, and handling updates
 * on specific actions. Updates on the level are run per-frame inside the main 
 * update loop in ClientGameplayState; aka Level's update DOES NOT take control of
 * the update loop, rather it executes in sequence with everything else in the primary
 * update.
 * 
 * right now, we can pass whole HashMaps of triggers and actions to the level for easy setup
 * I still need to make a 'validate' method that checks to make sure triggers hit the right actions
 * 
 * in the future, I plan on us being able to pass trigger and action queues as a form of simple
 * script injection, but it may not come to that; so for now, one cannot pass queues to Level.
 */
public class BasicLevel {

	private String levelName;								//name of level
	private HashMap<String, BasicTrigger> levelTriggerMap;	//collection of triggers in the level
	private HashMap<String, BasicAction> levelActionMap;	//collection of actions in the level
	private Queue<BasicTrigger> executeTriggers;			//queue of triggers to execute
	private Queue<BasicAction> executeActions;				//queue of actions to execute
	private boolean needsUpdate;							//does the level need to update?

	public BasicLevel(String name){
		this.levelName = name;
		this.levelTriggerMap = new HashMap<String, BasicTrigger>();
		this.levelActionMap = new HashMap<String, BasicAction>();
		executeTriggers = new LinkedList<BasicTrigger>();
		executeActions = new LinkedList<BasicAction>();
		
	}
	
	/**
	 * the meat and potatoes of the level class
	 * this method goes through the triggers to find which are triggered, and puts those in a queue
	 * going in FIFO, it activates the corresponding actions and places them in a queue (to maintain FIFO)
	 * then runs the proper methods for the actions, either action.ini() or action.update()
	 * @param delta
	 */
	public void update(int delta){
		//find which triggers are active
		for(Map.Entry<String, BasicTrigger> trigs : levelTriggerMap.entrySet()){
			if(trigs.getValue().isTrigged()){
				//put these triggers into a queue, the whole trigger rather than just the target
				//why? so we can remove the triggers that have been fired
				executeTriggers.add(trigs.getValue());
			}
		}
		
		//go through the trigger queue, select the trigger's action, put it on the action queue
		while(executeTriggers.iterator().hasNext()){
			executeActions.add(levelActionMap.get(executeTriggers.iterator().next().getTargetName()));
			executeTriggers.remove(executeTriggers.iterator().next());
		}
		
		//fire the actions
		while(executeActions.iterator().hasNext()){
			
			//if the action has not started; start it, and flag as started
			if(executeActions.iterator().next().isIni()){
				executeActions.iterator().next().setIni(false);
			
			//if the action has started, run its update for this frame	
			}else if(executeActions.iterator().next().isUpdate()){
				executeActions.iterator().next().update(delta);
			
			//if the action has finished, remove the action off the queue
			}else if(executeActions.iterator().next().isDone()){
				
				executeActions.remove(executeActions.iterator().next());
			}		
		}
	}
	
	public void addTrigger(BasicTrigger trig){
		levelTriggerMap.put(trig.getName(), trig);
	}

	public void addAction(BasicAction act){
		levelActionMap.put(act.getName(), act);
	}
	
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public HashMap<String, BasicTrigger> getLevelTriggerMap() {
		return levelTriggerMap;
	}

	public void setLevelTriggerMap(HashMap<String, BasicTrigger> levelTriggerMap) {
		this.levelTriggerMap = levelTriggerMap;
	}

	public HashMap<String, BasicAction> getLevelActionMap() {
		return levelActionMap;
	}

	public void setLevelActionMap(HashMap<String, BasicAction> levelTriggerAction) {
		this.levelActionMap = levelTriggerAction;
	}

//	public Queue<BasicTrigger> getExecuteTriggers() {
//		return executeTriggers;
//	}
//
//	public void setExecuteTriggers(Queue<BasicTrigger> executeTriggers) {
//		this.executeTriggers = executeTriggers;
//	}
//
//	public Queue<BasicAction> getExecuteActions() {
//		return executeActions;
//	}
//
//	public void setExecuteActions(Queue<BasicAction> executeActions) {
//		this.executeActions = executeActions;
//	}
	
	public boolean isNeedsUpdate() {
		return needsUpdate;
	}

	public void setNeedsUpdate(boolean needsUpdate) {
		this.needsUpdate = needsUpdate;
	}


}
