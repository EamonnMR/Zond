package ui.menustates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.CoreStateManager;
import core.GameDatabase;
import core.GameplayState;

public class PauseMenuState extends BasicGameState {

	private int id;
	private GameDatabase gdb;
	private boolean ini, opt, quit, res;
	private SpriteSheetFont greenFont;
	private Shape mouse_rec;
	private Rectangle unpause, options, leave;
	
	public PauseMenuState(int i){
		id= i;
		ini = true;
		opt = false;
		res = false;
		quit = false;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		mouse_rec = new Rectangle(0,0,1,1);
		options = new Rectangle(380, 414, 132, 20);
		leave = new Rectangle(380, 444, 120, 20);
		unpause = new Rectangle(380, 384, 120, 20);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		if(opt){
			greenFont.drawString(380, 414, "[(Options)]");
		}else{
			greenFont.drawString(380, 414, " (Options) ");
		}
		
		if(quit){
			greenFont.drawString(380, 444, "[(Leave)]");
		}else{
			greenFont.drawString(380, 444, " (Leave) ");
		}
		
		if(res){
			greenFont.drawString(380, 384, "[(Resume)]");
		}else{
			greenFont.drawString(380, 384, " (Resume) ");
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
		greenFont.drawString(576, 400, "Key C-------------Show Radar");
		greenFont.drawString(576, 420, "Key A-------------Show Navs");
		greenFont.drawString(576, 440, "Key W-------------Show Map");
		greenFont.drawString(576, 460, "Tab---------------Show Tasks");
		greenFont.drawString(576, 480, "Key Esc-----------Pause game");
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
		else if(leave.intersects(mouse_rec)){
			quit = true;
			if(in.isMousePressed(0)){
				in.clearMousePressedRecord();
				in.clearKeyPressedRecord();
				GameplayState gs = (GameplayState)  arg1.getState(CoreStateManager.GAMEPLAYSTATE);
				gs.shutDown();
				arg1.enterState(CoreStateManager.MAINMENUSTATE);
			}
		}
		else if(unpause.intersects(mouse_rec)){
			res = true;
			if(in.isMousePressed(0)){
				in.clearMousePressedRecord();
				GameplayState gs = (GameplayState)  arg1.getState(CoreStateManager.GAMEPLAYSTATE);
				gs.getGameDataBase().getSound(gs.getLevel().getMusic()).loop(1.0f, gs.getMusicVol());
				arg1.enterState(CoreStateManager.GAMEPLAYSTATE);
			}
		}else{
			opt = false;
			quit =false;
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
