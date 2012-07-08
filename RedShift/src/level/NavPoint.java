package level;

/**
 * simple point for the player to be guided by
 * @author proohr
 * Version 1.0
 */
public class NavPoint {
	
	private float x;
	private float y;
	private String name;		//
	private boolean active;		//the ability to turn point on and off

	public NavPoint(float x, float y, String n, boolean act ){
		this.x=x;
		this.y=y;
		this.name=n;	//make sure the names are unique! kinda obvious but just to be sure >.>
		this.active=act;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
