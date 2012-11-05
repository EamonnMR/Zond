package cond;

import core.GameplayState;

public class Counter extends Condition {

	int count = 0;
	int total;
	
	public Counter(int total, String target){
		this.target = target;
		this.total = total;
	}
	
	public String toString(){
		return "COND: Counter total =" + Integer.toString(total);
	}
	
	public void incremet(GameplayState c){
		count ++;
		if (count >= total){
			activated(c);
		}
	}
	
	@Override
	public boolean updateMe() {
		return false;
	}

	@Override
	public boolean check(GameplayState c, int delta) {
		// Since UpdateMe isn't there, check is unused
		return false;
	}

}
