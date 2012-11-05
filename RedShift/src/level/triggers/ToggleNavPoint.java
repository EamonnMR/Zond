package level.triggers;

import level.NavPoint;
import level.TriggerTypes;
import core.GameplayState;


public class ToggleNavPoint extends BasicTrigger {

	private NavPoint point;
	boolean state;
	public ToggleNavPoint(TriggerTypes trig, NavPoint p, boolean st) {
		super(trig);
		point = p;
		state = st;
	}

	@Override
	public void go(GameplayState cgs){
		point.setActive(state);
	}
}
