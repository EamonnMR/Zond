package effects;

import core.ClientGameplayState;

public class ModAction extends Effect {

	String targetName;
	boolean ini, fire, done;
	
	@Override
	public void affect(ClientGameplayState c) {
		// TODO Auto-generated method stub

	}

	public ModAction(String targetName, boolean ini, boolean fire, boolean done) {
		this.targetName = targetName;
		this.ini = ini;
		this.fire = fire;
		this.done = done;
	}
}
