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

	private int id, reason;
	private GameDatabase gdb;
	private String pilotDead, cause, action;
	
	public GameOverState(int id, GameDatabase g){
		this.id = id;
		gdb = g;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		if(reason==0){
			pilotDead = "[Status] Unknown.";
			cause="[Cause] Ship flew beyond recovery range.";
		}else if(reason==1){
			pilotDead = "[Status] Pilot Deceased.";
			cause="[Cause] Killed in Action.";
		}else if(reason==2){
			pilotDead = "[Status] Pilot Deceased.";
			cause="[Cause] Ship collided with object.";
		}
		action = "[Action] ==Press ESC==";
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		int x = 512 - (pilotDead.length()*12/2);
		gdb.getFont("green").drawString(x, 358.5f, pilotDead);
		gdb.getFont("green").drawString(x, 375.5f, cause);
		gdb.getFont("green").drawString(x, 409.5f, action);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input ip = arg0.getInput();
		if(ip.isKeyDown(Input.KEY_ESCAPE)){
			ip.clearMousePressedRecord();
			arg1.enterState(CoreStateManager.HANGARBAYSTATE);
		}
	}

	@Override
	public int getID() {
		return this.id;
	}

	public void setReason(int i){
		reason = i;
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame stbg){
		this.inputEnded();
	}
}
