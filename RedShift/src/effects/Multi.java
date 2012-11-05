package effects;

import core.GameplayState;

public class Multi extends Effect {
	Effect[] effects;
	@Override
	public void affect(GameplayState c) {
		for(Effect fx : effects){
			fx.affect(c);
		}
	}
	public Multi(Effect[] effects) {
		this.effects = effects;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String toSender = "EFFECT: Multi:\n";
		for(Effect i : effects){
			if(i == null){
				toSender += "\n\tNULL";
			} else {
				toSender += "\n\t" + i.toString();
			}
		}
		return toSender;
	}
}
