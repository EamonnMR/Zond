package effects;

import core.ClientGameplayState;

public class Multi extends Effect {
	Effect[] effects;
	@Override
	public void affect(ClientGameplayState c) {
		for(Effect fx : effects){
			fx.affect(c);
		}
	}
	public Multi(Effect[] effects) {
		this.effects = effects;
	}
}
