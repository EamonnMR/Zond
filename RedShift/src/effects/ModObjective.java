package effects;

import core.ClientGameplayState;

public class ModObjective extends Effect {
	String targetName;
	boolean newState, newCompletion;
	@Override
	public void affect(ClientGameplayState c) {
		// TODO Same as the other mod methods, but for objectives
		//IMPLEMENT ME!
	}
	public ModObjective(String targetName, boolean newState,
			boolean newCompletion) {
		this.targetName = targetName;
		this.newState = newState;
		this.newCompletion = newCompletion;
	}

}
