package core;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ents.EntityFactory;

public class ClientLoaderState extends BasicGameState {

	//member variables
	GameDatabase gdb;
	EntityFactory entFac;
	private int id;
	//constructor
	public ClientLoaderState(int i, GameDatabase gDB, EntityFactory ef){
		this.gdb = gDB;
		this.entFac = ef;
		id = i;
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		//TODO: do loading screen setup here
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) {
		try {
			gdb.iniGDB();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entFac.ini(gdb);		
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		arg1.enterState(4);
	}

}
