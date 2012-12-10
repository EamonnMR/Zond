package level.triggers;

import core.GameplayState;

public class TheWinTrigger extends BasicTrigger{

	private int winORlose;
	
	public TheWinTrigger(){}
	
	@Override
	public void go(GameplayState cgs){
		cgs.setWinLose(winORlose);
	}
	
	public void setWinLose(int i){
		this.winORlose = i;
	}
}
