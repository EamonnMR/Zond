package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ents.EntityFactory;

public class CoreStateManager extends StateBasedGame {

	//vars
	public static int LOADERSTATE = 1;
	public static int GAMEPLAYSTATE = 2;
	public static int CLIENTLOADSTATE = 3;
	public static int CLIENTPLAYSTATE = 4;
	
	public GameDatabase gDB;
	public PlayerClient player;
	public EntityFactory entFac;
	
	//const
	public CoreStateManager() {
		super("Project RedShift");
		// TODO Auto-generated constructor stub
		gDB = new GameDatabase();
		player = new PlayerClient(1);
		entFac = new EntityFactory(gDB);
//		this.addState(new GamePlayState(GAMEPLAYSTATE));
//		this.addState(new ClientResourceLoader(LOADERSTATE, gDB));
		this.addState(new ClientGameplayState(CLIENTPLAYSTATE, entFac, player));
		this.addState(new ClientLoaderState(CLIENTLOADSTATE, gDB));
		this.enterState(CLIENTLOADSTATE);
	}

	//methods
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
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
