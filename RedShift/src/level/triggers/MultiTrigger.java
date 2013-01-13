package level.triggers;

import level.TriggerTypes;

/**
 * test class to try and trigger multiple actions
 * @author Roohr
 *
 */
public class MultiTrigger extends BasicTrigger{

	private String[] targetNames;
	public MultiTrigger(TriggerTypes trig, String[] targets) {
		super(trig);
		this.targetNames = targets;
	}
	
	public MultiTrigger(){
		
	}
	
	public void addTargetName(String s){
//		use cat method here
	}
	
	public void setTargetNames(String[] s){
		targetNames = s;
	}
	public String[] getTargetNames(){
		return targetNames;
	}

}
