package cond;

import core.ClientGameplayState;

public class Timer extends Condition {
	private int count;
	private int max;
	
	@Override
	public boolean updateMe() {
		return true;
	}

	@Override
	public boolean check(ClientGameplayState c, int delta) {
		count += delta;
		if(count >= max){
			return true;
		}
		return false;
	}

}
