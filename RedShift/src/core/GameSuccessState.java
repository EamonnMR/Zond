package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author proohr
 *
 */
public class GameSuccessState extends BasicGameState {

	private int  id;
	
	public GameSuccessState(int i){
		id = i;
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.drawString("Mission Successful", 1024/2, 768/2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {	
	}

	@Override
	public int getID() {
		return id;
	}

}
