package effects;

import core.ClientGameplayState;

public class ModAction extends Effect {

	String targetName;
	boolean ini, fire, done;
	
	@Override
	public void affect(ClientGameplayState c) {
		// TODO: Make it actually do suff :P

	}

	public ModAction(String targetName, boolean ini, boolean fire, boolean done) {
		this.targetName = targetName;
		this.ini = ini;
		this.fire = fire;
		this.done = done;
	}

	@Override
	public String toString() {
		//Turn all of the fields into one giant string.
		//This is why nested procs should exist; I should be able to say:
		//local String bts(boolean b){ return Boolean.toString(b); };
		//And thereafter use bts() in place of Boolean.toString().
		//Seriously.
		return "EFFECT: Mod Action: ini: " + Boolean.toString(ini) +
				", fire: " + Boolean.toString(fire) + ", done: "+
				Boolean.toString(done);
	}
	
}
