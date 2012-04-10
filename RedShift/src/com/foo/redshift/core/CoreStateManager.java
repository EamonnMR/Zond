package com.foo.redshift.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foo.redshift.ents.EntityFactory;

public class CoreStateManager extends StateBasedGame {

    // private static Logger logger = Logger.getLogger(CoreStateManager.class);

    // vars
//    private static int LOADERSTATE = 1;
//    private static int GAMEPLAYSTATE = 2;
    private static int CLIENTLOADSTATE = 3;
    private static int CLIENTPLAYSTATE = 4;

    private GameDatabase gDB;
    private PlayerClient player;
    private EntityFactory entFac;

    // const
    public CoreStateManager() {
	super("Project RedShift");
	gDB = new GameDatabase();
	player = new PlayerClient(1);
	entFac = new EntityFactory(gDB);
//	this.addState(new GamePlayState(GAMEPLAYSTATE));
//	this.addState(new ClientResourceLoader(LOADERSTATE, gDB));
	this.addState(new ClientGameplayState(CLIENTPLAYSTATE, entFac, player));
	this.addState(new ClientLoaderState(CLIENTLOADSTATE, gDB));
	this.enterState(CLIENTLOADSTATE);
    }

    // methods
    @Override
    public void initStatesList(GameContainer arg0) throws SlickException {
	// this.getState(GAMEPLAYSTATE).init(arg0, this);
    }

    public static void main(String[] args) throws SlickException {
	AppGameContainer app = new AppGameContainer(new CoreStateManager());
	app.setMouseGrabbed(false);
	app.setDisplayMode(1024, 768, false);
	app.start();
    }
}
