package ui.menustates;

import java.awt.Point;
import java.util.HashMap;
import java.util.Scanner;

import level.LevelDataModel;
import level.LevelMetaData;
import level.LevelObjective;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UILib;
import core.CoreStateManager;
import core.GameDatabase;
import core.PlayerClient;
import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;
import ents.EntityFactory;

public class BriefingMenuState extends BasicGameState {

	private int id;
	private UILib ulib;
	private GameDatabase gdb;
	private boolean ini, backBool, accptBool;
	private SpriteSheetFont greenFont, grayFont;
	private Image backdrop;
	private LevelMetaData levelMeta;
	private Rectangle mouse_rec, back_rec, accpt_rec;
	private PlayerClient cl;
	private EntityFactory ef;
	
	public BriefingMenuState(int i){
		id = i;
		ini=true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		ulib.drawImageCenteredOnPoint(gfx, backdrop, new Point(512,384));
		grayFont.drawString(512-((16*12)/2), 36, "=[Zondv1.3]=");
		
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
		
		
		greenFont.drawString(90, 90, "Mission Briefing:");
		if(!(levelMeta==null)){
			int y = drawMultilineString(greenFont, levelMeta.getDescrip(), 138, 110, 20);
			renderObjectivesAlt(gfx, y);
		}
		
		if(backBool==true){
			greenFont.drawString(140, 500, "[(Back)]");
		}else{
			greenFont.drawString(140, 500, " (Back) ");
		}
		if(accptBool==true){
			greenFont.drawString(780, 500, "[(Hangar)]");
		}else{
			greenFont.drawString(780, 500, " (Hangar) ");
		}

	}

	private void renderObjectivesAlt(Graphics gfx, int y) {
		int i = 1;
		for(LevelObjective lo : levelMeta.getObjectives().values()){
			y = drawMultilineString(greenFont, i+": " + lo.getTltip(), 90, y, 20); // o.getTltip()
			y = drawMultilineString(greenFont,lo.getDesc(), 90, y, 20);				//o.getDesc(
			y += 20;
			i++;
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(ini){
			loadResources();
			ini=false;
		}
		Input i = arg0.getInput();
		mouse_rec.setCenterX(i.getMouseX());
		mouse_rec.setCenterY(i.getMouseY());
		
		if(back_rec.intersects(mouse_rec)){
			if(i.isMousePressed(0)){
				levelMeta = null;
				arg1.enterState(CoreStateManager.MAINMENUSTATE);
			}
			backBool=true;
		}else{
			backBool=false;
		}
		if(accpt_rec.intersects(mouse_rec)){
			if(i.isMousePressed(0)){
				i.clearMousePressedRecord();
				i.clearKeyPressedRecord();
				HangarBayState hang = (HangarBayState)arg1.getState(CoreStateManager.HANGARBAYSTATE);
				hang.setLevelToPlay(levelMeta.getName());
				spoolClientData(levelMeta);
				arg1.enterState(CoreStateManager.HANGARBAYSTATE);
			}
			accptBool=true;
		}else{
			accptBool=false;
		}
		
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			i.clearMousePressedRecord();
			i.clearKeyPressedRecord();
			levelMeta = null;
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}
	}

	private void loadResources() {
		greenFont = gdb.getFont("green");
		grayFont = gdb.getFont("gray");
		backdrop = gdb.getIMG("montrBKC");
		mouse_rec = new Rectangle(0, 0, 1, 1);
		back_rec = new Rectangle(140, 500,96,20);
		accpt_rec = new Rectangle(780, 500,108,20);
		ulib = new UILib();
	}

	@Override
	public int getID() {
		return id;
	}

	public void setLevel(String ldm){
		this.levelMeta = gdb.getSingleMetaData(ldm);
	}
	public void customInit(GameDatabase g, PlayerClient pc, EntityFactory entFac){
		gdb = g;
		cl = pc;
		ef = entFac;
	}
	
	//method simply converts level client info to tangible info in the player client
	private void spoolClientData(LevelMetaData meta) {
		cl.setClientShips(null);
		cl.setClientGuns(null);
		cl.setClientEngines(null);
		HashMap<String, BasicShip> ships = new HashMap<String, BasicShip>();
		for(String s: meta.getClient_Ships() ){
			ships.put(s, ef.buildShip(s, null, null, false, ""));
		}
		cl.setClientShips(ships);
			
		HashMap<String, BasicGun> gunz = new HashMap<String, BasicGun>();
		for(String s: meta.getClient_Guns() ){
			gunz.put(s, ef.buildGun(s));
		}
		cl.setClientGuns(gunz);
			
		HashMap<String, BasicEngine> engs = new HashMap<String, BasicEngine>();
		for(String s: meta.getClient_Engines()){
			engs.put(s, ef.buildEngine(s));
		}
		cl.setClientEngines(engs);
	}
	
	
	/**
	 * Returns the new Y position so you can add more lines after it without messing them up.
	 */
	private int drawMultilineString(SpriteSheetFont font, String mlString, int x, int y, int dy){
		Scanner multiLineString = new Scanner(mlString);
		while(multiLineString.hasNextLine()){
			y += dy;
			font.drawString(x, y, multiLineString.nextLine().replace('\n', ' '));
		}
		return y;
	}
	
}
