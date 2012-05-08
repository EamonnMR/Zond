package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ClientResourceLoader extends BasicGameState {
	//vars
	private int id;
	private GameDatabase temp;
	
	//const
	public ClientResourceLoader(int i, GameDatabase gDB){
		id = i;
		temp = gDB;
	}
	//methods

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void iniImages() throws SlickException{
		temp.loadImages();
	}
	
	public GameDatabase getDB(){
		return temp;
	}
}