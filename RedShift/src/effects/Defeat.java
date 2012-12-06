package effects;

import core.GameplayState;

public class Defeat extends Effect {

	@Override
	public void affect(GameplayState c) {
		c.setWinLose(-1);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EFFECT: Defeat";
	}

	
}
