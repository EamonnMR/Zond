package level.actions;

import org.newdawn.slick.Graphics;

import core.GameplayState;

public class PlaySound extends BasicAction {

	private String sound;
	
	public PlaySound(){}
	
	@Override
	public void ini(GameplayState cgs){
		this.setIni(false);
		this.setUpdate(true);
		
	}
	
	@Override 
	public void update(int delta, GameplayState cgs){
		cgs.getGameDataBase().getSound(sound).play(0,
												cgs.getSFXVol());
		this.setUpdate(false);
	}
	
	@Override
	public void render(Graphics gfx){

	}	
	
	public void setTarget(String s){
		this.sound = s;
	}
	
	public String getTarget(){
		return sound;
	}
}
