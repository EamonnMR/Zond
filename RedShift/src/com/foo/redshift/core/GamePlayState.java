package com.foo.redshift.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.foo.redshift.ents.BaseLevel;
import com.foo.redshift.ents.BasicEngine;
import com.foo.redshift.ents.BasicGun;
import com.foo.redshift.ents.BasicShip;

public class GamePlayState extends BasicGameState {

    // vars
    private int id;
    private float x, y;
    // private OptionsEnt ops; XXX use me
    public BasicShip player, dude;
    public BasicGun gun;
    public BasicEngine eng1;
    private BaseLevel level;
    private Circle col;
    private float rotation;

    // const
    public GamePlayState(int i) {
	id = i;
    }

    // methods
    @Override
    public void init(GameContainer arg0, StateBasedGame arg1)
	    throws SlickException {
	col = new Circle(x, y, 28, 48);

	// level = new BaseLevel("Scratch",1600,1600);
	level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));
	x = arg0.getWidth() / 2;
	y = arg0.getHeight() / 2;

	player = new BasicShip(1, new Image(
		"assets/images/ships/nasa/mercury/mercury.png"));
	gun = new BasicGun(1);
	gun.setImg(new Image("assets/images/weapons/20mm.png"));

	eng1 = new BasicEngine(1);
	eng1.setInGameImg(new Image("assets/images/engines/engine1.png"));
	eng1.setSparkleImg(new Image("assets/images/fx/engine1thrust.png"));
	eng1.setTurnrate(0.4f);

	player.setEngine(eng1);
	player.setWeapon(gun);
	player.setCollider(col);
	player.setX(x);
	player.setY(y);
	player.getWeapon().getImg().setRotation(0);

	dude = new BasicShip(2, new Image(
		"assets/images/ships/nasa/mercury/mercury.png"));
	dude.setX((arg0.getWidth() / 2) + 250);
	dude.setY((arg0.getHeight() / 2));

    }

    @Override
    public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
	    throws SlickException {
	level.render(arg2, 0, 0);
	player.render();
	dude.render();
    }

    @Override
    public void update(GameContainer arg0, StateBasedGame arg1, int delta)
	    throws SlickException {
	Input input = arg0.getInput();

	// XXX: action processing moved over to BasicShip methods
	if (input.isKeyDown(Input.KEY_LEFT)) {
	    // player.getImg().rotate(-0.4f * delta);
	    // player.getWeapon().getImg().rotate(-0.4f * delta);
	    // player.getEngine().getInGameImg().rotate(-0.4f * delta);
	    // player.getEngine().getSparkleImg().rotate(-0.4f * delta);
	    player.rotateLeft(delta);
	}

	if (input.isKeyDown(Input.KEY_RIGHT)) {
	    // player.getImg().rotate(0.4f * delta);
	    // player.getWeapon().getImg().rotate(0.4f * delta);
	    // player.getEngine().getInGameImg().rotate(0.4f * delta);
	    // player.getEngine().getSparkleImg().rotate(0.4f * delta);
	    player.rotateRight(delta);
	}

	if (input.isKeyDown(Input.KEY_UP)) {
	    // float hip = 0.4f * delta;
	    //
	    // rotation = player.getImg().getRotation();
	    //
	    // x+= hip * Math.sin(Math.toRadians(rotation));
	    // y-= hip * Math.cos(Math.toRadians(rotation));

	    player.moveForward(delta);
	}
	if (input.isKeyDown(Input.KEY_DOWN)) {
	    // float hip = 0.4f * delta;
	    //
	    // rotation = player.getImg().getRotation();
	    //
	    // x-= hip * Math.sin(Math.toRadians(rotation));
	    // y+= hip * Math.cos(Math.toRadians(rotation));

	    player.moveBackward(delta);
	}
	if (input.isKeyDown(Input.KEY_A)) {
	    player.strafeLeft(delta);
	}
	if (input.isKeyDown(Input.KEY_S)) {
	    player.strafeRight(delta);
	}

	player.update(rotation, player.getX(), player.getY());
    }

    @Override
    public int getID() {
	return id;
    }

}
