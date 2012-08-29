package effects;

import core.ClientGameplayState;

public class ModNavPoint extends Effect {

	String targetName;
	boolean newState;
	
	@Override
	public void affect(ClientGameplayState c) {
		// TODO Same as mod trig, but this time it works on a nav point.

	}

	public ModNavPoint(String targetName, boolean newState) {
		this.targetName = targetName;
		this.newState = newState;
	}

	@Override
	public String toString() {
		return "EFFECT: Mod Nav Point: targetName: ''"+ targetName +"'' newState: " +
				Boolean.toString(newState);
	}
	
}
