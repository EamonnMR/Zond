package cond;

import core.ClientGameplayState;

public class Counter extends Condition {

	int count = 0;
	int total;
	
	public Counter(int total){
		this.total = total;
	}
	
	public String toString(){
		return "COND: Counter total =" + Integer.toString(total);
	}
	
	public void incremet(ClientGameplayState c){
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
	public boolean check(ClientGameplayState c, int delta) {
		// Since UpdateMe isn't there, check is unused
		return false;
	}

}
