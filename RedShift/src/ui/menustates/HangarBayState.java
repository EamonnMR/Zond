package ui.menustates;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UILib;

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
	private UILib ulib;
	private Point center;
	
	public HangarBayState(int i, GameDatabase g, PlayerClient p){
		id = i;
		gdb = g;
		pc=p;
		ulib = new UILib();
		center = new Point(512,500);
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		mainScn_i = gdb.getIMG("montrBKC");
		wepScn_i = gdb.getIMG("small_scrn");
		engScn_i = gdb.getIMG("small_scrn");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		renderScreens(gfx);
		
		renderEngines(gfx);
		
		renderWeapons(gfx);

		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException{
		
	}

	
	private void renderScreens(Graphics gfx) {
		ulib.drawImageCenteredOnPoint(gfx, mainScn_i, center);

		
	}
	

	private void renderWeapons(Graphics gfx) {
		ulib.drawImageNextToImage(gfx, mainScn_i, wepScn_i, center, 0, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("wep_i"), 
				new Point(center.x-((mainScn_i.getWidth()/2)+(wepScn_i.getWidth()/2))
						,center.y-(mainScn_i.getHeight()/2-10)));
		
	}

	private void renderEngines(Graphics gfx) {
		ulib.drawImageNextToImage(gfx, mainScn_i, engScn_i, center, 1, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("engine_i"), 
				new Point(center.x+((mainScn_i.getWidth()/2)+(engScn_i.getWidth()/2))
						,center.y-(mainScn_i.getHeight()/2-10)));
		
	}
	
	
	@Override
	public int getID() {
		return id;
	}

}
