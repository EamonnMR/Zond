package effects;

import core.ClientGameplayState;

public class ModTrig extends Effect {
	String targetTrigger;
	boolean newState;
	@Override
	public void affect(ClientGameplayState c) {
		// TODO Make it set the state of a trigger
	}
	public ModTrig(String targetTrigger, boolean newState) {
		this.targetTrigger = targetTrigger;
		this.newState = newState;
	}

}
