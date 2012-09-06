package ui.menustates;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UILib;
import ui.hud.HudDataModel;

public class ModHudMenuState extends BasicGameState {

	private int id;
	private Rectangle mouse_rec, resetBTN_rec, backBTN_rec;
	private String resetBTN_str, backBTN_str;
	private HudDataModel hdm;
	private UILib uil;
	
	public ModHudMenuState(int i, HudDataModel h){
		id = i;
		mouse_rec = new Rectangle(0,0,1,1);
		hdm = h;
		uil =  new UILib();
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		mouse_rec.setCenterX(arg0.getInput().getMouseX());
		mouse_rec.setCenterY(arg0.getInput().getMouseY());
		resetBTN_str = "RESET";
		resetBTN_rec = new Rectangle(450,396,uil.getStringPixelWidth(resetBTN_str),25);
		
		backBTN_str = "BACK";
		backBTN_rec = new Rectangle(525,396,uil.getStringPixelWidth(backBTN_str),25);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.setColor(Color.green);
		
		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 150, 10);
		
		gfx.drawLine(0, 396, 1023, 396);
		gfx.drawLine(512, 0, 512, 766);
		
		hdm.getShipName_rec().setCenterX(hdm.getShipName_point_mod().x);
		hdm.getShipName_rec().setCenterY(hdm.getShipName_point_mod().y);
		gfx.draw(hdm.getShipName_rec());
		uil.drawStringAtShapeCenter(hdm.getShipName(), hdm.getShipName_rec(), gfx);
		
		hdm.getEngName_rec().setCenterX(hdm.getEngName_point_mod().x);
		hdm.getEngName_rec().setCenterY(hdm.getEngName_point_mod().y);
		gfx.draw(hdm.getEngName_rec());
		uil.drawStringAtShapeCenter(hdm.getEngName(), hdm.getEngName_rec(), gfx);
		
		hdm.getGunName_rec().setCenterX(hdm.getGunName_point_mod().x);
		hdm.getGunName_rec().setCenterY(hdm.getGunName_point_mod().y);
		gfx.draw(hdm.getGunName_rec());
		uil.drawStringAtShapeCenter(hdm.getGunName(), hdm.getGunName_rec(), gfx);
		
		hdm.getRadar_rec().setCenterX(hdm.getRadar_point_mod().x);
		hdm.getRadar_rec().setCenterY(hdm.getRadar_point_mod().y);
		gfx.draw(hdm.getRadar_rec());
		uil.drawStringAtShapeCenter(hdm.getRadar(), hdm.getRadar_rec(), gfx);
		
		hdm.getHp_rec().setCenterX(hdm.getHp_point_mod().x);
		hdm.getHp_rec().setCenterY(hdm.getHp_point_mod().y);
		gfx.draw(hdm.getHp_rec());
		uil.drawStringAtShapeCenter(hdm.getHealth(), hdm.getHp_rec(), gfx);
		
		hdm.getMinimap_rec().setCenterX(hdm.getMinimap_point_mod().x);
		hdm.getMinimap_rec().setCenterY(hdm.getMinimap_point_mod().y);
		gfx.draw(hdm.getMinimap_rec());
		
		gfx.draw(mouse_rec);
		gfx.draw(resetBTN_rec);
		gfx.draw(backBTN_rec);
		
		uil.drawStringAtShapeCenter(resetBTN_str, resetBTN_rec, gfx);
		uil.drawStringAtShapeCenter(backBTN_str, backBTN_rec, gfx);
		
		gfx.setColor(Color.darkGray);
		gfx.fill(mouse_rec);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input maus = arg0.getInput();
		mouse_rec.setCenterX(maus.getMouseX());
		mouse_rec.setCenterY(maus.getMouseY());
		if(maus.isMouseButtonDown(0)){
			if(hdm.getShipName_rec().intersects(mouse_rec)){
				hdm.setShipName_point_mod(new Point((int)mouse_rec.getCenterX(),(int)mouse_rec.getCenterY()));
			}
			if(hdm.getHp_rec().intersects(mouse_rec)){
				hdm.setHp_point_mod(new Point((int)mouse_rec.getCenterX(),(int)mouse_rec.getCenterY()));
			}
			if(hdm.getGunName_rec().intersects(mouse_rec)){
				hdm.setGunName_point_mod(new Point((int)mouse_rec.getCenterX(),(int)mouse_rec.getCenterY()));
			}
			if(hdm.getRadar_rec().intersects(mouse_rec)){
				hdm.setRadar_point_mod(new Point((int)mouse_rec.getCenterX(),(int)mouse_rec.getCenterY()));
			}
			if(hdm.getEngName_rec().intersects(mouse_rec)){
				hdm.setEngName_point_mod(new Point((int)mouse_rec.getCenterX(),(int)mouse_rec.getCenterY()));
			}
			if(hdm.getMinimap_rec().intersects(mouse_rec)){
				hdm.setMinimap_point_mod(new Point((int)mouse_rec.getCenterX(),(int)mouse_rec.getCenterY()));
			}
		}
		
		if(maus.isMousePressed(0)){
			if(resetBTN_rec.intersects(mouse_rec)){
				hdm.reset();
			}
			if(backBTN_rec.intersects(mouse_rec)){
				arg1.enterState(4);
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
