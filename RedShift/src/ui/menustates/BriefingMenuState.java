package ui.menustates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.GameDatabase;

public class BriefingMenuState extends BasicGameState {

	private int id;
	private GameDatabase gdb;
	private boolean ini;
	private SpriteSheetFont green, gray;
	
	public BriefingMenuState(int i){
		id = i;
		ini=true;
	}
	
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
		if(ini){
			loadResources();
			ini=false;
		}
	}

	private void loadResources() {

	}

	@Override
	public int getID() {
		return id;
	}

	public void customInit(GameDatabase g){
		gdb = g;
	}
}
