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

public class MainMenuState extends BasicGameState {

	private int id;
	private String title, playBTN_str, optBTN_str, quitBTN_str;
	private Rectangle playBTN_rec, optBTN_rec, quitBTN_rec, mouse_rec;
	
	public MainMenuState(int i){
		id = i;
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		title = "RedShift";
		playBTN_str = "Campaign";
		optBTN_str = "Options";
		quitBTN_str = "Quit";
		
		mouse_rec = new Rectangle(0,0,1,1);
		playBTN_rec = new Rectangle(100,200,200,50);
		optBTN_rec = new Rectangle(100,350,200,50);
		quitBTN_rec = new Rectangle(100,500,200,50);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.setColor(Color.red);
		gfx.drawString("=["+title+"v1.5]=", 512, 10);
		
		gfx.setColor(Color.yellow);
		gfx.draw(mouse_rec);
		
		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 105.0f, 10.0f);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 175.0f, 10.0f);
		
		gfx.setColor(Color.gray);
		gfx.draw(playBTN_rec);
		gfx.draw(optBTN_rec);
		gfx.draw(quitBTN_rec);
		
		gfx.setColor(Color.red);
		gfx.drawString(playBTN_str, playBTN_rec.getCenterX(), playBTN_rec.getCenterY());
		gfx.drawString(optBTN_str, optBTN_rec.getCenterX(), optBTN_rec.getCenterY());
		gfx.drawString(quitBTN_str, quitBTN_rec.getCenterX(), quitBTN_rec.getCenterY());
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input in = arg0.getInput();
		mouse_rec.setCenterX(in.getMouseX());
		mouse_rec.setCenterY(in.getMouseY());
		
		updateCollisions( arg0,  arg1);
	}

	private void updateCollisions(GameContainer gc, StateBasedGame stbg) {
		if(gc.getInput().isMousePressed(0)){
			if(playBTN_rec.intersects(mouse_rec)){
				try {
					stbg.getState(0).init(gc, stbg);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				stbg.enterState(0, new FadeOutTransition(Color.gray), null);
			}
			if(optBTN_rec.intersects(mouse_rec)){
				stbg.enterState(4);
			}
			if(quitBTN_rec.intersects(mouse_rec)){
//				gc.exit();
			}
		}
//		else if (gc.getInput().isMouseButtonDown(0)){
//			if(playBTN_rec.intersects(mouse_rec)){
//				playBTN_rec.setCenterX(gc.getInput().getMouseX());
//				playBTN_rec.setCenterY(gc.getInput().getMouseY());
//			}
//			if(optBTN_rec.intersects(mouse_rec)){
//				optBTN_rec.setCenterX(gc.getInput().getMouseX());
//				optBTN_rec.setCenterY(gc.getInput().getMouseY());
//			}
//			if(quitBTN_rec.intersects(mouse_rec)){
//				quitBTN_rec.setCenterX(gc.getInput().getMouseX());
//				quitBTN_rec.setCenterY(gc.getInput().getMouseY());
//			}
//		}
	}

	@Override
	public int getID() {
		return id;
	}

}
