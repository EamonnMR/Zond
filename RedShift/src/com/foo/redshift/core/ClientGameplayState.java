package com.foo.redshift.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.foo.redshift.shifts.BaseLevel;
import com.foo.redshift.shifts.BaseShiftity;
import com.foo.redshift.shifts.BasicGun;
import com.foo.redshift.shifts.BasicShip;
import com.foo.redshift.shifts.BasicShot;
import com.foo.redshift.shifts.ShiftityFactory;

public class ClientGameplayState extends BasicGameState {

    private static final int IMG_HEIGHT = 1600;
    private static final int IMG_WIDTH = 1600;
    private int height;
    private int width;
    // vars
    private int id, entCount, objCount, shotCount;
    private ShiftityFactory entFac;
    private PlayerClient pc, pc2;
    private BaseLevel level;
    private BaseShiftity asteroid;
    private HashMap<Integer, BasicShip> ships;
    private HashMap<Integer, BasicShot> shots;
    private HashMap<Integer, BaseShiftity> doodads;

    // const
    public ClientGameplayState(int i, ShiftityFactory ef, PlayerClient PC) {
	id = i;
	entFac = ef;
	pc = PC;
	pc2 = new PlayerClient(1);
	ships = new HashMap<Integer, BasicShip>();
	shots = new HashMap<Integer, BasicShot>();
	doodads = new HashMap<Integer, BaseShiftity>();
    }

    // methods
    @Override
    public void init(GameContainer arg0, StateBasedGame arg1)
	    throws SlickException {
	width = arg0.getWidth();
	height = arg0.getHeight();

	// level = new BaseLevel("Scratch",1600,1600);
	level = new BaseLevel("Scratch", new Rectangle(0, 0, IMG_WIDTH,
		IMG_HEIGHT));
	level.setBkgIMG(new Image("assets/images/ScratchLevel.png"));

	pc.setShip(entFac.stockMercury());
	pc.getShip().setEngine(entFac.stockEngine());
	pc.getShip().setWeapon(entFac.stock20mm());
	pc.getShip().setX((width / 2));
	pc.getShip().setY((height / 2));

	pc2.setShip(entFac.stockVostok());
	pc2.getShip().setEngine(entFac.stockEngine());
	pc2.getShip().setWeapon(entFac.stock20mm());
	pc2.getShip().setX(800);
	pc2.getShip().setY(400);

	addShip(pc.getShip());
	addShip(pc2.getShip());

	asteroid = entFac.smallAst();
	asteroid.setX(250);
	asteroid.setY(250);
	addObject(asteroid);
    }

    @Override
    public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
	    throws SlickException {
	level.render(arg2, 0, 0);
	// pc.getShip().render(arg2);
	// pc2.getShip().render(arg2);
	for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
	    entry.getValue().render();
	}
	for (Map.Entry<Integer, BaseShiftity> entry : doodads.entrySet()) {
	    entry.getValue().render();
	}

