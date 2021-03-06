package level.actions;


import org.newdawn.slick.Graphics;

import core.GameplayState;

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
	public void ini(GameplayState cgs){
		this.setIni(false);
		this.setUpdate(true);
	}
	
	@Override 
	public void update(int delta, GameplayState cgs){
		timer += delta;
		if(timer >= lifetime){
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

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}
