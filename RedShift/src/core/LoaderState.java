package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import level.TriggerFactory;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;
import ents.EntityFactory;

public class LoaderState extends BasicGameState {

	//member variables
	private GameDatabase gdb;
	private EntityFactory entFac;
	private TriggerFactory trigFac;
	private int id;
	public PlayerClient player;			//PlayerClient for the whole game
	public LevelBuilder lvbr;
	
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
		trigFac.setEntFac(entFac);
		
		//try loading levels here
		try {
			gdb.populateLevels(trigFac);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		createTestClientData(player);
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
	
	private void createTestClientData(PlayerClient client) {
		//build some engines
		HashMap<String, BasicEngine> testEngines = new HashMap<String, BasicEngine>();
		BasicEngine small =entFac.buildEngine("smallEngine");
		testEngines.put(small.getName(), small);
		BasicEngine med =entFac.buildEngine("medEngine");
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
