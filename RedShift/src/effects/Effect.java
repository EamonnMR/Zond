package effects;
/*
 * One-off effects on ClientGameplayState
 * It would be cleaner to implement them against a
 * special Interface, as we did with Splicer,
 * but we can always do that later.
 */
public abstract class Effect {
	public abstract void affect(core.ClientGameplayState c);
}
