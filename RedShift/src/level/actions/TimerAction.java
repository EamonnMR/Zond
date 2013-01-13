package level.actions;

import org.newdawn.slick.Graphics;

import core.GameDatabase;
import core.GameplayState;

public class TimerAction extends BasicAction {
	
	private int timer, x, y;
	private int lifetime;
	private boolean drawMe;
	private GameDatabase gdb;
	
	public TimerAction(){}
	
	@Override
	public void ini(GameplayState cgs){
		this.setIni(false);
		this.setUpdate(true);
		gdb = cgs.getGameDataBase();
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
		if(drawMe){
			gdb.getFont("green").drawString(x, y, String.valueOf(timer));
		}
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	public boolean isDrawMe() {
		return drawMe;
	}

	public void setDrawMe(boolean drawMe) {
		this.drawMe = drawMe;
	}
	
	
}
