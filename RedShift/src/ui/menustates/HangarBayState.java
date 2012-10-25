package ui.menustates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.GameDatabase;
import core.PlayerClient;
import ents.BasicShip;

public class HangarBayState extends BasicGameState {

	private int id;
	private PlayerClient pc;
	private Rectangle acceptBTN_rec, backBTN_rec;
	private BasicShip displayShip;
	private GameDatabase gdb;
	private Image mainScn_i, wepScn_i, engScn_i, briefScn_i;
	
	public HangarBayState(int i, GameDatabase g, PlayerClient p){
		id = i;
		gdb = g;
		pc=p;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		mainScn_i = gdb.getIMG("montrBKC");
		wepScn_i = gdb.getIMG("montrBKC").copy();
		engScn_i = gdb.getIMG("montrBKC").copy();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException{
		
	}

	
	
	
	
	@Override
	public int getID() {
		return id;
	}

}
