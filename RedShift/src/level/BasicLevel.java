package level;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * core level class that does all level things...ever
 * @author proohr
 * @verion 1.o
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
	}
	
	public void update(int delta){
		//find which triggers are on
		for(Map.Entry<String, BasicTrigger> trigs : levelTriggerMap.entrySet()){
			if(trigs.getValue().isTrigged()){
				executeTriggers.add(trigs.getValue());
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

	public HashMap<String, BasicAction> getLevelTriggerAction() {
		return levelActionMap;
	}

	public void setLevelTriggerAction(HashMap<String, BasicAction> levelTriggerAction) {
		this.levelActionMap = levelTriggerAction;
	}

	public Queue<BasicTrigger> getExecuteTriggers() {
		return executeTriggers;
	}

	public void setExecuteTriggers(Queue<BasicTrigger> executeTriggers) {
		this.executeTriggers = executeTriggers;
	}

	public Queue<BasicAction> getExecuteActions() {
		return executeActions;
	}

	public void setExecuteActions(Queue<BasicAction> executeActions) {
		this.executeActions = executeActions;
	}
	
	public boolean isNeedsUpdate() {
		return needsUpdate;
	}

	public void setNeedsUpdate(boolean needsUpdate) {
		this.needsUpdate = needsUpdate;
	}


}
