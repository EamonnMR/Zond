package level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
 * but the queues are polled in clientgameplaystate
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
		this.setNeedsUpdate(false);
	}
	
	/**
	 * the meat and potatoes of the level class
	 * this method goes through the triggers to find which are triggered, and puts those in a queue
	 * going in FIFO, it activates the corresponding actions and places them in a queue (to maintain FIFO)
	 * then runs the proper methods for the actions, either action.ini() or action.update()
	 * PS: this method is For-Loop city :P
	 * @param delta
	 */
	public void update(int delta){
		
		
		//find which triggers are active
		for(BasicTrigger trig : levelTriggerMap.values()){
			if(trig.isTrigged()){
				//put these triggers into a queue, the whole trigger rather than just the target
				//why? so we can remove the triggers that have been fired
				executeTriggers.add(trig);
				System.out.println("Trigger: "+trig.getName()+" added to queue");
			}
		}
		
		//go through the trigger queue, select the trigger's action, put it on the action queue
		for(BasicTrigger trig : executeTriggers){
			executeActions.add(levelActionMap.get(trig.getTargetName()));
//			executeTriggers.remove(trig);
			System.out.println("Trigger: "+ trig.getName()+"has been executed");
		}
		
		//fire the actions
		for(BasicAction act : executeActions){
			
			//if the action has not started; start it, and flag as started
			if(act.isIni()==true){
				act.ini();
				System.out.println("Trigger: "+act.getName()+"is initialized");
			//if the action has started, run its update for this frame	
			}else if(act.isUpdate()){
				//note: to end the 'update' state, simply set isUpdate=false, isDone=true inside action.update()
				act.update(delta);
				System.out.println("Trigger: "+act.getName()+"is updating");
			//if the action has finished, remove the action off the queue
			}
		}
		
		cleanTriggers();
		cleanActions();
		
		if(executeTriggers.isEmpty() && executeActions.isEmpty()){
			setNeedsUpdate(false);
		}
	}
	
	//garbage day! 
	private void cleanTriggers(){
		ArrayList<String> cleanTrigs = new ArrayList<String>();
		while(executeTriggers.iterator().hasNext()){
			System.out.println("Trigger: has been removed");
			cleanTrigs.add(executeTriggers.element().getName());
			executeTriggers.remove();
		}
		for(String str : cleanTrigs){
			levelTriggerMap.remove(str);
		}

	}
	
	private void cleanActions(){
		ArrayList<String> cleanAct = new ArrayList<String>();
		while(executeActions.iterator().hasNext()){
				System.out.println("Action: has been removed");
				cleanAct.add(executeActions.element().getName());
				executeActions.remove();
		}
		for(String str : cleanAct){
			levelTriggerMap.remove(str);
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

	public Queue<BasicTrigger> getExecuteTriggers() {
		return executeTriggers;
	}
//
//	public void setExecuteTriggers(Queue<BasicTrigger> executeTriggers) {
//		this.executeTriggers = executeTriggers;
//	}
//
	public Queue<BasicAction> getExecuteActions() {
		return executeActions;
	}
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
