package com.foo.redshift.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

public class ClientLoaderState extends BasicGameState {
    // vars
    private int id;
    // XXX use member variables.
    // private Image merc, vost, engine1, gun1, level1, thrust1, shot1;
    // private PlayerClient client;
    private GameDatabase gdb;

    // const
    public ClientLoaderState(int i, GameDatabase GDB) {
	id = i;
	gdb = GDB;
    }

    // methods

    @Override
    public void init(GameContainer arg0, StateBasedGame arg1)
	    throws SlickException {
	gdb.loadImages();
	gdb.populateArmIMG();
	gdb.populateEngIMG();
	gdb.populateGFXIMG();
	gdb.populateGunIMG();
	gdb.populateLvlIMG();
	gdb.populateShipIMG();
	gdb.populateShotIMG();
	gdb.populateObjIMG();
    }

    @Override
    public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
	    throws SlickException {

    }

    @Override
    public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
	    throws SlickException {

	arg1.enterState(4, null, new FadeInTransition(Color.black));

    }

    @Override
    public int getID() {
	return id;
    }
}
