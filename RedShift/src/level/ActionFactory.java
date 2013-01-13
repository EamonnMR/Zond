package level;

import level.actions.BasicAction;
import level.actions.TimerAction;

/**
 * just like ent and trig facs, this builds actions from strings!
 * @author proohr
 * @version 1.0
 */
public class ActionFactory {

	public ActionFactory(){}
	
	public BasicAction buildAction(String classType, String...args){
		BasicAction act;
		if(classType.equals("timer")){
			act = new TimerAction();
			doBasicSetup(act, args);
			return act;
		}
		
		return null;
	}
	
	public void doBasicSetup(BasicAction a, String...args){
		a.setName(args[1]);
		a.setTriggerTargetName(args[2]);
	}
}
