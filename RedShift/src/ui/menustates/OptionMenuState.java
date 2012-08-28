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
	private Rectangle mouse_rec;
	private String title;

	
	public OptionMenuState(int i, OptionsEnt ops){
		id = i;
		options = ops;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		title = "Options";

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

}
