package level.triggers;

import core.GameplayState;

public class CompleteObjective extends BasicTrigger{

	private String obj;
	
	public CompleteObjective() {}
	
	public void setObectiveTarget(String s){
		this.obj = s;
	}
	
	public String getObjectiveTarget(){
		return obj;
	}
	
	@Override
	public void go(GameplayState gs){
		gs.getLevel().getObjective(obj).setComplete(true);
		gs.getLevel().getObjective(obj).setActive(false);
	}

}
