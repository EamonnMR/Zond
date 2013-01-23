package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	private PlayerClient p;
	private Image backdrop;
	
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
		backdrop.draw(0, 0);
		greenF.drawString(410, 338.5f, "[Status] Success!");
		greenF.drawString(410, 355.5f, "-Press Enter or Escape to continue-");
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
		if(!gdb.getSound("menuPrelude").playing()){
			gdb.getSound("menuPrelude").loop(1.0f, p.getOptions().getMusevol());
		}
	}
	
	public void loadResources(){
		greenF = gdb.getFont("green");
		backdrop = gdb.getIMG("montrBKC");
	}
	

	@Override
	public int getID() {
		return id;
	}
	
	public void customInit(GameDatabase g, PlayerClient pc){
		gdb = g;
		p = pc;
	}
}
