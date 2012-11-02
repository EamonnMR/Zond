package effects;

import core.ClientGameplayState;

public class ModTrig extends Effect {
	String targetTrigger;
	@Override
	public void affect(ClientGameplayState c) {
		c.tripTrigger(targetTrigger);
	}
	public ModTrig(String targetTrigger) {
		this.targetTrigger = targetTrigger;
	}
	@Override
	public String toString() {
		return "EFFECT: Mod Trig: targetTrigger: ''" + targetTrigger;
	}
	
	

}
