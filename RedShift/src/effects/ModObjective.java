package effects;

import core.GameplayState;

public class ModObjective extends Effect {
	String targetName;
	boolean newState, newCompletion;
	@Override
	public void affect(GameplayState c) {
		// TODO Same as the other mod methods, but for objectives
		//IMPLEMENT ME!
	}
	public ModObjective(String targetName, boolean newState,
			boolean newCompletion) {
		this.targetName = targetName;
		this.newState = newState;
		this.newCompletion = newCompletion;
	}
	@Override
	public String toString() {
		return "EFFECT: Mod Objective: targetName: ''" + targetName +
				"'' newState: " + Boolean.toString(newState) +
				" newCompletion: " + newCompletion;
	}

}
