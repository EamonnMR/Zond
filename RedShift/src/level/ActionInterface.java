package level;

/**
 * this interface provides entities and classes with a means to
 * communicate and be called by triggers and the level class.
 * @author proohr
 * @version 1.0
 */
public interface ActionInterface {

	public void doAction();
	
	public boolean isActive();
	
	public void update(int delta);
	
	public void setName(String name);
	public String getName();
	
}
