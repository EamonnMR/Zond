package ui.menustates;

import level.LevelDataModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UIButton;

import core.CoreStateManager;
import core.GameDatabase;

public class PauseMenuState extends BasicGameState {

	private int id;
	private GameDatabase gdb;
	private boolean ini, opt, hang, res;
	private SpriteSheetFont greenFont;
	private Shape mouse_rec;
	private Rectangle unpause, options, hangar;
	
	public PauseMenuState(int i){
		id= i;
		ini = true;
		opt = false;
		res = false;
		hang = false;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		mouse_rec = new Rectangle(0,0,1,1);
		options = new Rectangle(380, 381, 132, 20);
		hangar = new Rectangle(380, 411, 120, 20);
		unpause = new Rectangle(380, 441, 120, 20);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		if(opt){
			greenFont.drawString(380, 384, "[(Options)]");
		}else{
			greenFont.drawString(380, 384, " (Options) ");
		}
		
//		if(hang){
//			greenFont.drawString(380, 414, "[(Hangar)]");
//		}else{
//			greenFont.drawString(380, 414, " (Hangar) ");
//		}
		
		if(res){
			greenFont.drawString(380, 444, "[(Resume)]");
		}else{
			greenFont.drawString(380, 444, " (Resume) ");
		}
		
		greenFont.drawString(710, 202, "+====+");
		greenFont.drawString(710, 219, "|KEYS|");				
		greenFont.drawString(710, 236, "+====+");
		greenFont.drawString(576, 260, "Up Arrow----------Forward");
		greenFont.drawString(576, 280, "Down Arrow--------Backward");
		greenFont.drawString(576, 300, "Left Arrow--------Turn Left");
		greenFont.drawString(576, 320, "Right Arrow-------Turn Right");
		greenFont.drawString(576, 340, "Key Z-------------Strafe Left");
		greenFont.drawString(576, 360, "Key X-------------Strafe Right");
		greenFont.drawString(576, 380, "Left Control------Fire");
		greenFont.drawString(576, 400, "Key C-------------Toggle Radar");
		greenFont.drawString(576, 420, "Key A-------------Toggle Navs");
		greenFont.drawString(576, 440, "Key W-------------Toggle Map");
		greenFont.drawString(576, 460, "Key Esc-----------Leave game");
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(ini){
			loadResources();
			ini = false;
		}
		
		Input in = arg0.getInput();
		mouse_rec.setCenterX(in.getMouseX());
		mouse_rec.setCenterY(in.getMouseY());
		
		if(options.intersects(mouse_rec)){
			opt = true;
			if(in.isMousePressed(0)){
				in.clearMousePressedRecord();
				OptionMenuState ops = (OptionMenuState) arg1.getState(CoreStateManager.OPTIONSMENUSTATE);
				ops.setPause(true);
				arg1.enterState(CoreStateManager.OPTIONSMENUSTATE);
			}
		}
//		else if(hangar.intersects(mouse_rec)){
//			hang = true;
//			if(in.isMousePressed(0)){
//				in.clearMousePressedRecord();
//				arg1.enterState(CoreStateManager.HANGARBAYSTATE);
//			}
//		}
		else if(unpause.intersects(mouse_rec)){
			res = true;
			if(in.isMousePressed(0)){
				in.clearMousePressedRecord();
				arg1.enterState(CoreStateManager.GAMEPLAYSTATE);
			}
		}else{
			opt = false;
			hang =false;
			res = false;
		}
		
	}
	
	private void loadResources() {
		greenFont = gdb.getFont("green");
	}
	

	@Override
	public int getID() {
		return id;
	}

	public void customInit(GameDatabase gb){
		gdb = gb;
	}
}
