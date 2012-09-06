package effects;
/* Just in case you ever needed a no-operation effect.*/
import core.ClientGameplayState;

public class NoOp extends Effect {

	@Override
	public void affect(ClientGameplayState c) {
	}

}
