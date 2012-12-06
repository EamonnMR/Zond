package cond;
import core.GameplayState;

public abstract class Condition {
	String target;
	
	public abstract boolean updateMe();
	
	public void activated(GameplayState c){
		c.tripTrigger(target);
	}
	public void update(GameplayState c, int delta){
		if (check(c, delta)){
			activated(c);
		}
	}
	
	public abstract boolean check(GameplayState c, int delta);
}
