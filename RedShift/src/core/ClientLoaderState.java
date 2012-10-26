package core;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.hud.HudDataModel;
import ui.menustates.HangarBayState;
import ui.menustates.MainMenuState;
import ui.menustates.ModHudMenuState;
import ui.menustates.OptionMenuState;

import ents.EntityFactory;

public class ClientLoaderState extends BasicGameState {

	//member variables
	GameDatabase gdb;
	EntityFactory entFac;
	
	public PlayerClient player;			//PlayerClient for the whole game
	public LevelBuilder lvbr;
	private HudDataModel hdm;
	
	private int id;
	//constructor
	public ClientLoaderState(int i, GameDatabase gDB, EntityFactory ef){
		this.gdb = gDB;
		this.entFac = ef;
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
		player = new PlayerClient(1);
		lvbr = new LevelBuilder();
		hdm = new HudDataModel();
		
		createGreenLetters(gdb.getIMG("grnAlphNm"));
		createGrayLetters(gdb.getIMG("graAlphNm"));
		
		arg1.addState(new ClientGameplayState(0, player, gdb, entFac, lvbr,hdm));
		arg1.addState(new ClientGameplayState(1, player, gdb, entFac, lvbr, hdm));
		arg1.addState(new GameOverState(-1));
		arg1.addState(new GameSuccessState(2));
		arg1.addState(new MainMenuState(3, gdb));
		arg1.addState(new OptionMenuState(4, player.getOptions(), gdb));
		arg1.addState(new HangarBayState(5, gdb, player));
		arg1.addState(new ModHudMenuState(6, hdm, gdb));
		
		try {
			arg1.getState(3).init(arg0, arg1);
			arg1.getState(4).init(arg0, arg1);
			arg1.getState(5).init(arg0, arg1);
			arg1.getState(6).init(arg0, arg1);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void leave(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

		arg1.enterState(3);
	}
	
	private void createGreenLetters(Image img) {

	}

	private void createGrayLetters(Image img) {
		
	}
}
