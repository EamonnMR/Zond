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
		
		arg1.addState(new ClientGameplayState(0, player, gdb, entFac, lvbr,hdm));
		arg1.addState(new ClientGameplayState(1, player, gdb, entFac, lvbr, hdm));
		arg1.addState(new GameOverState(-1));
		arg1.addState(new GameSuccessState(2));
		arg1.addState(new MainMenuState(3, gdb));
		arg1.addState(new OptionMenuState(4, player.getOptions(), gdb));
		arg1.addState(new HangarBayState(5, gdb, player, entFac));
		arg1.addState(new ModHudMenuState(6, hdm, gdb));
		
		createTestClientData(player);
		
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
		player.setClientGuns(testGuns);
		
		//get some ships
		HashMap<String, BasicShip> testShips = new HashMap<String, BasicShip>();
		BasicShip s1 = entFac.buildShip("mercury", "20mm", "smallEngine");
		testShips.put(s1.getName(), s1);
		BasicShip s2 = entFac.buildShip("gemini", "20mm", "smallEngine");
		testShips.put(s2.getName(), s2);
		BasicShip s3 = entFac.buildShip("lunar", "20mm", "smallEngine");
		testShips.put(s3.getName(), s3);
		player.setClientShips(testShips);
	}
}
