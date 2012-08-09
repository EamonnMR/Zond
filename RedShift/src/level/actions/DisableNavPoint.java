package level.actions;

import core.ClientGameplayState;
import level.NavPoint;

public class DisableNavPoint extends BasicAction {

	private NavPoint point;
	
	public DisableNavPoint(String name, NavPoint p){
		setName(name);
		point = p;
	}
	
	@Override
	public void update(int delta, ClientGameplayState cgs){
		point.setActive(false);
		this.setUpdate(false);
		this.setDone(true);
	}
}
