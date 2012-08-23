package level.triggers;

import level.NavPoint;
import level.TriggerTypes;
import core.ClientGameplayState;


public class ToggleNavPoint extends BasicTrigger {

	private NavPoint point;
	boolean state;
	public ToggleNavPoint(TriggerTypes trig, NavPoint p, boolean st) {
		super(trig);
		point = p;
		state = st;
	}

	@Override
	public void go(ClientGameplayState cgs){
		point.setActive(state);
	}
}
