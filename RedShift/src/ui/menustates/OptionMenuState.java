package ui.menustates;

import java.text.DecimalFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UILib;
import core.GameDatabase;
import ents.OptionsEnt;

public class OptionMenuState extends BasicGameState{

	private int id, mX, mY;
	private OptionsEnt options;
	private Rectangle mouse_rec, sfxDisplay_rec, musDisplay_rec, voiDisplay_rec, backBTN_rec, modBTN_rec, comScrn_rec;
	private Rectangle sfxVol_sld, musVol_sld, voiVol_sld, onPart_rec, onFsc_rec;
	private Rectangle sfxBnd_rec, musBnd_rec, voiBnd_rec;
	private UILib uilib;
	private float fx_prvX,sfxBnd_x;
	private GameDatabase gdb;
	private Sound s;
	private Image bkIMG,backBTN_i, cfghudBTN_i, optLBL_i, sfxLBL_i, musLBL_i, voiLBL_i, fscLBL_i, partLBL_i, onBTN_i, offBTN_i;
	
	public OptionMenuState(int i, OptionsEnt ops, GameDatabase gdb){
		id = i;
		options = ops;
		uilib = new UILib();
		this.gdb = gdb;
//		s = gdb.getSound("twentys");
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		bkIMG = gdb.getIMG("montrBKC");
		backBTN_i = gdb.getIMG("bckBTN_n");
		cfghudBTN_i = gdb.getIMG("cfghdBTN_n");
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
		
		//display rectangles
		mouse_rec = new Rectangle(0,0,1,1);
		
		sfxDisplay_rec = new Rectangle(121,422,100,11);
		musDisplay_rec = new Rectangle(121,497,100,11);
		voiDisplay_rec = new Rectangle(121,572,100,11);
		
		comScrn_rec = new Rectangle(20, 300, 500, 450);
		
		backBTN_rec = new Rectangle(35,650,96,19);
		modBTN_rec = new Rectangle(261,567,165,22);
		
		//bounding rectangles
		sfxBnd_rec = new Rectangle(121,418,100,21);
		sfxBnd_x = sfxBnd_rec.getX();
		musBnd_rec = new Rectangle(121,492,100,21);
		voiBnd_rec = new Rectangle(121,568,100,21);
		
		//sliders
		sfxVol_sld = new Rectangle(155,418,5,21);
		sfxVol_sld.setCenterX(169);
		
		musVol_sld = new Rectangle(155,493,5,21);
		musVol_sld.setCenterX(169);
		
		voiVol_sld = new Rectangle(155,568,5,21);
		voiVol_sld.setCenterX(169);
		
		//On/off Button
		onPart_rec = new Rectangle(410, 417, 84,20);
		onFsc_rec = new Rectangle(410, 492, 84,20);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.drawImage(bkIMG, 20, 300);
		gfx.setColor(Color.green);

		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 100, 10);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 150, 10);
		
		
		gfx.drawString(String.valueOf(sfxVol_sld.getCenterX()), 200, 10);
		gfx.drawString(String.valueOf(fx_prvX), 250, 10);
		
		gfx.draw(comScrn_rec);
		
		renderLabels(gfx);
		renderSliders(gfx);
		renderOnOffs(gfx);
		
		gfx.setColor(Color.darkGray);
		gfx.fill(mouse_rec);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {
		Input in = arg0.getInput();
		mX = in.getMouseX();
		mY = in.getMouseY();
		
		mouse_rec.setX(mX);
		mouse_rec.setY(mY);
		
		updateCollisions(delta, mouse_rec, arg0, in, arg1);
		
		updateOptionsVals();
		
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(3);
		}
	}
	/**
	 * checks for collisions between cursor and gui elements
	 * @param delta
	 * @param mouse
	 * @param gc
	 * @param in
	 * @param stg
	 */
	public void updateCollisions(int delta, Rectangle mouse, GameContainer gc, Input in, StateBasedGame stg){
		//XXX:polish-make sound when button let up
		if(mouse.intersects(sfxBnd_rec)){
			if(in.isMouseButtonDown(0)){
				if(sfxVol_sld.intersects(mouse) && sfxDisplay_rec.contains(mX, 430)){
					sfxVol_sld.setCenterX(mX);
				}else if(sfxDisplay_rec.contains(mX, 430)){
					sfxVol_sld.setCenterX(mX);
				}
			}
		}
		
		if(mouse.intersects(musBnd_rec)){
			if(in.isMouseButtonDown(0)){
				if(musVol_sld.intersects(mouse) && musDisplay_rec.contains(mX, 505)){
					musVol_sld.setCenterX(mX);
				}else if(musDisplay_rec.contains(mX, 505)){
					musVol_sld.setCenterX(mX);
				}
			}
		}
		
		if(mouse.intersects(voiBnd_rec)){
			if(in.isMouseButtonDown(0)){
				if(voiVol_sld.intersects(mouse) && voiDisplay_rec.contains(mX, 580)){
					voiVol_sld.setCenterX(mX);
				}else if(voiDisplay_rec.contains(mX, 580)){
					voiVol_sld.setCenterX(mX);
				}	
			}
		}


		if(in.isMousePressed(0)){
			if(onPart_rec.intersects(mouse)){
				if(options.getParticleStatus()==true){
					options.setParticleStatus(false);
				}else{
					options.setParticleStatus(true);
				}
			}
			if(onFsc_rec.intersects(mouse)){
				
				if(options.getFullscreenStatus()==true){
					options.setFullscreenStatus(false);
				}else{
					options.setFullscreenStatus(true);
				}
				
				try {
					gc.setFullscreen(true);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			if(modBTN_rec.intersects(mouse)){
				stg.enterState(6);
			}
			if(backBTN_rec.intersects(mouse)){
				stg.enterState(3);
			}
		}
	}
	
	private void updateOptionsVals() {
		
		float ratio = 1.0f/100f;
		
		float fxSldr_x = sfxVol_sld.getCenterX();
		options.setFxvol((fxSldr_x-sfxBnd_x)*ratio);
		
		fxSldr_x = musVol_sld.getCenterX(); 
		options.setMusevol((fxSldr_x-sfxBnd_x)*ratio);
		
		fxSldr_x = voiVol_sld.getCenterX();
		options.setVoicevol((fxSldr_x-sfxBnd_x)*ratio);
		

	}

	private void renderLabels(Graphics gfx){
		gfx.setColor(Color.green);
		gfx.drawImage(backBTN_i, 36, 650);
		gfx.drawImage(cfghudBTN_i, 261, 570);
		gfx.drawImage(optLBL_i, 225, 305);
		gfx.drawImage(sfxLBL_i, 36, 400);
		gfx.drawImage(musLBL_i, 36, 475);
		gfx.drawImage(voiLBL_i, 36, 550);
		gfx.drawImage(fscLBL_i, 261, 475);
		gfx.drawImage(partLBL_i, 261,400);
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
			gfx.drawImage(onBTN_i, 410, 416);
		}else{
			gfx.drawImage(offBTN_i, 410, 416);
		}
		
		if(options.getFullscreenStatus()){
			gfx.drawImage(onBTN_i, 410, 491);
		}else{
			gfx.drawImage(offBTN_i, 410, 491);
		}
	}
	
	private String getFormattedValue(float val){
		String form;
		double mod = val*100;
		form = String.valueOf(new DecimalFormat("###").format(mod));
		return form;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
}
