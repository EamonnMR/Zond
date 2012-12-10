package level.triggers;

import core.GameplayState;

public class ToggleObjective extends BasicTrigger{

	private String target;
	private boolean state;
	
	public ToggleObjective(){}

	public String getTarget() {
		return target;
	}
	
	@Override
	public void go(GameplayState gs){
		gs.getLevel().getObjective(target).setActive(state);
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	
}
