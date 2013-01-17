package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

import core.CoreStateManager;

public class InfoState extends BasicGameState{

	public static int id;
	private String header1, header2;
	private int timer=0;
	
	public InfoState(int i){
		id = i;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		header1 = "[Attention]";
		header2 = "The following is a game in its early / Alpha stages." +
				"\nThere will be bugs and imbalances in game mechanics.\n" +
				"If you encounter a bug, let me know at:\n" +
				"Mortarion12@yahoo.com\n\n" +
				"-to skip hit ESC or SPACE-";
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		// TODO Auto-generated method stub
		gfx.setColor(Color.white);
		gfx.drawString(header1, 468, 268);
		gfx.drawString(header2, 350, 300);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		timer++;
		Input i = arg0.getInput();
		if(i.isKeyPressed(Input.KEY_SPACE)){
			arg1.enterState(CoreStateManager.LOADERSTATE, new FadeOutTransition(Color.black), null);
		}
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(CoreStateManager.LOADERSTATE, new FadeOutTransition(Color.black), null);
		}
		if(timer==10000){
			arg1.enterState(CoreStateManager.LOADERSTATE, new FadeOutTransition(Color.black), null);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
