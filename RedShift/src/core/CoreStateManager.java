package core;


import java.io.IOException;

import level.TriggerFactory;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ui.hud.HudDataModel;
import ui.menustates.HangarBayState;
import ui.menustates.MainMenuState;
import ui.menustates.ModHudMenuState;
import ui.menustates.OptionMenuState;
import ents.EntityFactory;

/**
 * core controller of the whole program, state controller as well
 * @author proohr
 *	@version 1.0
 */
public class CoreStateManager extends StateBasedGame {

	//vars
	public static int LOADERSTATE = 0;
	private LoaderState load;
	public static int GAMEPLAYSTATE = 1;
	private GameplayState gamePlay;
	public static int GAMEOVERSTATE = -1;
	private GameOverState gameOver;
	public static int GAMEWINSTATE = 2;
	private GameSuccessState gameWin;
	public static int MAINMENUSTATE = 3;
	private MainMenuState mainMenu;
	public static int OPTIONSMENUSTATE = 4;
	private OptionMenuState optionsMenu;
	public static int HANGARBAYSTATE = 5;
	private HangarBayState hangarBay;
	public static int HUDMODSTATE = 6;
	private ModHudMenuState modHud;
	
	//optionals - these are defined here so that they can be modified before gameplay runtime,
	//perhaps in the future, any of these can be modular to install new content
	private GameDatabase gDB;			//GameDataBase instance for whole game
	private PlayerClient player;			//PlayerClient for the whole game
	private EntityFactory entFac;		//Entity Factory for the whole game
	private LevelBuilder lvbr;
	private HudDataModel hdm;
	private TriggerFactory trigFac;
	
	//constructor
	public CoreStateManager() {
		super("RedShift v1.5");		
		createStates();	//queue up the list of states, add them to the game
		loadResources();//populate our data classes with necessary info
		customIniStates();	//sadly BasicGameState.init cannot be trusted as it only triggers when state is added to game
		this.enterState(LOADERSTATE);
	}

	//methods
	/**
	 * creates a new instance of each state needed
	 */
	private void createStates() {
		load = new LoaderState(LOADERSTATE);
		mainMenu = new MainMenuState(MAINMENUSTATE);
		optionsMenu = new OptionMenuState(OPTIONSMENUSTATE);
		hangarBay = new HangarBayState(HANGARBAYSTATE);
		modHud = new ModHudMenuState(HUDMODSTATE);
		gameOver = new GameOverState(GAMEOVERSTATE);
		gameWin = new GameSuccessState(GAMEWINSTATE);
		gamePlay = new GameplayState(GAMEPLAYSTATE);
	}
	
	/**
	 * instantiates pertinent data models like the gdb and ent fac
	 * @throws IOException 
	 */
	private void loadResources(){
		//two very important things
		gDB = new GameDatabase();
		entFac = new EntityFactory();
		player = new PlayerClient(1);
		lvbr = new LevelBuilder();
		hdm = new HudDataModel();
		trigFac = new TriggerFactory();
	}
	
	/**
	 * StateBasedGame's initStatesList isn't truly spot on for what we need, so heres a custom ini called 
	 * before anything else. I think theres persistence between states going on in the background
	 * which makes it look like a state's ini has been called when in fact its only called one...i think.
	 * In this case, we're setting up the references because java is pass by reference :)
	 */
	private void customIniStates() {
		load.customInit(gDB, entFac, trigFac,  player);
		this.addState(load);
		
		mainMenu.customInit(gDB);
		this.addState(mainMenu);
		
		optionsMenu.customInit(player.getOptions(), gDB);
		this.addState(optionsMenu);
		
		hangarBay.customInit(gDB, player, entFac);
		this.addState(hangarBay);
		
		modHud.customInit(hdm, gDB);
		this.addState(modHud);
		
		gameOver.customInit(gDB);
		this.addState(gameOver);
		
		this.addState(gameWin);
		
		gamePlay.customInit(player,gDB, entFac, lvbr, hdm );
		this.addState(gamePlay);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.getState(MAINMENUSTATE).init(arg0, this);
		this.getState(OPTIONSMENUSTATE).init(arg0, this);
		this.getState(HANGARBAYSTATE).init(arg0, this);
		this.getState(HUDMODSTATE).init(arg0, this);
		this.getState(GAMEOVERSTATE).init(arg0, this);
		this.getState(GAMEWINSTATE).init(arg0, this);
		this.getState(GAMEPLAYSTATE).init(arg0, this);
	}

	
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new CoreStateManager());
         app.setMouseGrabbed(false);
         app.setDisplayMode(1024, 768, false);
         app.setShowFPS(true);
         app.start();
    }
}
