package effects;

import core.ClientGameplayState;

public class Victory extends Effect {

	@Override
	public void affect(ClientGameplayState c) {
		c.setWinLose(1);
	}
}