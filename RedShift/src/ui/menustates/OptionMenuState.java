package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ents.OptionsEnt;

public class OptionMenuState extends BasicGameState{

	private int id, next_state;
	private OptionsEnt options;
	private Rectangle mouse_rec, sfxVol_rec, musVol_rec, voiVol_rec, part_rec, fullscrn_rec, comScrn_rec;
	private Rectangle sfxVol_sld, musVol_sld, voiVol_sld;
	private String title, sfxVol_str, musVol_str, voiVol_str, part_str, fullscrn_str;

	
	public OptionMenuState(int i, OptionsEnt ops){
		id = i;
		options = ops;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		//strings
		title = "+-------+\n|Options|\n+-------+";
		sfxVol_str = "+---+\n|SFX|\n+---+";
		musVol_str = "+-----+\n|Music|\n+-----+";
		voiVol_str = "+-----+\n|Voice|\n+-----+";
		
		//rectangles
		mouse_rec = new Rectangle(0,0,1,1);
		sfxVol_rec = new Rectangle(120,425,100,11);
		musVol_rec = new Rectangle(120,500,100,11);
		voiVol_rec = new Rectangle(120,575,100,11);
		part_rec = new Rectangle(0,0,0,0);
		fullscrn_rec = new Rectangle(0,0,0,0);
		comScrn_rec = new Rectangle(20, 300, 500, 450);
		
		//sliders
		sfxVol_sld = new Rectangle(120,420,10,21);
		musVol_sld = new Rectangle(120,495,10,21);
		voiVol_sld = new Rectangle(120,570,10,21);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		String x = String.valueOf(arg0.getInput().getMouseX());
		gfx.drawString(x, 100, 10);
		x = String.valueOf(arg0.getInput().getMouseY());
		gfx.drawString(x, 150, 10);
		
		gfx.setColor(Color.green);
		gfx.drawString(title,225, 305);
		gfx.drawString(sfxVol_str, 36, 400);
		gfx.drawString(musVol_str, 36, 475);
		gfx.drawString(voiVol_str, 36, 550);
		
		
		gfx.draw(sfxVol_rec);
		gfx.draw(musVol_rec);
		gfx.draw(voiVol_rec);
		
		gfx.fill(sfxVol_sld);
		gfx.fill(musVol_sld);
		gfx.fill(voiVol_sld);
		
		gfx.draw(comScrn_rec);
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
		if(in.isMouseButtonDown(0)){
			if(sfxVol_sld.intersects(mouse)){
				sfxVol_sld.setCenterX(in.getMouseX());
			}
		}
	}
	
	public void drawOn(Graphics gfx, double x, double y){
		
	}
	
	public void drawOff(Graphics gfx, double x, double y){
		
	}
	
}
