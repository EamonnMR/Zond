package ui.menustates;

import java.util.HashMap;

import level.LevelMetaData;

import org.lwjgl.openal.AL;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UIButton;
import core.CoreStateManager;
import core.GameDatabase;
import core.PlayerClient;
import ents.EntityFactory;

public class MainMenuState extends BasicGameState implements MouseListener {

	//private static final String GameDatabase.get() = null;
	private int id, natoX=700, natoY=195, warsX=400,warsY=195;
	private Rectangle playBTN_rec, scenBTN_rec, optBTN_rec, quitBTN_rec, mouse_rec;
	private GameDatabase gdb;
	private Image montrBKG;
	private boolean campRollover, scenRollover, optRollover, quitRollover, ini, showCamp;
	private Input i;
	private boolean scenSelect;
	private HashMap<String, UIButton> uiButtons;
	private SpriteSheetFont greenFont;
	private SpriteSheetFont grayFont;
	private EntityFactory entFac;
	private PlayerClient pc;
	
	public MainMenuState(int i){
		id = i;
		ini=true;
		uiButtons= new HashMap<String, UIButton>();
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
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
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);

//		gfx.setColor(Color.red);
//		gfx.draw(mouse_rec);
		grayFont.drawString(512-((16*12)/2), 36, "=[Zondv1.3]=");
		
		if(campRollover){
			grayFont.drawString(90, 90, "[(Campaign)]");
			grayFont.drawString(90, 600, "<ERROR> Pilot Data unavailable.");
		}else{
			grayFont.drawString(90, 90, " (Campaign) ");
		}
		
		if(showCamp){
			renderCamp(gfx);
		}
		
		if(scenRollover){
			greenFont.drawString(90, 155, "[(Scenario)]");
			greenFont.drawString(90, 600, "Click here to choose a challenge mission.");
		}else{
			greenFont.drawString(90, 155, " (Scenario) ");
		}
		
		if(scenSelect){
			renderScenarios(gfx);
		}
		
		if(optRollover){
			greenFont.drawString(90, 220, "[(Options)]");
			greenFont.drawString(90, 600, "Click here to configure game options.");
		}else{
			greenFont.drawString(90, 220, " (Options) ");	
		}
		
		if(quitRollover){
			greenFont.drawString(90, 285, "[(Quit)]");
		}else{
			greenFont.drawString(90, 285, " (Quit) ");
		}
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
//		if(showScen){
//			scenRollovers();
//		}
		
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			AL.destroy();
			arg0.exit();
		}
	}
	/**
	 * custom resource load call as part of load restructuring
	 */
	private void loadResources() {
		montrBKG = gdb.getIMG("montrBKC");
		
		for(LevelMetaData lmd : gdb.getAllLevelMetaData().values()){
			String s = lmd.getUiname();
			UIButton b = new UIButton(s, false, lmd);
			b.setRectangle(new Rectangle(0,0,(s.length()+2)*12, 17));
			uiButtons.put(s, b);
		}
		
		greenFont = gdb.getFont("green");
		grayFont = gdb.getFont("gray");
		
		gdb.getSound("menuPrelude").loop(1.0f, pc.getOptions().getMusevol());
	}

	private void updateCollisions(GameContainer gc, StateBasedGame stbg) {
		if (playBTN_rec.intersects(mouse_rec)) {
			if (i.isMousePressed(0)) {
//				stbg.enterState(CoreStateManager.GAMEPLAYSTATE);
			}
		}
		if (scenBTN_rec.intersects(mouse_rec)) {
			if (gc.getInput().isMousePressed(0)) {
				scenSelect=true;
			}
		}
		if (optBTN_rec.intersects(mouse_rec)) {
			if (gc.getInput().isMousePressed(0)) {
				OptionMenuState ops = (OptionMenuState) stbg.getState(CoreStateManager.OPTIONSMENUSTATE);
				ops.setPause(false);
				stbg.enterState(CoreStateManager.OPTIONSMENUSTATE);
			}
		}
		if (quitBTN_rec.intersects(mouse_rec)) {
			if (gc.getInput().isMousePressed(0)) {
				AL.destroy();
				System.exit(0);
			}
		}
		
		for(UIButton u : uiButtons.values()){
			if(u.getRectangle().intersects(mouse_rec)){
				if(gc.getInput().isMouseButtonDown(0)){
//					
					
					BriefingMenuState brief = (BriefingMenuState)stbg.getState(CoreStateManager.BRIEFING);
					LevelMetaData dat = (LevelMetaData)u.getThing();
					brief.setLevel(dat.getName());
					stbg.enterState(CoreStateManager.BRIEFING);
				}
				u.setState(true);
				break;
			}else{
				u.setState(false);
			}
		}
		if(!gdb.getSound("menuPrelude").playing()){
			gdb.getSound("menuPrelude").loop(1.0f, pc.getOptions().getMusevol());
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
	
	private void renderScenarios(Graphics gfx){
		greenFont.drawString(550, 155, "[Scenarios]");
		greenFont.drawString(400, 175, "-WarsawPact-");
		greenFont.drawString(700, 175, "-NATO-");

		//show missions
		natoY = 195;
		warsY = 195;
		for(UIButton u : uiButtons.values()){
			LevelMetaData lmd = (LevelMetaData)u.getThing();
			if(lmd.getFaction()==0){
				u.getRectangle().setX(warsX);
				u.getRectangle().setY(warsY);
				if(u.isState()){
					greenFont.drawString(warsX, warsY, "["+lmd.getUiname()+"]");
					greenFont.drawString(90, 600, lmd.getTltip());
				}else{
					greenFont.drawString(warsX, warsY, " "+lmd.getUiname()+" ");
				}
				warsY+=30;
			}else if(lmd.getFaction()==1){
				u.getRectangle().setX(natoX);
				u.getRectangle().setY(natoY);			
				if(u.isState()){
					greenFont.drawString(natoX, natoY, "["+lmd.getUiname()+"]");
					greenFont.drawString(90, 600, lmd.getTltip());
				}else{
					greenFont.drawString(natoX, natoY, " "+lmd.getUiname()+" ");
				}
				natoY+=30;
			}
		}

	}
	private void campRollovers() {

	}
	private void renderCamp(Graphics gfx) {

	}
	
	@Override
	public int getID() {
		return id;
	}
	
	public void customInit(GameDatabase g, EntityFactory ef, PlayerClient p){
		gdb = g;
		entFac = ef;
		pc = p;
	}
	
	@Override
	public void mouseClicked(int but, int x, int y, int cnt){
		gdb.getSound("click").play();
	}
}
