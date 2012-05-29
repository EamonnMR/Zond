package level;

import org.newdawn.slick.GameContainer;

/**
 * outputs a message to the player at a specified location and for a certain amount of time
 * @author proohr
 * @version 1.0
 */
public class MessageAction implements ActionInterface{

	private int lifetime;
	private int timer;
	private String message;
	private String name;
	private float x,y;
	private boolean isActive;
	
	public MessageAction(String name, int life, String message, float x, float y){
		this.name = name;
		this.message = message;
		this.lifetime = life;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void doAction() {
		
	}
	@Override
	public void update(int delta) {
		timer += delta;
		if(timer <= lifetime){
			isActive = true;
		}else{
			isActive = false;
		}
		
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}
	
	public void setY(float y) {
		this.y = y;	
	}

	public float getY() {
		return y;
	}

	@Override
	public boolean isActive(){
		return isActive;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	
}
