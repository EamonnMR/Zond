package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ents.EntityFactory;

/**
 * core controller of the whole program, state controller as well
 * @author proohr
 *	@version 1.0
 */
public class CoreStateManager extends StateBasedGame {

	//vars
	public static int LOADERSTATE = 1;
	public static int GAMEPLAYSTATE = 2;
	public static int CLIENTLOADSTATE = 3;
	public static int CLIENTPLAYSTATE = 4;
	
	//optionals - these are defined here so that they can be modified before gameplay runtime,
		//perhaps in the future, any of these can be modular to install new content
	public GameDatabase gDB;			//GameDataBase instance for whole game
	public PlayerClient player;			//PlayerClient for the whole game
	public EntityFactory entFac;		//Entity Factory for the whole game
	
	//constructor
	public CoreStateManager() {
		super("Project RedShift");
		gDB = new GameDatabase();
		player = new PlayerClient(1);
		entFac = new EntityFactory(gDB);
		this.addState(new ClientGameplayState(CLIENTPLAYSTATE, entFac, player));
		this.addState(new ClientLoaderState(CLIENTLOADSTATE, gDB));
		this.enterState(CLIENTLOADSTATE);
	}

	//methods
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
//		this.getState(GAMEPLAYSTATE).init(arg0, this);
	}

    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new CoreStateManager());
         app.setMouseGrabbed(false);
         app.setDisplayMode(1024, 768, false);
         app.start();
    }
}
