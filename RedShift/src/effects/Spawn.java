package effects;

import core.GameplayState;
import ents.ShipDesc;

public class Spawn extends Effect {
	ents.ShipDesc s;
	public Spawn(ShipDesc s) {
		super();
		this.s = s;
	}

	@Override
	public void affect(GameplayState c) {
		c.spawnShip(s);
	}

	@Override
	public String toString() {
		return "EFFECT: Spawn:\n\t" + s.toString();
	}
	
}
