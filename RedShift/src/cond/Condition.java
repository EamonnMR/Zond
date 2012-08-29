package cond;
import core.ClientGameplayState;

public abstract class Condition {
	String target;
	
	public abstract boolean updateMe();
	
	public void activated(ClientGameplayState c){
		c.tripTrigger(target);
	}
	public void update(ClientGameplayState c, int delta){
		if (check(c, delta)){
			activated(c);
		}
	}
	
	public abstract boolean check(ClientGameplayState c, int delta);
}
