package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import core.CoreStateManager;
import core.GameDatabase;

public class TitlesState extends BasicGameState {

	private Image tools, montBCK;
	private int id,counter = 0;
	private boolean ini;
	private GameDatabase gdb;
	
	public TitlesState(int id){
		this.id = id;
		ini=true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		if(!ini){
			montBCK.drawCentered(512, 384);
			tools.drawCentered(512, 384);
		}
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(ini){
			tools = gdb.getIMG("tools");
			montBCK = gdb.getIMG("montrBKC");
			ini=false;
		}
		counter++;
		Input i = arg0.getInput();
		if(i.isKeyPressed(Input.KEY_SPACE)){
			arg1.enterState(CoreStateManager.PRESENTS2);
		}
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(CoreStateManager.PRESENTS2);
		}
		if(counter==5000){
			arg1.enterState(CoreStateManager.PRESENTS2);
		}
		
	}
	
	public void customInit(GameDatabase gDB){
		this.gdb = gDB;
	}

	@Override
	public int getID() {
		return id;
	}

}
