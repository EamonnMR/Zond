package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UILib;
import ents.OptionsEnt;

public class OptionMenuState extends BasicGameState{

	private int id;
	private OptionsEnt options;
	private Rectangle mouse_rec, sfxVol_rec, musVol_rec, voiVol_rec, backBTN_rec, modBTN_rec, comScrn_rec;
	private Rectangle sfxVol_sld, musVol_sld, voiVol_sld, onPart_rec, offPart_rec, onFsc_rec, offFsc_rec;
	private String title, sfxVol_str, musVol_str, voiVol_str, part_str, fullscrn_str, onPart_str, offPart_str, backBTN_str, modBTN_str;
	private UILib uilib;
	
	public OptionMenuState(int i, OptionsEnt ops){
		id = i;
		options = ops;
		uilib = new UILib();
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		//strings
		title = "+-------+\n|Options|\n+-------+";
		sfxVol_str = "+---+\n|SFX|\n+---+";
		musVol_str = "+-----+\n|Music|\n+-----+";
		voiVol_str = "+-----+\n|Voice|\n+-----+";
		part_str = "+---------+\n|Particles|\n+---------+";
		fullscrn_str = "+----------+\n|FullScreen|\n+----------+";
		backBTN_str = "[Back]";
		modBTN_str = "[Config HUD]";
		
		//rectangles
		mouse_rec = new Rectangle(0,0,1,1);
		sfxVol_rec = new Rectangle(105,425,100,11);
		musVol_rec = new Rectangle(105,500,100,11);
		voiVol_rec = new Rectangle(105,575,100,11);
		comScrn_rec = new Rectangle(20, 300, 500, 450);
		backBTN_rec = new Rectangle(35,650,60,22);
		modBTN_rec = new Rectangle(261,567,112,22);
		
		//sliders
		sfxVol_sld = new Rectangle(110,420,10,21);
		musVol_sld = new Rectangle(110,495,10,21);
		voiVol_sld = new Rectangle(110,570,10,21);
		
		//On Button
		onPart_str = "[ON]";
		onPart_rec = new Rectangle(385, 418, 50,22);
		onFsc_rec = new Rectangle(385, 493, 50,22);
		
		//Off Button
		offPart_str = "[OFF]";
		offPart_rec = new Rectangle(449, 418, 50,22);
		offFsc_rec = new Rectangle(449, 493, 50,22);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.setColor(Color.green);

		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 100, 10);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 150, 10);
		gfx.draw(comScrn_rec);
		gfx.draw(modBTN_rec);
		gfx.draw(backBTN_rec);
		
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
		mouse_rec.setX(in.getMouseX());
		mouse_rec.setY(in.getMouseY());
		
		updateCollisions(delta, mouse_rec, arg0, in, arg1);
		
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			arg0.exit();
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void updateCollisions(int delta, Rectangle mouse, GameContainer gc, Input in, StateBasedGame stg){
		if (sfxVol_sld.intersects(mouse)
				&& sfxVol_rec.contains(in.getMouseX(), 430)) {
			if (in.isMouseButtonDown(0)) {
				sfxVol_sld.setCenterX(in.getMouseX());
				options.setFxvol(in.getMouseX());
			}
		}
		if (musVol_sld.intersects(mouse)
				&& musVol_rec.contains(in.getMouseX(), 505)) {
			if (in.isMouseButtonDown(0)) {
				musVol_sld.setCenterX(in.getMouseX());
			}

		}
		if (voiVol_sld.intersects(mouse)
				&& voiVol_rec.contains(in.getMouseX(), 580)) {
			if (in.isMouseButtonDown(0)) {
				voiVol_sld.setCenterX(in.getMouseX());
			}

		}
		
		if(in.isMousePressed(0)){
			if(onPart_rec.intersects(mouse)){
				options.setParticleStatus(true);
			}
			if(offPart_rec.intersects(mouse)){
				options.setParticleStatus(false);
			}
			if(onFsc_rec.intersects(mouse)){
				options.setFullscreenStatus(true);
			}
			if(offFsc_rec.intersects(mouse)){
				options.setFullscreenStatus(false);
			}
			if(modBTN_rec.intersects(mouse)){
				stg.enterState(6);
			}
			if(backBTN_rec.intersects(mouse)){
				stg.enterState(3);
			}
		}
		
	}
	
	private void renderLabels(Graphics gfx){
		gfx.setColor(Color.green);
		gfx.drawString(title,225, 305);
		gfx.drawString(sfxVol_str, 36, 400);
		gfx.drawString(musVol_str, 36, 475);
		gfx.drawString(voiVol_str, 36, 550);
		gfx.drawString(part_str, 261,400);
		gfx.drawString(fullscrn_str,261, 475);
		gfx.drawString("*Only if it looks nice :3*", 261, 525);
		gfx.drawString(modBTN_str, 261, 570);
		gfx.drawString(backBTN_str, 36, 650);
	}
	
	private void renderSliders(Graphics gfx){
		gfx.setColor(Color.green);
		gfx.draw(sfxVol_rec);
		gfx.draw(musVol_rec);
		gfx.draw(voiVol_rec);
		
		gfx.fill(sfxVol_sld);
		uilib.drawStringNextToShape(String.valueOf(options.getFxvol()), sfxVol_rec, 6, 1, gfx);
		gfx.fill(musVol_sld);
		uilib.drawStringNextToShape(String.valueOf(options.getMusevol()), musVol_rec, 6, 1, gfx);
		gfx.fill(voiVol_sld);
		uilib.drawStringNextToShape(String.valueOf(options.getVoicevol()), voiVol_rec, 6, 1, gfx);
	}
	
	private void renderOnOffs(Graphics gfx) {
		gfx.setColor(Color.green);
		if(options.getParticleStatus()){
			gfx.setColor(Color.green);
		}else{
			gfx.setColor(Color.gray);
		}
		gfx.drawString(onPart_str, 390, 420);
		gfx.draw(onPart_rec);
		
		if(options.getParticleStatus()){
			gfx.setColor(Color.gray);
		}else{
			gfx.setColor(Color.green);
		}
		gfx.drawString(offPart_str, 450, 420);
		gfx.draw(offPart_rec);
		
		
		if(options.getFullscreenStatus()){
			gfx.setColor(Color.green);
		}else{
			gfx.setColor(Color.gray);
		}
		gfx.drawString(onPart_str, 390, 495);
		gfx.draw(onFsc_rec);
		
		if(options.getFullscreenStatus()){
			gfx.setColor(Color.gray);
		}else{
			gfx.setColor(Color.green);
		}
		gfx.drawString(offPart_str, 450, 495);
		gfx.draw(offFsc_rec);

	}
	
}
