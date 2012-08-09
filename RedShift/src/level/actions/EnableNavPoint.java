package level.actions;

import level.NavPoint;
import core.ClientGameplayState;

public class EnableNavPoint extends BasicAction{
	private NavPoint point;
	
	public EnableNavPoint(String name,NavPoint p){
		setName(name);
		point = p;
	}
	
	@Override
	public void update(int delta, ClientGameplayState cgs){
		point.setActive(true);
		this.setUpdate(false);
		this.setDone(true);
	}
}
