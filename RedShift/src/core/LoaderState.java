package core;

import java.io.FileNotFoundException;
import java.io.IOException;

import level.TriggerFactory;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ents.EntityFactory;

public class LoaderState extends BasicGameState {

	//member variables
	private GameDatabase gdb;
	private EntityFactory entFac;
	private TriggerFactory trigFac;
	private int id;
	public PlayerClient player;			//PlayerClient for the whole game
//	public LevelBuilder lvbr;
	
	//constructor
	public LoaderState(int i){
		id = i;
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		//TODO: do loading screen setup here
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) {
		try {
			gdb.iniGDB();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		entFac.ini(gdb);
		
		//try loading levels here
		try {
			gdb.populateLevels(trigFac);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//createTestClientData(player);
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

		arg1.enterState(CoreStateManager.MAINMENUSTATE);
	}
	
	public void customInit(GameDatabase g, EntityFactory e, TriggerFactory t, PlayerClient p){
		this.gdb = g;
		this.entFac = e;
		this.player = p;
		this.trigFac = t;
	}
}
