package ui.menustates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.CoreStateManager;
import core.GameDatabase;

public class TitleStates2 extends BasicGameState{

	private int id, counter=0;
	private Image montBKC, presents;
	private GameDatabase gdb;
	private boolean ini;
	
	public TitleStates2(int id){
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
			montBKC.drawCentered(512, 384);
			presents.drawCentered(512, 384);
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(ini){
			presents = gdb.getIMG("mta");
			montBKC = gdb.getIMG("montrBKC");
			ini=false;
		}
		counter++;
		Input i = arg0.getInput();
		if(i.isKeyPressed(Input.KEY_SPACE)){
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}
		if(counter==5000){
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void customInit(GameDatabase gDB){
		this.gdb = gDB;
	}

	

}
