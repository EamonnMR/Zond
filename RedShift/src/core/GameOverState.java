package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * when the player dies go here, and then rerack the ClientGameplayState
 * @author Roohr
 *
 */
public class GameOverState extends BasicGameState {

	private int id;
	
	public GameOverState(int id){
		this.id = id;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.drawString("You're fucking dead", 1024/2, 768/2);
		arg2.drawString("press space to try again, idiot", (1024/2), (768/2)-35);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input ip = arg0.getInput();
		if(ip.isKeyDown(Input.KEY_SPACE)){
			arg1.enterState(1);
		}
	}

	@Override
	public int getID() {
		return this.id;
	}

}
