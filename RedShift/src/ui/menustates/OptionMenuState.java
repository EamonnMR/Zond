package ui.menustates;

import org.newdawn.slick.Color;
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
	private String title, sndOnBTN_str, sndOffBTN_str, musOnBTN_str, musOffBTN_str, fullScn_str;
	private Rectangle sndOnBTN_rec, sndOffBTN_rec, musOnBTN_rec, musOffBTN_rec, mouse_rec;
	private Rectangle musVol_rec, fxVol_rec, voiceVol_rec, totVol_rec;
	private Rectangle musVolSdr_rec, fxVolSdr_rec, voiceVolSdr_rec,totVolSdr_rec;
	private Rectangle fullScn_rec, quitBtn_rec;
	
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
		fullScn_str = "Fullscreen";
		
		mouse_rec = new Rectangle(0,0,5,5);
		sndOnBTN_rec = new Rectangle(100,100,200,50);
		sndOffBTN_rec = new Rectangle(100,200,200,50);
		musOnBTN_rec = new Rectangle(100,300,200,50);
		musOffBTN_rec = new Rectangle(100,400,200,50);
		fullScn_rec = new Rectangle(100, 500, 200,50);
		quitBtn_rec = new Rectangle(100, 600, 200,50);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.setColor(Color.yellow);
		gfx.draw(mouse_rec);
		
		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 105.0f, 10.0f);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 175.0f, 10.0f);
		
		gfx.setColor(Color.gray);
		gfx.draw(fullScn_rec);
		
		gfx.setColor(Color.red);
		gfx.drawString(fullScn_str, fullScn_rec.getCenterX(), fullScn_rec.getCenterY()-100);
		gfx.drawString(String.valueOf(options.getFullscreen()), fullScn_rec.getCenterX(), fullScn_rec.getCenterY()-50);
		gfx.draw(quitBtn_rec);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		Input in = arg0.getInput();
		mouse_rec.setX(in.getMouseX());
		mouse_rec.setY(in.getMouseY());
		
		updateCollisions(delta, mouse_rec, arg0, in, arg1);
		
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
	
	public void updateCollisions(int delta, Rectangle mouse, GameContainer gc, Input in, StateBasedGame stg){
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
			if(quitBtn_rec.intersects(mouse_rec)){
				stg.enterState(3);
			}
		}
		
		if(in.isMouseButtonDown(0)){
			
		}
	}

}
