package level.triggers;

import level.BasicObjective;
import level.TriggerTypes;
import core.ClientGameplayState;

public class CompleteObjective extends BasicTrigger{

	private BasicObjective obj;
	
	public CompleteObjective(TriggerTypes trig, BasicObjective o, String name) {
		super(trig);
		this.setName(name);
		obj = o;
	}
	
	@Override
	public void go(ClientGameplayState cgs){
		obj.setComplete(true);
	}

}
