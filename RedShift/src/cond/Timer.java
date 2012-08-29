package cond;

import core.ClientGameplayState;

public class Timer extends Condition {
	private int count;
	private int max;
	
	
	public Timer(int max){
		count = 0;
		this.max = max;
	}
	
	public String toString(){
		return "COND: Timer max = " + Integer.toString(max);
	}
	
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
