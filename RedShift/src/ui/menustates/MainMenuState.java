package ui.menustates;

import java.util.HashMap;

import level.Scenario;

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

import ui.UIButton;
import core.CoreStateManager;
import core.GameDatabase;

public class MainMenuState extends BasicGameState implements MouseListener {

	private int id, natoX=750, natoY=195, warsX=450,warsY=195;
	private String title;
	private Rectangle playBTN_rec, scenBTN_rec, optBTN_rec, quitBTN_rec, mouse_rec;
	private GameDatabase gdb;
	private Image montrBKG;
	private boolean campRollover, scenRollover, optRollover, quitRollover, ini, showScen, showCamp;
	private Input i;
	private boolean scenSelect;
	private HashMap<String, UIButton> uiButtons;
	
	public MainMenuState(int i){
		id = i;
		ini=true;
		uiButtons= new HashMap<String, UIButton>();
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
		

		
		if(campRollover){
			gdb.getFont("green").drawString(90, 90, "[(Campaign)]");
			gdb.getFont("green").drawString(90, 600, "Click here to play the Story mode.");
		}else{
			gdb.getFont("green").drawString(90, 90, " (Campaign) ");
		}
		//TODO:stub: display load / save features
		if(showCamp){
			renderCamp(gfx);
		}
		
		if(scenRollover){
			gdb.getFont("green").drawString(90, 155, "[(Scenario)]");
			gdb.getFont("green").drawString(90, 600, "Click here to choose a challenge mission.");
		}else{
			gdb.getFont("green").drawString(90, 155, " (Scenario) ");
		}
		//TODO:stub: display scenario selection features
		if(scenSelect){
			renderScenarios(gfx);
		}
		
		if(optRollover){
			gdb.getFont("green").drawString(90, 220, "[(Options)]");
			gdb.getFont("green").drawString(90, 600, "Click here to configure game options.");
		}else{
			gdb.getFont("green").drawString(90, 220, " (Options) ");	
		}
		
		if(quitRollover){
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
		if(showCamp){
			campRollovers();
		}
		if(showScen){
			scenRollovers();
		}
		
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
//				stbg.enterState(CoreStateManager.HANGARBAYSTATE);
				scenSelect=true;
				
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
			campRollover=true;
		}else 
		if(scenBTN_rec.intersects(mouse_rec)){
			scenRollover = true;
		}else 
		if(optBTN_rec.intersects(mouse_rec)){
			optRollover = true;
		}else 
		if(quitBTN_rec.intersects(mouse_rec)){
			quitRollover = true;
		}else{
			campRollover = false;
			scenRollover = false;
			optRollover = false;
			quitRollover = false;
		}
	}

	private void scenRollovers(){
		
	}
	
	private void renderScenarios(Graphics gfx){
		gdb.getFont("green").drawString(600, 155, "[SCENARIOS]");
		gdb.getFont("green").drawString(450, 175, "-WarsawPact-");
		gdb.getFont("green").drawString(750, 175, "-NATO-");
		
		//show missions
		natoY = 195;
		warsY = 195;
		for(UIButton u : uiButtons.values()){
			Scenario s = (Scenario) u.getThing();
			if(s.getFaction()==0){
				u.getRectangle().setX(warsX);
				u.getRectangle().setY(warsY);
				
				gdb.getFont("green").drawString(warsX, warsY, s.getName());
				warsY+=17;
			}else if(s.getFaction()==1){
				u.getRectangle().setX(natoX);
				u.getRectangle().setY(natoY);
				
				gdb.getFont("green").drawString(natoX, natoY, s.getName());
				natoY+=17;
			}
				

		}
//		for(Scenario s : uiButtons.keySet()){
//			if(s.getFaction()==0){
//				gdb.getFont("green").drawString(natoX, natoY, s.getName());
//				natoY+=20;
//				
//			}else if (s.getFaction()==1){
//				gdb.getFont("green").drawString(warsX, warsY, s.getName());
//				warsY+=20;
//			}
//		}
	}
	private void campRollovers() {

	}
	private void renderCamp(Graphics gfx) {

	}

	
	@Override
	public int getID() {
		return id;
	}
	
	public void customInit(GameDatabase g){
		gdb = g;
		if(!(gdb.getScenarios()==null)){
			for(Scenario s : gdb.getScenarios().values()){
//				uiButtons.put(s, new Rectangle(0,0,s.getName().length()*12,17));
				UIButton b = new UIButton(s.getName(), false, s);
				b.setRectangle(new Rectangle(0,0,s.getName().length()*12, 17));
				uiButtons.put(s.getName(), b);
			}
		}
	}
}
