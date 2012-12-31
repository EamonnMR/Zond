package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * @author proohr
 *
 */
public class GameSuccessState extends BasicGameState {

	private int  id;
	private SpriteSheetFont greenF;
	private GameDatabase gdb;
	private boolean ini;
	
	public GameSuccessState(int i){
		id = i;
		ini = true;
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		greenF.drawString(512-288, 358.5f, "[Mission Status] Success");
		greenF.drawString(512-288, 378.5f, "-Press Enter or Escape to continue-");
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		if(ini){
			loadResources();
			ini=false;
		}
		Input i = arg0.getInput();
		if(i.isKeyPressed(Input.KEY_ENTER)){
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}else if(i.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}
	}
	
	public void loadResources(){
		greenF = gdb.getFont("green");
	}
	

	@Override
	public int getID() {
		return id;
	}
	
	public void customInit(GameDatabase g){
		gdb = g;
	}
}
