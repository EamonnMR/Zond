package core;


import java.io.IOException;

import level.TriggerFactory;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

import ui.hud.HudDataModel;
import ui.menustates.BriefingMenuState;
import ui.menustates.HangarBayState;
import ui.menustates.InfoState;
import ui.menustates.MainMenuState;
import ui.menustates.ModHudMenuState;
import ui.menustates.OptionMenuState;
import ui.menustates.PauseMenuState;
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
	public static int BRIEFING = 7;
	private BriefingMenuState brief;
	public static int PAUSE = 8;
	private PauseMenuState pause;
	public static int INFO = 9;
	private InfoState info;
	
	//optionals - these are defined here so that they can be modified before gameplay runtime,
	//perhaps in the future, any of these can be modular to install new content
	private GameDatabase gDB;			//GameDataBase instance for whole game
	private PlayerClient player;			//PlayerClient for the whole game
	private EntityFactory entFac;		//Entity Factory for the whole game
//	private LevelBuilder lvbr;
	private HudDataModel hdm;
	private TriggerFactory trigFac;
	
	//constructor
	public CoreStateManager() {
		super("RedShift v1.5");		
		createStates();	//queue up the list of states, add them to the game
		loadResources();//populate our data classes with necessary info
		customIniStates();	//sadly BasicGameState.init cannot be trusted as it only triggers when state is added to game
//		this.enterState(LOADERSTATE);
		this.enterState(INFO, null, new FadeInTransition(Color.black));
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
		brief = new BriefingMenuState(BRIEFING);
		pause = new PauseMenuState(PAUSE);
		info = new InfoState(INFO);
	}
	
	/**
	 * instantiates pertinent data models like the gdb and ent fac
	 * @throws IOException 
	 */
	private void loadResources(){
		gDB = new GameDatabase();
		entFac = new EntityFactory();
		player = new PlayerClient(1);
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
		
		mainMenu.customInit(gDB, entFac, player);
		this.addState(mainMenu);
		
		optionsMenu.customInit(player.getOptions(), gDB);
		this.addState(optionsMenu);
		
		hangarBay.customInit(gDB, player);
		this.addState(hangarBay);
		
		modHud.customInit(hdm, gDB);
		this.addState(modHud);
		
		gameOver.customInit(gDB);
		this.addState(gameOver);
		
		gameWin.customInit(gDB);
		this.addState(gameWin);
		
		brief.customInit(gDB);
		this.addState(brief);
		
		pause.customInit(gDB);
		this.addState(pause);
		
		gamePlay.customInit(player,gDB, entFac, hdm );
		this.addState(gamePlay);
		
		this.addState(info);
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
		this.getState(INFO).init(arg0, this);
	}

	
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new CoreStateManager());
         app.setMouseGrabbed(false);
         app.setDisplayMode(1024, 768, false);
         app.setShowFPS(true);
//         app.setTargetFrameRate(120);
//         app.setVSync(true);
         app.start();
    }
}
