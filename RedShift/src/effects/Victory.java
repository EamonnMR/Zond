package effects;

import core.GameplayState;

public class Victory extends Effect {

	@Override
	public void affect(GameplayState c) {
		c.setWinLose(1);
	}

	@Override
	public String toString() {
		return "EFFECT: Victory";
	}
	
	
}