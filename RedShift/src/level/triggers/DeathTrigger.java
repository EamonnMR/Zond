package level.triggers;

import level.TriggerTypes;
import core.GameplayState;

/**
 * this may seem redundant but its an organizational thing. Basically GameplayState
 * will look for one of these when an ent dies, from here one can chain more triggers.
 * @author proohr
 *
 */
public class DeathTrigger extends BasicTrigger{
	
	public DeathTrigger(TriggerTypes trig, String name) {
		super(trig);
		this.setName(name);
	}
	
	public DeathTrigger(){}
	
	@Override 
	public void go(GameplayState cgs){
		if(cgs.getLevel().getTrigger(getTargetName())!=null){
			cgs.getLevel().getTrigger(getTargetName()).trigger(true);
		}
	}
}
