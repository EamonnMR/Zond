package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.hud.HudDataModel;
import ui.menustates.HangarBayState;
import ui.menustates.MainMenuState;
import ui.menustates.ModHudMenuState;
import ui.menustates.OptionMenuState;
import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;
import ents.EntityFactory;

public class LoaderState extends BasicGameState {

	//member variables
	GameDatabase gdb;
	EntityFactory entFac;
	
	public PlayerClient player;			//PlayerClient for the whole game
	public LevelBuilder lvbr;
	private HudDataModel hdm;
	
	private int id;
	//constructor
	public LoaderState(int i, GameDatabase gDB, EntityFactory ef){
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
		
		arg1.addState(new GameplayState(CoreStateManager.PLAYSTATE, player, gdb, entFac, lvbr, hdm));
		arg1.addState(new GameOverState(CoreStateManager.GAMEOVERSTATE, gdb));
		arg1.addState(new GameSuccessState(CoreStateManager.SUCCSTATE));
		arg1.addState(new MainMenuState(CoreStateManager.MAINMENUSTATE, gdb));
		arg1.addState(new OptionMenuState(CoreStateManager.OPTIONSMENUSTATE, player.getOptions(), gdb));
		arg1.addState(new HangarBayState(CoreStateManager.HANGARBAYSTATE, gdb, player, entFac));
		arg1.addState(new ModHudMenuState(CoreStateManager.HUDMODSTATE, hdm, gdb));
		
		createTestClientData(player);
		
		try {
			arg1.getState(CoreStateManager.GAMEOVERSTATE).init(arg0, arg1);
			arg1.getState(CoreStateManager.MAINMENUSTATE).init(arg0, arg1);
			arg1.getState(CoreStateManager.OPTIONSMENUSTATE).init(arg0, arg1);
			arg1.getState(CoreStateManager.HANGARBAYSTATE).init(arg0, arg1);
			arg1.getState(CoreStateManager.HUDMODSTATE).init(arg0, arg1);
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
	
	private void createTestClientData(PlayerClient client) {
		//build some engines
		HashMap<String, BasicEngine> testEngines = new HashMap<String, BasicEngine>();
		BasicEngine small =entFac.buildEngine("smallEngine");
		testEngines.put(small.getName(), small);
		BasicEngine med =entFac.buildEngine("mediumEngine");
		testEngines.put(med.getName(), med);
		BasicEngine large =entFac.buildEngine("largeEngine");
		testEngines.put(large.getName(), large);
		player.setClientEngines(testEngines);
		
		//get some guns
		HashMap<String, BasicGun> testGuns = new HashMap<String, BasicGun>();
		BasicGun g1 = entFac.buildGun("20mm");
		testGuns.put(g1.getName(), g1);
		BasicGun g2 = entFac.buildGun("60mm");
		testGuns.put(g2.getName(), g2);
		BasicGun g3 = entFac.buildGun("105mm");
		testGuns.put(g3.getName(), g3);
		BasicGun g4 = entFac.buildGun("plas");
		testGuns.put(g4.getName(), g4);
		BasicGun g5 = entFac.buildGun("las");
		testGuns.put(g5.getName(), g5);
		player.setClientGuns(testGuns);
		
		//get some ships
		HashMap<String, BasicShip> testShips = new HashMap<String, BasicShip>();
		BasicShip s1 = entFac.stockMercury();
		testShips.put(s1.getName(), s1);
		BasicShip s2 = entFac.stockGem();
		testShips.put(s2.getName(), s2);
		BasicShip s3 = entFac.stockLunar();
		testShips.put(s3.getName(), s3);
		BasicShip s4 = entFac.stockVoskhod();
		testShips.put(s4.getName(), s4);
		BasicShip s5 = entFac.stockVostok();
		testShips.put(s5.getName(), s5);
		BasicShip s6 = entFac.stockZond();
		testShips.put(s6.getName(), s6);
		player.setClientShips(testShips);
	}
}
