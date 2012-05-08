package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

/**
 * the loader state for gameplay, always call this before 
 * the game launches...or the gameplay state - not sure which
 * at this point
 * @author proohr
 * @version 1.0
 */
public class ClientLoaderState extends BasicGameState {
	//vars
	private int id;
	private GameDatabase gdb;	//instance of a gameDataBase
	
	//const
	public ClientLoaderState(int i, GameDatabase GDB){
		id = i;
		gdb = GDB;
	}
	//methods

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		gdb.loadImages();			//call the database to load the images
		gdb.populateImages();		//populate the database with the images
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		//TODO: loading screen and loading bar?
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		arg1.enterState(4, null, new FadeInTransition(Color.black));
		//TODO: timer for loading bar etc
	}

	@Override
	public int getID() {
		return id;
	}
}
