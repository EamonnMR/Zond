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
	private Rectangle mouse_rec, resetBTN_rec, backBTN_rec, rad_rec, hp_rec, wep_rec, eng_rec, sh_rec;
	private HudDataModel hdm;
	private UILib uil;
	private GameDatabase gdb;
	private Image hp_i, radar_i, wep_i, eng_i, shp_nm_i, ship;
	private boolean ini, onReset, onBack;
	
	public ModHudMenuState(int i){
		id = i;
		mouse_rec = new Rectangle(0,0,1,1);
		uil =  new UILib();
		ini = true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		mouse_rec.setCenterX(arg0.getInput().getMouseX());
		mouse_rec.setCenterY(arg0.getInput().getMouseY());
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.setColor(Color.green);
//		
//		gfx.drawString(String.valueOf(hp_rec.getCenterX()), 200, 10);
//		gfx.drawString(String.valueOf(hp_rec.getCenterY()), 250, 10);
//		
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 150, 10);
		
//		gfx.drawLine(0, 396, 1023, 396);
//		gfx.drawLine(512, 0, 512, 766);
		uil.drawImageCenteredOnPoint(gfx, ship, new Point(512,384));
		
		sh_rec.setCenterX(hdm.getShipName_point_mod().x);
		sh_rec.setCenterY(hdm.getShipName_point_mod().y);
		gfx.draw(sh_rec);
		Point sP = uil.imageCenterAtShapeCenter(sh_rec, shp_nm_i);
		gfx.drawImage(shp_nm_i, sP.x, sP.y);
		
		hp_rec.setCenterX(hdm.getHp_point_mod().x);
		hp_rec.setCenterY(hdm.getHp_point_mod().y);
		gfx.draw(hp_rec);
		Point hP = uil.imageCenterAtShapeCenter(hp_rec, hp_i);
		gfx.drawImage(hp_i, hP.x, hP.y);
		
		eng_rec.setCenterX(hdm.getEngName_point_mod().x);
		eng_rec.setCenterY(hdm.getEngName_point_mod().y);
		gfx.draw(eng_rec);
		Point eP = uil.imageCenterAtShapeCenter(eng_rec, eng_i);
		gfx.drawImage(eng_i, eP.x, eP.y);
		
		wep_rec.setCenterX(hdm.getGunName_point_mod().x);
		wep_rec.setCenterY(hdm.getGunName_point_mod().y);
		gfx.draw(wep_rec);
		Point wP = uil.imageCenterAtShapeCenter(wep_rec, wep_i);
		gfx.drawImage(wep_i, wP.x, wP.y);
		
		rad_rec.setCenterX(hdm.getRadar_point_mod().x);
		rad_rec.setCenterY(hdm.getRadar_point_mod().y);
		gfx.draw(rad_rec);
		Point rP = uil.imageCenterAtShapeCenter(rad_rec, radar_i);
		gfx.drawImage(radar_i, rP.x, rP.y);
		
		if(onBack){
			gdb.getFont("green").drawString(569,375, "[(Back)]");
		}else{
			gdb.getFont("green").drawString(569,375, " (Back) ");
		}
		
		if(onReset){
			gdb.getFont("green").drawString(347, 375, "[(Reset)]");
		}else{
			gdb.getFont("green").drawString(347, 375, " (Reset) ");
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(ini){
			loadResource();
			ini=false;
		}
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
			
			if(resetBTN_rec.intersects(mouse_rec)){
				if(maus.isMousePressed(0)){
					hdm.reset();
				}
				onReset = true;
			}else{
				onReset = false;
			}
			if(backBTN_rec.intersects(mouse_rec)){
				if(maus.isMousePressed(0)){
					arg1.enterState(CoreStateManager.OPTIONSMENUSTATE);
				}
				onBack = true;
			}else{
				onBack = false;
			}
	}

	private void loadResource() {
		shp_nm_i = gdb.getIMG("shp_nm");
		sh_rec = new Rectangle(hdm.getShipName_point_mod().x-shp_nm_i.getWidth(),hdm.getShipName_point_mod().y-shp_nm_i.getHeight(),shp_nm_i.getWidth(),shp_nm_i.getHeight());
		
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
		
		ship = gdb.getIMG("mercury");
		ship.rotate(-90);
		
		resetBTN_rec = new Rectangle(347,375,108,19);
		
		backBTN_rec = new Rectangle(569,375,96,19);
		
	}

	public void customInit(HudDataModel h, GameDatabase g){
		hdm = h;
		gdb = g;
	}
	
	@Override
	public int getID() {
		return id;
	}
}
