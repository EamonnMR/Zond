package level.triggers;

import level.NavPoint;
import level.TriggerTypes;
import core.GameplayState;


public class ToggleNavPoint extends BasicTrigger {

//	private NavPoint point;
	private String navPointer;
	private boolean navState;
	
	public ToggleNavPoint(TriggerTypes trig, NavPoint p, boolean st) {
		super(trig);
//		point = p;
		navState = st;
	}

	public ToggleNavPoint(){}
	
//	public void setNavPoint(NavPoint p){
//		point = p;
//	}
	
	public void setNavState(boolean t){
		navState = t;
	}
	
	public boolean getNavState(){
		return navState;
	}
	
	public void setNavPointer(String s){
		navPointer = s;
	}
	
	public String getNavPointer(){
		return navPointer;
	}
	
	@Override
	public void go(GameplayState cgs){
		cgs.getLevel().getNavMap().get(navPointer).setActive(navState);
	}
}
