package ui.menustates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ents.OptionsEnt;

public class OptionMenuState extends BasicGameState{

	private int id, next_state;
	private OptionsEnt options;
	private String title, sndOnBTN_str, sndOffBTN_str, musOnBTN_str, musOffBTN_str;
	private Rectangle sndOnBTN_rec, sndOffBTN_rec, musOnBTN_rec, musOffBTN_rec, mouse_rec;
	private Rectangle musVol_rec, fxVol_rec, voiceVol_rec, totVol_rec;
	private Rectangle musVolSdr_rec, fxVolSdr_rec, voiceVolSdr_rec,totVolSdr_rec;
	private Rectangle fullScn_rec;
	
	public OptionMenuState(int i, OptionsEnt ops){
		id = i;
		options = ops;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		title = "Options";
		sndOnBTN_str = "";
		sndOffBTN_str = "";
		musOnBTN_str = "";
		musOffBTN_str = "";
		
		mouse_rec = new Rectangle(0,0,10,10);
		sndOnBTN_rec = new Rectangle(0,100,100,100);
		sndOffBTN_rec = new Rectangle(0,250,100,100);
		musOnBTN_rec = new Rectangle(0,400,100,100);
		musOffBTN_rec = new Rectangle(0,550,100,010);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.draw(mouse_rec);
		
		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 0.0f, 0.0f);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 0.0f, 20.0f);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		Input in = arg0.getInput();
		mouse_rec.setX(in.getMouseX());
		mouse_rec.setX(in.getMouseY());
		
		updateCollisions(delta, mouse_rec, arg0, in);
		
		if(options.getFullscreen()){
			arg0.setFullscreen(options.getFullscreen());
		}else{
			arg0.setFullscreen(options.getFullscreen());
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void updateCollisions(int delta, Rectangle mouse, GameContainer gc, Input in){
		if (in.isMousePressed(0)) {
			if (sndOnBTN_rec.intersects(mouse_rec)) {
				options.setSndOn_bool(true);
			}
			if (sndOffBTN_rec.intersects(mouse_rec)) {
				options.setSndOn_bool(false);
			}
			
			if (musOnBTN_rec.intersects(mouse_rec)) {
				options.setMusOn_bool(true);
			}
			if (musOffBTN_rec.intersects(mouse_rec)) {
				options.setMusOn_bool(false);
			}
			
			if(fullScn_rec.intersects(mouse_rec)){
				if(options.getFullscreen()==true){
					options.setFullscreen(false);
				}else if(options.getFullscreen()==false){
					options.setFullscreen(true);
				}
			}
		}
		
		if(in.isMouseButtonDown(0)){
			
		}
	}

}
