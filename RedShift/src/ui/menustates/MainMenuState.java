package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

import core.GameDatabase;

public class MainMenuState extends BasicGameState {

	private int id;
	private String title;
	private Rectangle playBTN_rec, scenBTN_rec, optBTN_rec, quitBTN_rec, mouse_rec;
	private GameDatabase gdb;
	private Image campBTN_img, scenBTN_img, optBTN_img, qutBTN_img, montrBKG;
	
	public MainMenuState(int i, GameDatabase g){
		id = i;
		gdb = g;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		title = "RedShift";
		
		mouse_rec = new Rectangle(0,0,1,1);
		
		montrBKG = gdb.getIMG("montrBKC");
		
		campBTN_img = gdb.getIMG("campBTN_n");
		scenBTN_img = gdb.getIMG("scenBTN_n");
		optBTN_img = gdb.getIMG("optBTN_n");
		qutBTN_img = gdb.getIMG("qutBTN_n");

		playBTN_rec = new Rectangle(25, 345,campBTN_img.getWidth(),campBTN_img.getHeight());
		scenBTN_rec = new Rectangle(25, 410, scenBTN_img.getWidth(), scenBTN_img.getHeight());	
		optBTN_rec = new Rectangle(25, 485,optBTN_img.getWidth(),optBTN_img.getHeight());
		quitBTN_rec = new Rectangle(25, 545, qutBTN_img.getWidth(),qutBTN_img.getHeight());

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
		gfx.fill(mouse_rec);
		

		
		gfx.drawImage(montrBKG, 20, 300);
		gfx.drawImage(campBTN_img, 25, 345);
		gfx.drawImage(scenBTN_img, 25, 410);
		gfx.drawImage(optBTN_img, 25, 485);
		gfx.drawImage(qutBTN_img, 25, 545);
		
		
		//XXX:sanity stuff, remove when finalized
//		gfx.draw(playBTN_rec);
//		gfx.draw(scenBTN_rec);
//		gfx.draw(optBTN_rec);
//		gfx.draw(quitBTN_rec);	
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
		//only here for quick moving of recs
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
