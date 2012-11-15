package ui.menustates;

import org.lwjgl.openal.AL;
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

import core.CoreStateManager;
import core.GameDatabase;

public class MainMenuState extends BasicGameState implements MouseListener {

	private int id;
	private String title;
	private Rectangle playBTN_rec, scenBTN_rec, optBTN_rec, quitBTN_rec, mouse_rec;
	private GameDatabase gdb;
	private Image montrBKG;
	private boolean campBool, scenBool, optBool, quitBool, ini;
	private Input i;
	
	public MainMenuState(int i){
		id = i;
		ini=true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		title = "RedShift";
		
		mouse_rec = new Rectangle(0,0,1,1);
		
		playBTN_rec = new Rectangle(90, 90,144,17);
		scenBTN_rec = new Rectangle(90, 155, 144,17);	
		optBTN_rec = new Rectangle(90, 220,132,17);
		quitBTN_rec = new Rectangle(90, 285,96,17);
		
		i = arg0.getInput();
		i.addPrimaryListener(this);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.drawImage(montrBKG, 0, 0);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
		

		gfx.setColor(Color.red);
		gfx.draw(mouse_rec);
		gdb.getFont("gray").drawString(512-((16*12)/2), 36, "=["+title+"v1.0]=");
		

		
		if(campBool==true){
			gdb.getFont("green").drawString(90, 90, "[(Campaign)]");
		}else{
			gdb.getFont("green").drawString(90, 90, " (Campaign) ");
		}
		//TODO:stub: display load / save features
		
		if(scenBool==true){
			gdb.getFont("green").drawString(90, 155, "[(Scenario)]");
		}else{
			gdb.getFont("green").drawString(90, 155, " (Scenario) ");
		}
		//TODO:stub: display scenario selection features
		
		if(optBool==true){
			gdb.getFont("green").drawString(90, 220, "[(Options)]");
		}else{
			gdb.getFont("green").drawString(90, 220, " (Options) ");	
		}
		
		if(quitBool==true){
			gdb.getFont("green").drawString(90, 285, "[(Quit)]");
		}else{
			gdb.getFont("green").drawString(90, 285, " (Quit) ");
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
		if(ini){
			loadResources();
			ini=false;
		}
		
		Input in = arg0.getInput();
		mouse_rec.setCenterX(in.getMouseX());
		mouse_rec.setCenterY(in.getMouseY());
		
		updateRollOvers();
		updateCollisions( arg0,  arg1);
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			AL.destroy();
			arg0.reinit();
			//XXX:change me to proper exit when released
//			arg0.exit();
		}
	}

	/**
	 * custom resource load call as part of load restructuring
	 */
	private void loadResources() {
		montrBKG = gdb.getIMG("montrBKC");
	}

	private void updateCollisions(GameContainer gc, StateBasedGame stbg) {
		if (playBTN_rec.intersects(mouse_rec)) {
			if (i.isMousePressed(0)) {
				stbg.enterState(CoreStateManager.GAMEPLAYSTATE);
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
				AL.destroy();
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
	public int getID() {
		return id;
	}
	
	public void customInit(GameDatabase g){
		gdb = g;
	}
}