	for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()) {
	    entry.getValue().render();
	}

	BasicShip ship = pc.getShip();
	arg2.draw(ship.getCollider());
	arg2.draw(pc2.getShip().getCollider());

	String x = String.valueOf(arg0.getInput().getMouseX());
	arg2.drawString(x, 25, 700);
	x = String.valueOf(arg0.getInput().getMouseY());
	arg2.drawString(x, 25, 725);

	x = String.valueOf(ship.getHealth());
	arg2.drawString("Players Health: " + x, 10, 35);

	arg2.drawString("Players X: " + (int) ship.getX(), 10, 50);
	arg2.drawString("Players Y: " + (int) ship.getY(), 10, 65);

	x = String.valueOf(pc2.getShip().getHealth());
	arg2.drawString("Dummy Health: " + x, 10, 80);

	arg2.drawString("Shottas: " + shotCount, 10, 95);
    }

    @Override
    public void update(GameContainer arg0, StateBasedGame arg1, int delta)
	    throws SlickException {
	Input p = arg0.getInput();

	BasicShip mehShip = pc.getShip();
	BasicGun mehWeapon = mehShip.getWeapon();

	if (p.isKeyDown(pc.getKey(0))) {
	    mehShip.moveForward(delta);
	}
	if (p.isKeyDown(pc.getKey(1))) {
	    mehShip.moveBackward(delta);
	}
	if (p.isKeyDown(pc.getKey(2))) {
	    mehShip.rotateLeft(delta);
	}
	if (p.isKeyDown(pc.getKey(3))) {
	    mehShip.rotateRight(delta);
	}
	if (p.isKeyDown(pc.getKey(4))) {
	    mehShip.strafeLeft(delta);
	}
	if (p.isKeyDown(pc.getKey(5))) {
	    mehShip.strafeRight(delta);
	}
	if (p.isKeyDown(pc.getKey(6))) {
	    addShot(mehWeapon.makeShot());
	}

	doCollisions();

	for (Map.Entry<Integer, BasicShip> entry : ships.entrySet()) {
	    entry.getValue().update(delta, entry.getValue().getX(),
		    entry.getValue().getY());
	}

	for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()) {
	    entry.getValue().update(delta);
	}
	for (Map.Entry<Integer, BasicShot> entry : shots.entrySet()) {
	    if (entry.getValue().getLifetime() <= 0) {
		shots.remove(entry);
	    }
	}

	for (Map.Entry<Integer, BaseShiftity> entry : doodads.entrySet()) {
	    entry.getValue().update(delta);
	}

    }

    /**
     * adds entity to the hashmap
     */
    public int addShip(BasicShip e) {
	entCount++;
	ships.put(entCount, e);
	return entCount;
    }

    /**
     * add a BaseEnt to the doodad hashmap
     * 
     * @param e
     * @return int objCount
     */
    public int addObject(BaseShiftity e) {
	objCount++;
	doodads.put(objCount, e);
	return objCount;
    }

    /**
     * 
     * @param s
     * @return
     */
    public int addShot(BasicShot s) {
	shotCount++;
	shots.put(shotCount, s);
	return shotCount;
    }

    /**
     * run collsion checks
     */
    public void doCollisions() {
	Set<Entry<Integer, BasicShip>> shipsEntrySet = ships.entrySet();
	Iterator<Entry<Integer, BasicShip>> shipsEntrySetIterator = shipsEntrySet
		.iterator();
	while (shipsEntrySetIterator.hasNext()) {
	    Entry<Integer, BasicShip> currentShipMap = shipsEntrySetIterator.next();
	    BasicShip currentShip = currentShipMap.getValue();
	    reachAround(currentShip);
	    Set<Entry<Integer, BasicShot>> shotsEntrySet = shots.entrySet();
	    Iterator<Entry<Integer, BasicShot>> shotsEntrySetIterator = shotsEntrySet
		    .iterator();
	    while (shotsEntrySetIterator.hasNext()) {
		Entry<Integer, BasicShot> currentShot = shotsEntrySetIterator
			.next();
		BasicShot basicShot = currentShot.getValue();
		if (isSpecificHit(currentShip,
			currentShot.getValue())) {
		    double tempHp = currentShip.getHealth()
			    - basicShot.getDamage();
		    currentShip.setHealth(tempHp);
		}
		if (isOffScreen(basicShot)) {
		    shotCount--;
		    shotsEntrySetIterator.remove();
		}

	    }
	}
    }

    private void reachAround(BasicShip currentShip) {
	double x = currentShip.getX();
	double y = currentShip.getY();
	if (x >= (double) width)
	{
	    currentShip.setX(0.0);
	}
	else if (x <= 0.0)
	{
	    currentShip.setX((double) width);
	}
	else if(y >= (double) height)
	{
	    currentShip.setY(0.0);
	}
	else if (y <= 0.0) {
	    currentShip.setY((double) height);
	}
    }

    private boolean isOffScreen(BasicShot basicShot) {
	double x = basicShot.getX();
	double y = basicShot.getY();
	if (x >= (double) width || x <= 0.0 || y >= (double) height
		|| y <= 0.0) {
	    return true;
	}
	return false;
    }

    /**
     * makes a specific collison check between a ship, and a shot
     * 
     * @param a
     *            BasicShip
     * @param b
     *            BasicShot
     * @return boolean
     */
    public boolean isSpecificHit(BasicShip a, BasicShot b) {
	if (a.getCollider().intersects(b.getCollider())) {
	    return true;
	} else if (b.getCollider().intersects(a.getCollider())) {
	    return true;
	}
	return false;
    }

    @Override
    public int getID() {
	return id;
    }

    public void setEntFac(ShiftityFactory ef) {
	entFac = ef;
    }

    public void setPlayerClient(PlayerClient PC) {
	pc = PC;
    }

}
