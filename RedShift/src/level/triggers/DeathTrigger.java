package level.triggers;

import level.TriggerTypes;
import core.GameplayState;

public class DeathTrigger extends BasicTrigger{
	
	public DeathTrigger(TriggerTypes trig, String name) {
		super(trig);
		this.setName(name);
	}
	
	@Override 
	public void go(GameplayState cgs){
		if(cgs.getLevel().getTrigger(getTargetName())!=null){
			cgs.getLevel().getTrigger(getTargetName()).trigger(true);
		}
	}
}
