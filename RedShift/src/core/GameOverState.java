package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	private Image backdrop;
	private boolean ini;
	
	public GameOverState(int id){
		this.id = id;
		ini=false;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		int x = 512 - (pilotDead.length()*12/2);
		arg2.drawImage(backdrop, 0, 0);
		gdb.getFont("green").drawString(x, 338.5f, pilotDead);
		gdb.getFont("green").drawString(x, 355.5f, cause);
		gdb.getFont("green").drawString(x, 389.5f, action);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(!ini){
			backdrop = gdb.getIMG("montrBKC");
			ini=true;
		}
		
		Input ip = arg0.getInput();
		
		if(reason==0){
			pilotDead = "[Status] Unknown.";
			cause="[Cause] Ship flew beyond recovery range.";
		}else if(reason==1){
			pilotDead = "[Status] Pilot Deceased.";
			cause="[Cause] Killed in Action.";
		}else if(reason==2){
			pilotDead = "[Status] Pilot Deceased.";
			cause="[Cause] Ship collided with object.";
		}else if(reason==3){
			pilotDead = "[Status] Pilot Grounded.";
			cause="[Cause] Failure to fulfill mission parameters";
		}
		action = "[Action] ==Press ESC==";
		
		
		if(ip.isKeyDown(Input.KEY_ESCAPE)){
			ip.clearKeyPressedRecord();
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}
	}
	
	public void customInit(GameDatabase g){
		gdb = g;
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
