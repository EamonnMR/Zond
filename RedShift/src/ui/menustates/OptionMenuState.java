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
	private Rectangle mouse_rec, sfxVol_rec, musVol_rec, voiVol_rec, part_rec, fullscrn_rec;
	private Rectangle sfxVol_sld, musVol_sld, voiVol_sld;
	private String title, sfxVol_str, musVol_str, voiVol_str, part_str, fullscrn_str;

	
	public OptionMenuState(int i, OptionsEnt ops){
		id = i;
		options = ops;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		//strings
		title = "Options";
		sfxVol_str = "SFX";
		musVol_str = "Music";
		voiVol_str = "Voice";
		
		//rectangles
		sfxVol_rec = new Rectangle(0,0,0,0);
		musVol_rec = new Rectangle(0,0,0,0);
		voiVol_rec = new Rectangle(0,0,0,0);
		part_rec = new Rectangle(0,0,0,0);
		fullscrn_rec = new Rectangle(0,0,0,0);
		
		//sliders
		sfxVol_sld = new Rectangle(0,0,0,0);
		musVol_sld = new Rectangle(0,0,0,0);
		voiVol_sld = new Rectangle(0,0,0,0);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		Input in = arg0.getInput();
		mouse_rec.setX(in.getMouseX());
		mouse_rec.setY(in.getMouseY());
		
		updateCollisions(delta, mouse_rec, arg0, in, arg1);
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void updateCollisions(int delta, Rectangle mouse, GameContainer gc, Input in, StateBasedGame stg){

	}
	
	public void drawOn(Graphics gfx, double x, double y){
		
	}
	
	public void drawOff(Graphics gfx, double x, double y){
		
	}

}
