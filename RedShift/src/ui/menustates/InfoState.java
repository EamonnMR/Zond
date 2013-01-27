package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import core.CoreStateManager;
import core.GameDatabase;

public class InfoState extends BasicGameState{

	public static int id;
	private String header1, header2,header3,header4,header5,header6;
	private int timer=0;
	private SpriteSheetFont gFont;
	private GameDatabase gdb;
	private boolean ini;
	
	public InfoState(int i){
		id = i;
		ini = true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gFont.drawString(425, 268, header1);
		gFont.drawString(300, 300, header2);
		gFont.drawString(300, 320, header3);
		gFont.drawString(300, 340, header4);
		gFont.drawString(300, 360, header5);
		gFont.drawString(300, 380, header6);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(ini){
			gFont = gdb.getFont("green");
			header1 = "[Attention]";
			header2 = "The following is a game in its early / Alpha stages.";
			header3 = "There will be bugs and imbalances in game mechanics.";
			header4 = "If you encounter a bug, let me know at:";
			header5 = "Mortarion12@yahoo.com";
			header6 = "-to skip hit ESC or SPACE-";
			ini = false;
		}
		timer++;
		Input i = arg0.getInput();
		if(i.isKeyPressed(Input.KEY_SPACE)){
			arg1.enterState(CoreStateManager.PRESENTS,  new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(CoreStateManager.PRESENTS,  new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
		if(timer==10000){
			arg1.enterState(CoreStateManager.PRESENTS,   new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void customInit(GameDatabase gDB){
		this.gdb = gDB;
	}

}
