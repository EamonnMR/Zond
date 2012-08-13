package core;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import ents.EntityFactory;

/**
 * core controller of the whole program, state controller as well
 * @author proohr
 *	@version 1.0
 */
public class CoreStateManager extends StateBasedGame {

	//vars
	public static int CLIENTLOADERSTATE = 0;
	public static int CLIENTPLAYSTATE = 1;
	public static int CLIENTGAMEOVERSTATE = -1;
	public static int CLIENTSUCCSTATE = 2;
	public Sound test;
	
	//optionals - these are defined here so that they can be modified before gameplay runtime,
	//perhaps in the future, any of these can be modular to install new content
	public GameDatabase gDB;			//GameDataBase instance for whole game
	public PlayerClient player;			//PlayerClient for the whole game
	public EntityFactory entFac;		//Entity Factory for the whole game
	public LevelBuilder lvbr;
	
	//constructor
	public CoreStateManager() {
		super("RedShift v1.0");
		player = new PlayerClient(1);
		gDB = new GameDatabase();
		entFac = new EntityFactory();
		lvbr = new LevelBuilder();
		

		this.addState(new ClientLoaderState(CLIENTLOADERSTATE, gDB, entFac));
		this.addState(new ClientGameplayState(CLIENTPLAYSTATE, player, gDB, entFac, lvbr));
		this.addState(new GameOverState(CLIENTGAMEOVERSTATE));
		this.addState(new GameSuccessState(CLIENTSUCCSTATE));
		this.enterState(CLIENTLOADERSTATE);
	}

	//methods
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.getState(CLIENTLOADERSTATE).init(arg0, this);
	}

    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new CoreStateManager());
         app.setMouseGrabbed(false);
         app.setDisplayMode(1024, 768, false);
//         app.setFullscreen(true);
         app.start();
    }
}
