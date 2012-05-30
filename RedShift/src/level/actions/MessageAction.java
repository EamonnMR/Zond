package level.actions;

import level.BasicAction;

import org.newdawn.slick.Graphics;

/**
 * outputs a simple string to the user at a given location
 * @author proohr
 * @version 1.0
 */
public class MessageAction extends BasicAction {

	private String message;
	private float x,y;
	private int timer;
	private int lifetime;
	
	public MessageAction(String name, float x, float y, String msg, int life){
		this.setName(name);
		this.x = x;
		this.y = y;
		this.message = msg;
		this.lifetime = life;
	}
	
	@Override
	public void ini(){
		this.setUpdate(true);
	}
	
	@Override 
	public void update(int delta){
		timer += delta;
		if(timer <= lifetime){
			
		}else{
			this.setUpdate(false);
			this.setDone(true);
		}
	}
	
	@Override
	public void render(Graphics gfx){
		gfx.drawString(message, x, y);
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public String getMessage(){
		return this.message;
	}
}
