package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

import core.CoreStateManager;
import core.GameDatabase;

public class MainMenuState extends BasicGameState implements MouseListener {

	private int id;
	private String title;
	private Rectangle playBTN_rec, scenBTN_rec, optBTN_rec, quitBTN_rec, mouse_rec;
	private GameDatabase gdb;
	private Image montrBKG;
	private boolean campBool, scenBool, optBool, quitBool;
	private Input i;
	
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
		playBTN_rec = new Rectangle(25, 345,144,17);
		scenBTN_rec = new Rectangle(25, 410, 144,17);	
		optBTN_rec = new Rectangle(25, 485,132,17);
		quitBTN_rec = new Rectangle(25, 545,96,17);
		
		i = arg0.getInput();
		i.addPrimaryListener(this);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
		
		
		gfx.setColor(Color.red);
		gdb.getFont("gray").drawString(512-((16*12)/2), 10, "=["+title+"v1.0]=", new Color(255,39,64));
		
//		gfx.setColor(Color.yellow);
//		gfx.draw(mouse_rec);
//		
//		gfx.setColor(Color.green);
//		gfx.fill(mouse_rec);
		
		gfx.drawImage(montrBKG, 20, 300);
		
		if(campBool==true){
			gdb.getFont("green").drawString(25, 345, "[(Campaign)]");
		}else{
			gdb.getFont("green").drawString(25, 345, " (Campaign) ");
		}
		
		if(scenBool==true){
			gdb.getFont("green").drawString(25, 410, "[(Scenario)]");
		}else{
			gdb.getFont("green").drawString(25, 410, " (Scenario) ");
		}
		
		if(optBool==true){
			gdb.getFont("green").drawString(25, 485, "[(Options)]");
		}else{
			gdb.getFont("green").drawString(25, 485, " (Options) ");	
		}
		
		if(quitBool==true){
			gdb.getFont("green").drawString(25, 545, "[(Quit)]");
		}else{
			gdb.getFont("green").drawString(25, 545, " (Quit) ");
		}
		
		
		
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
		
		updateRollOvers();
		updateCollisions( arg0,  arg1);
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg0.exit();
		}
	}

	private void updateCollisions(GameContainer gc, StateBasedGame stbg) {
		if (playBTN_rec.intersects(mouse_rec)) {
			if (i.isMousePressed(0)) {
				stbg.enterState(CoreStateManager.PLAYSTATE);
			}
		}
		if (scenBTN_rec.intersects(mouse_rec)) {
			if (gc.getInput().isMousePressed(0)) {
				stbg.enterState(CoreStateManager.HANGARBAYSTATE);
			}
		}
		if (optBTN_rec.intersects(mouse_rec)) {
			if (gc.getInput().isMousePressed(0)) {
				stbg.enterState(CoreStateManager.OPTIONSMENUSTATE);
			}
		}
		if (quitBTN_rec.intersects(mouse_rec)) {
			if (gc.getInput().isMousePressed(0)) {
				System.exit(0);
			}
		}

	}
	

	private void updateRollOvers() {
		if(playBTN_rec.intersects(mouse_rec)){
			campBool=true;
		}else 
		if(scenBTN_rec.intersects(mouse_rec)){
			scenBool = true;
		}else 
		if(optBTN_rec.intersects(mouse_rec)){
			optBool = true;
		}else 
		if(quitBTN_rec.intersects(mouse_rec)){
			quitBool = true;
		}else{
			campBool = false;
			scenBool = false;
			optBool = false;
			quitBool = false;
		}
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame stbg){
		mouse_rec.setX(gc.getInput().getMouseX());
		mouse_rec.setY(gc.getInput().getMouseY());
	}

	@Override
	public int getID() {
		return id;
	}
}
