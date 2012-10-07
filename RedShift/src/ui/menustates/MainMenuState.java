package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

import ui.UILib;

public class MainMenuState extends BasicGameState {

	private int id;
	private String title, playBTN_str, scenBTN_str, optBTN_str, quitBTN_str;
	private Rectangle playBTN_rec, scenBTN_rec, optBTN_rec, quitBTN_rec, mouse_rec, comScrn_rec;
	private UILib ulib;
	
	public MainMenuState(int i){
		id = i;
		ulib = new UILib();
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		title = "RedShift";
		playBTN_str = "[Campaign]";
		scenBTN_str = "[Scenarios]";
		optBTN_str = "[Options]";
		quitBTN_str = "[Quit]";
		
		mouse_rec = new Rectangle(0,0,1,1);
		playBTN_rec = new Rectangle(0,0,100,25);
		playBTN_rec.setCenterX(100);
		playBTN_rec.setCenterY(357);
		
		scenBTN_rec = new Rectangle(0, 0, 100, 25);
		scenBTN_rec.setCenterX(100);
		scenBTN_rec.setCenterY(455);
		
		optBTN_rec = new Rectangle(0,0,90,25);
		optBTN_rec.setCenterX(95);
		optBTN_rec.setCenterY(553);
		
		quitBTN_rec = new Rectangle(0,0,60,25);
		quitBTN_rec.setCenterX(80);
		quitBTN_rec.setCenterY(651);
		comScrn_rec = new Rectangle(20, 300, 500, 450);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 100, 10);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 150, 10);
		
		gfx.setColor(Color.red);
		gfx.drawString("=["+title+"v1.5]=", 512, 10);
		
		gfx.setColor(Color.yellow);
		gfx.draw(mouse_rec);
		
		gfx.setColor(Color.green);
		gfx.draw(comScrn_rec);
		gfx.fill(mouse_rec);
		
		gfx.draw(playBTN_rec);
		gfx.draw(scenBTN_rec);
		gfx.draw(optBTN_rec);
		gfx.draw(quitBTN_rec);
		
		ulib.drawStringAtShapeCenter(playBTN_str,playBTN_rec , gfx);
		ulib.drawStringAtShapeCenter(scenBTN_str, scenBTN_rec, gfx);
		ulib.drawStringAtShapeCenter(optBTN_str,optBTN_rec , gfx);
		ulib.drawStringAtShapeCenter(quitBTN_str,quitBTN_rec , gfx);
	
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input in = arg0.getInput();
		mouse_rec.setCenterX(in.getMouseX());
		mouse_rec.setCenterY(in.getMouseY());
		
		updateCollisions( arg0,  arg1);
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg0.exit();
		}
	}

	private void updateCollisions(GameContainer gc, StateBasedGame stbg) {
		if(gc.getInput().isMousePressed(0)){
			if(playBTN_rec.intersects(mouse_rec)){
				try {
					stbg.getState(0).init(gc, stbg);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				stbg.enterState(1, new FadeOutTransition(Color.gray), null);
			}
			if(scenBTN_rec.intersects(mouse_rec)){
				
			}
			if(optBTN_rec.intersects(mouse_rec)){
				stbg.enterState(4);

			}
			if(quitBTN_rec.intersects(mouse_rec)){
				gc.exit();
			}
		}
		else if (gc.getInput().isMouseButtonDown(0)){
//			if(playBTN_rec.intersects(mouse_rec)){
//				playBTN_rec.setCenterX(gc.getInput().getMouseX());
//				playBTN_rec.setCenterY(gc.getInput().getMouseY());
//			}
//			if(scenBTN_rec.intersects(mouse_rec)){
//				scenBTN_rec.setCenterX(mouse_rec.getCenterX());
//				scenBTN_rec.setCenterY(mouse_rec.getCenterY());
//			}
//			if(optBTN_rec.intersects(mouse_rec)){
//				optBTN_rec.setCenterX(gc.getInput().getMouseX());
//				optBTN_rec.setCenterY(gc.getInput().getMouseY());
//			}
//			if(quitBTN_rec.intersects(mouse_rec)){
//				quitBTN_rec.setCenterX(gc.getInput().getMouseX());
//				quitBTN_rec.setCenterY(gc.getInput().getMouseY());
//			}
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
