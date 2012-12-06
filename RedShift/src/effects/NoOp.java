package effects;
/* Just in case you ever needed a no-operation effect.*/
import core.GameplayState;

public class NoOp extends Effect {

	@Override
	public void affect(GameplayState c) {
	}
	@Override
	public String toString() {
		return "EFFECT: No-Op";
	}

	
}
