package ui.menustates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.hud.HudDataModel;

public class ModHudMenuState extends BasicGameState {

	private int id;
	private Rectangle mouse_rec;
	private HudDataModel hdm;
	
	public ModHudMenuState(int i){
		id = i;
		mouse_rec = new Rectangle(0,0,1,1);
		hdm = new HudDataModel();
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
		gfx.draw(hdm.getShipName_rec());
		gfx.draw(hdm.getEngName_rec());
		gfx.draw(hdm.getGunName_rec());
		gfx.draw(hdm.getRadar_rec());
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void setHDM(HudDataModel hdm){
		this.hdm = hdm;
	}
}
