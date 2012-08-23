package effects;

import core.ClientGameplayState;
import ents.ShipDesc;

public class Spawn extends Effect {
	ents.ShipDesc s;
	public Spawn(ShipDesc s) {
		super();
		this.s = s;
	}

	@Override
	public void affect(ClientGameplayState c) {
		// TODO Make it spawn a ship
	}
}
