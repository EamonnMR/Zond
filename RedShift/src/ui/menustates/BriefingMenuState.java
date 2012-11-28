package ui.menustates;

import java.awt.Point;

import level.LevelDataModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UILib;
import core.GameDatabase;

public class BriefingMenuState extends BasicGameState {

	private int id;
	private UILib ulib;
	private GameDatabase gdb;
	private boolean ini;
	private SpriteSheetFont greenF, grayF;
	private Image backdrop;
	private String hangar, back;
	private LevelDataModel ldm;
	private Rectangle mouse_rec;
	
	public BriefingMenuState(int i){
		id = i;
		ini=true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		mouse_rec = new Rectangle(0, 0, 1, 1);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		ulib.drawImageCenteredOnPoint(gfx, backdrop, new Point(512,384));
		grayF.drawString(512-((16*12)/2), 36, "=[Redshiftv1.0]=");
		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(ini){
			loadResources();
			ini=false;
		}
		Input i = arg0.getInput();
		mouse_rec.setCenterX(i.getMouseX());
		mouse_rec.setCenterY(i.getMouseY());
	}

	private void loadResources() {
		greenF = gdb.getFont("green");
		grayF = gdb.getFont("gray");
		backdrop = gdb.getIMG("montrBKC");
	}

	@Override
	public int getID() {
		return id;
	}

	public void customInit(GameDatabase g){
		gdb = g;
	}
}
