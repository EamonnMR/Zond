package level.triggers;

import level.TriggerTypes;
import core.ClientGameplayState;

public class DeathTrigger extends BasicTrigger{
	
	public DeathTrigger(TriggerTypes trig, String name) {
		super(trig);
		this.setName(name);
	}
	
	@Override 
	public void go(ClientGameplayState cgs){
		if(cgs.getLevel().getTrigger(getTargetName())!=null){
			cgs.getLevel().getTrigger(getTargetName()).trigger(true);
		}
	}
}
