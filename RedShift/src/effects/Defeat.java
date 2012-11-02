package effects;

import core.ClientGameplayState;

public class Defeat extends Effect {

	@Override
	public void affect(ClientGameplayState c) {
		c.setWinLose(-1);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "EFFECT: Defeat";
	}

	
}
