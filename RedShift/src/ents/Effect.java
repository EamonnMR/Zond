package ents;

import org.newdawn.slick.Animation;

import core.GameDatabase;

public class Effect extends BaseEnt{

	Animation effect;
	boolean isDone = false;
	
	public Effect(GameDatabase g, String type, float x, float y){
		effect = g.getAnimation(type).copy();
		setX(x);
		setY(y);
	}
	@Override
	public void render(int xOffset, int yOffset){
		int w,h;
		w = effect.getWidth()/2;
		h = effect.getHeight()/2;
		effect.draw((float)(xOffset+getX()-w), (float)(yOffset+getY()-h));
	}
	
	@Override
	public void update(int delta){
		effect.update(delta);
		if(effect.isStopped()){
			isDone = true;
		}
	}
	
	public boolean getIsDone(){
		return isDone;
	}
}
