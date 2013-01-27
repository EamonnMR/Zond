package ui.menustates;

import java.awt.Point;
import java.text.DecimalFormat;

import org.newdawn.slick.Color;
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

import ui.UILib;
import core.CoreStateManager;
import core.GameDatabase;
import ents.OptionsEnt;

public class OptionMenuState extends BasicGameState implements MouseListener{

	private int id;
	private OptionsEnt options;
	private Rectangle mouse_rec, sfxDisplay_rec, musDisplay_rec, voiDisplay_rec, backBTN_rec, modBTN_rec;
	private Rectangle sfxVol_sld, musVol_sld, voiVol_sld, onPart_rec, onFsc_rec;
	private Rectangle sfxBnd_rec, musBnd_rec, voiBnd_rec;
	private UILib uilib;
	private GameDatabase gdb;
	private Image bkIMG, optLBL_i, sfxLBL_i, musLBL_i, voiLBL_i, fscLBL_i, partLBL_i, onBTN_i, offBTN_i;
	private boolean backBool, cfgHudBool, ini, overFsc, overPart, pause;
	private SpriteSheetFont greenFont, grayFont;
	
	public OptionMenuState(int i){
		id = i;
		uilib = new UILib();
		ini = true;
//		s = gdb.getSound("twentys");
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		//display rectangles
		mouse_rec = new Rectangle(0,0,1,1);
		
		sfxDisplay_rec = new Rectangle(180,227,100,11);
		musDisplay_rec = new Rectangle(180,303,100,11);
		voiDisplay_rec = new Rectangle(180,377,100,11);
		
		backBTN_rec = new Rectangle(90,430,96,19);
		modBTN_rec = new Rectangle(326, 430,204,22);
		
		//bounding rectangles
		sfxBnd_rec = new Rectangle(180,222,100,21);
		musBnd_rec = new Rectangle(180,298,100,21);
		voiBnd_rec = new Rectangle(180,372,100,21);
		
		//sliders
		sfxVol_sld = new Rectangle(180,222,8,21);
		sfxVol_sld.setCenterX(230);
		
		musVol_sld = new Rectangle(180,298,8,21);
		musVol_sld.setCenterX(230);
		
		voiVol_sld = new Rectangle(180,372,8,21);
		voiVol_sld.setCenterX(230);
		
		//On/off Button
		onPart_rec = new Rectangle(475, 222, 84,20);
		onFsc_rec = new Rectangle(475, 296, 84,20);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.drawImage(bkIMG, 0, 0);
		grayFont.drawString(512-((16*12)/2), 36, "=[Zondv1.2]=");
		gfx.setColor(Color.green);
		
		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 100, 10);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 150, 10);
		
		renderLabels(gfx);
		renderSliders(gfx);
		renderOnOffs(gfx);
		renderKeys(gfx);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		if(ini){
			loadResource();
			ini=false;
		}
		Input in = arg0.getInput();
		mouse_rec.setCenterX(in.getMouseX());
		mouse_rec.setCenterY(in.getMouseY());
		
		updateRollOvers();
		updateCollisions(delta, arg0, in, arg1);
		
		updateOptionsVals();
		
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			in.clearMousePressedRecord();
			in.clearKeyPressedRecord();
			if(pause){
				arg1.enterState(CoreStateManager.PAUSE);
			}else{
				arg1.enterState(CoreStateManager.MAINMENUSTATE);
			}
		}
	}

	private void loadResource() {
		bkIMG = gdb.getIMG("montrBKC");
		optLBL_i = gdb.getIMG("optLBL_n");
		sfxLBL_i = gdb.getIMG("sfxLBL_n");
		musLBL_i = gdb.getIMG("musLBL_n");
		voiLBL_i = gdb.getIMG("voiLBL_n");
		fscLBL_i = gdb.getIMG("fscLBL_n");
		partLBL_i = gdb.getIMG("ptcLBL_n");
		onBTN_i = gdb.getIMG("onBTN_n");
		offBTN_i = gdb.getIMG("offBTN_n");
		onBTN_i = gdb.getIMG("onBTN_n");
		offBTN_i = gdb.getIMG("offBTN_n");
		greenFont = gdb.getFont("green");
		grayFont = gdb.getFont("gray");
	}

	/**
	 * checks for collisions between cursor and gui elements
	 * @param delta
	 * @param mouse
	 * @param gc
	 * @param in
	 * @param stg
	 */
	public void updateCollisions(int delta, GameContainer gc, Input in, StateBasedGame stg){
		if (in.isMouseButtonDown(0)) {
			if (sfxBnd_rec.intersects(mouse_rec)) {
				if (sfxVol_sld.intersects(mouse_rec)&& sfxDisplay_rec.contains(in.getMouseX(), in.getMouseY())) {
					sfxVol_sld.setCenterX(in.getMouseX());
				} else if (sfxDisplay_rec.contains(in.getMouseX(), in.getMouseY())) {
						sfxVol_sld.setCenterX(in.getMouseX());
				}
			}

			if (musBnd_rec.intersects(mouse_rec)) {
				if (musVol_sld.intersects(mouse_rec) && musDisplay_rec.contains(in.getMouseX(), in.getMouseY())) {
					musVol_sld.setCenterX(in.getMouseX());
				} else if (musDisplay_rec.contains(in.getMouseX(), in.getMouseY())) {
					musVol_sld.setCenterX(in.getMouseX());
				}
			}

			if (voiBnd_rec.intersects(mouse_rec)) {
				if (voiVol_sld.intersects(mouse_rec)&& voiDisplay_rec.contains(in.getMouseX(), in.getMouseY())) {
					voiVol_sld.setCenterX(in.getMouseX());
				} else if (voiDisplay_rec.contains(in.getMouseX(), in.getMouseY())) {
					voiVol_sld.setCenterX(in.getMouseX());
				}
			}
		}


		if (onPart_rec.intersects(mouse_rec)) {
			if (in.isMousePressed(0)) {
				if (options.getParticleStatus() == true) {
					options.setParticleStatus(false);
				} else {
					options.setParticleStatus(true);
				}
			}
		}
		if (onFsc_rec.intersects(mouse_rec)) {
			if (in.isMousePressed(0)) {
				if (options.getFullscreenStatus() == true) {
					options.setFullscreenStatus(false);
					try {
						gc.setFullscreen(false);
					} catch (SlickException e) {
						e.printStackTrace();
					}
				} else {
					options.setFullscreenStatus(true);
					try {
						gc.setFullscreen(true);
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}

		}
		if (modBTN_rec.intersects(mouse_rec)) {
			if (in.isMousePressed(0)) {
				in.clearMousePressedRecord();
				in.clearKeyPressedRecord();
				stg.enterState(CoreStateManager.HUDMODSTATE);
			}
		}
		if (backBTN_rec.intersects(mouse_rec)) {
			if (in.isMousePressed(0)) {
				if(pause){
					stg.enterState(CoreStateManager.PAUSE);
				}else{
					stg.enterState(CoreStateManager.MAINMENUSTATE);
				}
			}
		}
		
	}
	
	private void updateRollOvers() {
		if(backBTN_rec.intersects(mouse_rec)){
			backBool=true;
		}else 
		if(modBTN_rec.intersects(mouse_rec)){
			cfgHudBool = true;
		}else{
			backBool = false;
			cfgHudBool = false;
		}
		if(onFsc_rec.intersects(mouse_rec)){
			overFsc=true;
		}else{
			overFsc=false;
		}
		if(onPart_rec.intersects(mouse_rec)){
			overPart=true;
		}else{
			overPart=false;
		}
	}
	
	private void updateOptionsVals() {
		
		float ratio = 1.0f/100f;
		
		float fxSldr_x = sfxVol_sld.getCenterX();
		options.setFxvol((fxSldr_x-sfxBnd_rec.getX())*ratio);
		
		fxSldr_x = musVol_sld.getCenterX(); 
		options.setMusevol((fxSldr_x-sfxBnd_rec.getX())*ratio);
		
		fxSldr_x = voiVol_sld.getCenterX();
		options.setVoicevol((fxSldr_x-sfxBnd_rec.getX())*ratio);
		

	}

	private void renderLabels(Graphics gfx){
		gfx.setColor(Color.green);
		
		if(backBool==true){
			greenFont.drawString(90,430, "[(Back)]");
		}else{
			greenFont.drawString(90,430, " (Back) ");
		}
		if(cfgHudBool==true){
			greenFont.drawString(326, 430, "[(Configure HUD)]");
			greenFont.drawString(90, 600, "Click here to arrange information on your heads up display.");
		}else{
			greenFont.drawString(326, 430, " (Configure HUD) ");
		}
		
		uilib.drawImageCenteredOnPoint(gfx, optLBL_i, new Point(512,120));
		gfx.drawImage(sfxLBL_i, 90, 205);
		gfx.drawImage(musLBL_i, 90, 280);
		gfx.drawImage(voiLBL_i, 90, 355);
		gfx.drawImage(partLBL_i, 326 ,205);
		gfx.drawImage(fscLBL_i, 326, 280);
	}
	
	private void renderSliders(Graphics gfx){
		gfx.setColor(Color.green);
		gfx.draw(sfxDisplay_rec);
		gfx.draw(musDisplay_rec);
		gfx.draw(voiDisplay_rec);
		
		gfx.fill(sfxVol_sld);
		
		uilib.drawStringNextToShape(getFormattedValue(options.getFxvol()), sfxDisplay_rec, 6, 1, gfx);
		
		gfx.fill(musVol_sld);
		
		uilib.drawStringNextToShape(getFormattedValue(options.getMusevol()), musDisplay_rec, 6, 1, gfx);
		
		gfx.fill(voiVol_sld);
		
		uilib.drawStringNextToShape(getFormattedValue(options.getVoicevol()), voiDisplay_rec, 6, 1, gfx);	
	}
	
	/**
	 * simple little method to show on btn or off btn
	 * @param gfx
	 */
	private void renderOnOffs(Graphics gfx) {
		if(options.getParticleStatus()){
			gfx.drawImage(onBTN_i, 475, 222);
		}else{
			gfx.drawImage(offBTN_i, 475, 222);
		}
		
		if(options.getFullscreenStatus()){
			gfx.drawImage(onBTN_i, 475, 296);
		}else{
			gfx.drawImage(offBTN_i, 475, 296);
		}
		
		if(overFsc){
			greenFont.drawString(90, 600, "Click here to toggle Fullscreen. [Not Optimized]");
		}
		
		if(overPart){
			greenFont.drawString(90, 600, "Click here to toggle Particle effects. [Not Implemented]");
		}
	}
	
	private void renderKeys(Graphics gfx) {
		if(!pause){
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
	}
	
	private String getFormattedValue(float val){
		String form;
		double mod = val*100;
		form = String.valueOf(new DecimalFormat("###").format(mod));
		return form;
	}
	
	public void customInit(OptionsEnt o, GameDatabase g){
		options = o;
		gdb = g;
	}
	
	@Override
	public int getID() {
		return id;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	@Override
	public void mouseClicked(int but, int x, int y, int cnt){
		gdb.getSound("click").play();
	}
	
	
}
