package ui.menustates;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UILib;
import ui.hud.HudDataModel;
import core.CoreStateManager;
import core.GameDatabase;

public class ModHudMenuState extends BasicGameState {

	private int id;
	private Rectangle mouse_rec, resetBTN_rec, backBTN_rec, rad_rec, hp_rec, wep_rec, nm_rec, eng_rec, sh_rec;
	private HudDataModel hdm;
	private UILib uil;
	private GameDatabase gdb;
	private Image hp_i, radar_i, wep_i, eng_i, back_i, reset_i, shp_nm_i;
	
	public ModHudMenuState(int i, HudDataModel h, GameDatabase g){
		id = i;
		mouse_rec = new Rectangle(0,0,1,1);
		hdm = h;
		uil =  new UILib();
		gdb = g;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		shp_nm_i = gdb.getIMG("shp_nm");
		nm_rec = new Rectangle(hdm.getShipName_point_mod().x-shp_nm_i.getWidth(),hdm.getShipName_point_mod().y-shp_nm_i.getHeight(),shp_nm_i.getWidth(),shp_nm_i.getHeight());
		
		hp_i = gdb.getIMG("hp_i");
		hp_rec = new Rectangle(hdm.getHp_point_mod().x-hp_i.getWidth(),hdm.getHp_point_mod().y-hp_i.getHeight(),hp_i.getWidth(),hp_i.getHeight());
		
		radar_i = gdb.getIMG("radar_i");
		rad_rec = new Rectangle(hdm.getRadar_point_mod().x-radar_i.getWidth(),hdm.getRadar_point_mod().y-radar_i.getHeight(),radar_i.getWidth(),radar_i.getHeight());
		
		wep_i = gdb.getIMG("wep_i");
		wep_rec = new Rectangle(hdm.getGunName_point_mod().x-wep_i.getWidth(),hdm.getGunName_point_mod().y-wep_i.getHeight(),wep_i.getWidth(),wep_i.getHeight());
		
		eng_i = gdb.getIMG("engine_i");
		eng_rec = new Rectangle(hdm.getEngName_point_mod().x-eng_i.getWidth(),hdm.getEngName_point_mod().y-eng_i.getHeight(),eng_i.getWidth(),eng_i.getHeight());
		
		shp_nm_i = gdb.getIMG("shp_nm");
		sh_rec = new Rectangle(hdm.getShipName_point_mod().x-shp_nm_i.getWidth(),hdm.getShipName_point_mod().y-shp_nm_i.getHeight(),shp_nm_i.getWidth(),shp_nm_i.getHeight());
		
		
		reset_i = gdb.getIMG("resetBTN");
		resetBTN_rec = new Rectangle(405,396,reset_i.getWidth(),reset_i.getHeight());
		
		back_i = gdb.getIMG("bckBTN_n");
		backBTN_rec = new Rectangle(512,396,back_i.getWidth(),back_i.getHeight());
		
		mouse_rec.setCenterX(arg0.getInput().getMouseX());
		mouse_rec.setCenterY(arg0.getInput().getMouseY());
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.setColor(Color.green);
		
		gfx.drawString(String.valueOf(hp_rec.getCenterX()), 200, 10);
		gfx.drawString(String.valueOf(hp_rec.getCenterY()), 250, 10);
		
		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 150, 10);
		
		gfx.drawLine(0, 396, 1023, 396);
		gfx.drawLine(512, 0, 512, 766);
	

//		gfx.draw(hp_rec);
		uil.drawImageAtShapeCenter(gfx, hp_rec, hp_i);
		
//		gfx.draw(sh_rec);
		uil.drawImageAtShapeCenter(gfx, sh_rec, shp_nm_i);
		
//		gfx.draw(rad_rec);
		uil.drawImageAtShapeCenter(gfx, rad_rec, radar_i);
		
//		gfx.draw(eng_rec);
		uil.drawImageAtShapeCenter(gfx, eng_rec, eng_i);
		
//		gfx.draw(wep_rec);
		uil.drawImageAtShapeCenter(gfx, wep_rec, wep_i);
		
		uil.drawImageAtShapeCenter(gfx, backBTN_rec, back_i);
		uil.drawImageAtShapeCenter(gfx, resetBTN_rec, reset_i);
		
		gfx.setColor(Color.darkGray);
		gfx.fill(mouse_rec);
		gfx.draw(mouse_rec);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input maus = arg0.getInput();
		mouse_rec.setCenterX(maus.getMouseX());
		mouse_rec.setCenterY(maus.getMouseY());

			if(hp_rec.intersects(mouse_rec)){
				if(maus.isMouseButtonDown(0)){
					hdm.setHp_point_mod(new Point(maus.getMouseX(),maus.getMouseY()));
					hp_rec.setCenterX(hdm.getHp_point_mod().x);
					hp_rec.setCenterY(hdm.getHp_point_mod().y);
				}
			}
			if(sh_rec.intersects(mouse_rec)){
				if(maus.isMouseButtonDown(0)){
					hdm.setShipName_point_mod(new Point(maus.getMouseX(),maus.getMouseY()));
					sh_rec.setCenterX(hdm.getShipName_point_mod().x);
					sh_rec.setCenterY(hdm.getShipName_point_mod().y);
				}
			}
			if(wep_rec.intersects(mouse_rec)){
				if(maus.isMouseButtonDown(0)){
					hdm.setGunName_point_mod(new Point(maus.getMouseX(),maus.getMouseY()));
					wep_rec.setCenterX(hdm.getGunName_point_mod().x);
					wep_rec.setCenterY(hdm.getGunName_point_mod().y);
				}
			}
			if(rad_rec.intersects(mouse_rec)){
				if(maus.isMouseButtonDown(0)){
					hdm.setRadar_point_mod(new Point(maus.getMouseX(),maus.getMouseY()));
					rad_rec.setCenterX(hdm.getRadar_point_mod().x);
					rad_rec.setCenterY(hdm.getRadar_point_mod().y);
				}
			}
			if(eng_rec.intersects(mouse_rec)){
				if(maus.isMouseButtonDown(0)){
					hdm.setEngName_point_mod(new Point(maus.getMouseX(),maus.getMouseY()));
					eng_rec.setCenterX(hdm.getEngName_point_mod().x);
					eng_rec.setCenterY(hdm.getEngName_point_mod().y);
				}
			}
//			if(hdm.getMinimap_rec().intersects(mouse_rec)){
//				hdm.setMinimap_point_mod(new Point((int)mouse_rec.getCenterX(),(int)mouse_rec.getCenterY()));
//			}
			
		if(maus.isMousePressed(0)){
			if(resetBTN_rec.intersects(mouse_rec)){
				hdm.reset();
			}
			if(backBTN_rec.intersects(mouse_rec)){
				arg1.enterState(CoreStateManager.OPTIONSMENUSTATE);
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void setHDM(HudDataModel hdm){
		this.hdm = hdm;
	}
}
