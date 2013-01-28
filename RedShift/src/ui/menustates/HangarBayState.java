package ui.menustates;

import java.awt.Point;
import java.util.HashMap;

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
import org.newdawn.slick.state.transition.FadeOutTransition;

import ui.UIButton;
import ui.UILib;
import core.CoreStateManager;
import core.GameDatabase;
import core.GameplayState;
import core.PlayerClient;
import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;

public class HangarBayState extends BasicGameState implements MouseListener {

	private int id, currentChoice, rolloverChoice;
	private PlayerClient pc;
	private Rectangle acceptBTN_rec, backBTN_rec, mouse_rec;
	private BasicShip displayShip, tempShip;
	private BasicGun displayGun, tempGun;
	private BasicEngine displayEngine, tempEng;
	private GameDatabase gdb;
	private Image mainScn_i;
	private UILib ulib;
	private HashMap<Integer, BasicGun> guns;
	private HashMap<Integer, BasicEngine> engines;
	private HashMap<Integer, BasicShip> ships;
	private HashMap<String, UIButton> listedButtons;
	private boolean backBool, accptBool, ini;
	private float mx,my;
	private SpriteSheetFont greenFont;
	private SpriteSheetFont grayFont;
	private String levelToPlay;
	
	public HangarBayState(int i){
		id = i;
		ulib = new UILib();
		ini = true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		backBTN_rec = new Rectangle(140,500,96,17);
		acceptBTN_rec = new Rectangle(780,500, 120,17);
		
		//mouse...maybe there should be a mouse class next go around?
		mouse_rec = new Rectangle(0,0,1,1);
		mx = arg0.getInput().getMouseX();
		my = arg0.getInput().getMouseY();
		mouse_rec.setCenterX(mx);
		mouse_rec.setCenterY(my);
	}


	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		ulib.drawImageCenteredOnPoint(gfx, mainScn_i, new Point(512,384));
		grayFont.drawString(512-((16*12)/2), 36, "=[Zondv1.2]=");
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
		
		renderMainDisplay(gfx);
		
		renderEngines(gfx);
		
		renderWeapons(gfx);
		
		renderRollover(gfx);
		
		renderCurrent(gfx);
	}
	
	private void renderMainDisplay(Graphics gfx) {	
		greenFont.drawString(182-(6*12/2), 240, "[SHIP]");
		gfx.setColor(Color.green);
		
		int x = 72, y = 270;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicShip.class)){
				u.getRectangle().setX(x);
				u.getRectangle().setY(y);
				
				if(u.isState()){
					greenFont.drawString(x, y, "[" + u.getName() + "]");
				}else{
					grayFont.drawString(x, y, " "+u.getName()+" ",Color.white);
				}
				
				if(y>320){
					x=190;
					y=270;
				}else{
					y+=20;
				}
			}
		}
		ulib.drawImageCenteredOnPoint(gfx, displayShip.getWireframe(), new Point(509,310));
		ulib.drawImageNextToImage(gfx, displayShip.getWireframe(), displayGun.getWireframe(), new Point(509,310), 2, 10);
		ulib.drawImageNextToImage(gfx, displayShip.getWireframe(), displayEngine.getWireFrame(), new Point(509,310), 3, 10);
		if(backBool==true){
			greenFont.drawString(140, 500, "[(Back)]");
		}else{
			greenFont.drawString(140, 500, " (Back) ");
		}
		if(accptBool==true){
			greenFont.drawString(780, 500, "[(Launch)]");
		}else{
			greenFont.drawString(780, 500, " (Launch) ");
		}
	}
	

	private void renderWeapons(Graphics gfx) {
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("wep_i"), 
				new Point(182,85));
		
		int y = 102, x = 70;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicGun.class)){
				u.getRectangle().setY(y);
				u.getRectangle().setX(x);
				
				if(u.isState()){
					greenFont.drawString(x, y, "[" + gdb.getGun(u.getName()).getUiName() + "]");
				}else{
					grayFont.drawString(x, y, " "+gdb.getGun(u.getName()).getUiName()+" ", Color.white);
				}
				
				if(y>408){
					y=308;
					x=135;
				}else{
					y+=17;
				}
			}
		}
	}

	private void renderEngines(Graphics gfx) {
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("engine_i"), 
				new Point(182,380));
		int y = 410, x=76;
		for(UIButton u : listedButtons.values()){
			if (u.getThing().getClass().equals(BasicEngine.class)) {
				u.getRectangle().setX(x);
				u.getRectangle().setY(y);

				if (u.isState()) {
					greenFont.drawString(76, y,
							"[" + gdb.getEngine(u.getName()).getUiName() + "]");
				} else {
					grayFont.drawString(76, y, " "+gdb.getEngine(u.getName()).getUiName()+" ", Color.white);
				}
				y += 17;
			}
		}
	}

	private void renderCurrent(Graphics gfx) {
		greenFont.drawString(783, 360, "[CURRENT]");
		
		if(currentChoice==0){
			greenFont.drawString(725, 390, "Damage:  "+displayGun.getProj().getDamage()+"kt");
			greenFont.drawString(725, 410, "Cooldown:"+(double)displayGun.getCoolDown()/1000+"sec");
			greenFont.drawString(725, 430, "Weight:    "+displayGun.getWeight());
		}else if(currentChoice==1){
			greenFont.drawString(725, 390, "Name: "+displayShip.getName());
			greenFont.drawString(725, 410, "Health: "+displayShip.getHealth());
			if(displayShip.getFaction()==0){
				greenFont.drawString(725, 430, "Faction: USSR");
			}else if(displayShip.getFaction()==1){
				greenFont.drawString(725, 430, "Faction: NATO");
			}
			greenFont.drawString(725, 450, "Weight: "+displayShip.getTotalWeight());

		}else if(currentChoice==2){
			greenFont.drawString(725, 390, "Thrust:"+displayEngine.getThrustX());
			greenFont.drawString(725, 410, "Turn:  "+displayEngine.getTurnrate());
			greenFont.drawString(725 ,430, "Strafe:"+displayEngine.getStrafeRate());
			greenFont.drawString(725, 450, "Weight:  "+displayEngine.getWeight());
		}
		
	}

	private void renderRollover(Graphics gfx) {
		greenFont.drawString(782, 75, "[COMPARE]");
		if(rolloverChoice==0){
			if(!(tempGun==null)){
				greenFont.drawString(725, 105, "Damage:  "+tempGun.getProj().getDamage()+"kt");
				greenFont.drawString(725, 125, "Cooldown:"+(double)tempGun.getCoolDown()/1000+"sec");
				greenFont.drawString(725, 145, "Weight:    "+tempGun.getWeight());
				greenFont.drawString(72, 590, tempGun.getToolTip());
			}
		}else if(rolloverChoice==1){
			if(!(tempShip==null)){
				greenFont.drawString(725, 105, "Name: "+tempShip.getName());
				greenFont.drawString(725, 125, "Health: "+tempShip.getHealth());
				if(tempShip.getFaction()==0){
					greenFont.drawString(725, 145, "Faction: USSR");
				}else if(tempShip.getFaction()==1){
					greenFont.drawString(725, 145, "Faction: NATO");
				}
				greenFont.drawString(725, 165, "Weight: "+tempShip.getTotalWeight());
				greenFont.drawString(72, 590, tempShip.getToolTip());
			}
		}else if(rolloverChoice==2){
			if(!(tempEng==null)){
				greenFont.drawString(725, 105, "Thrust:"+tempEng.getThrustX());
				greenFont.drawString(725, 125, "Turn:  "+tempEng.getTurnrate());
				greenFont.drawString(725 ,145, "Strafe:"+tempEng.getStrafeRate());
				greenFont.drawString(725, 165, "Weight:  "+tempEng.getWeight());
				greenFont.drawString(72, 590, tempEng.getToolTip());
			}
		}
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException{
		if(ini){
			loadResource();
			ini=false;
		}
		Input i = arg0.getInput();
		mouse_rec.setX(i.getMouseX());
		mouse_rec.setY(i.getMouseY());
		
		updateButtons(mouse_rec, i);
		
		if(backBTN_rec.intersects(mouse_rec)){
			backBool=true;
			if(i.isMousePressed(0)){
				i.clearMousePressedRecord();
				i.clearKeyPressedRecord();
				ini=true;
				arg1.enterState(CoreStateManager.BRIEFING);
			}
		}else if(acceptBTN_rec.intersects(mouse_rec)){
			if(i.isMousePressed(0)){
				gdb.getSound("menuPrelude").stop();
				i.clearMousePressedRecord();
				i.clearKeyPressedRecord();
				pc.setPlayShip(displayShip);
				pc.getPlayShip().setEngine(displayEngine);
				pc.getPlayShip().setWeapon(displayGun);
				ini=true;
				GameplayState gamePlay = (GameplayState)arg1.getState(CoreStateManager.GAMEPLAYSTATE);
				gamePlay.setPlayerClient(pc);
				gamePlay.setLevelPointer(levelToPlay);
				arg1.enterState(CoreStateManager.GAMEPLAYSTATE, new FadeOutTransition(Color.lightGray) , null);
			}
			accptBool=true;
		}else{
			backBool=false;
			accptBool=false;
		}
		if(i.isKeyPressed(Input.KEY_ENTER)){
			gdb.getSound("menuPrelude").stop();
			i.clearMousePressedRecord();
			i.clearKeyPressedRecord();
			pc.setPlayShip(displayShip);
			pc.getPlayShip().setEngine(displayEngine);
			pc.getPlayShip().setWeapon(displayGun);
			ini=true;
			GameplayState gamePlay = (GameplayState)arg1.getState(CoreStateManager.GAMEPLAYSTATE);
			gamePlay.setPlayerClient(pc);
			gamePlay.setLevelPointer(levelToPlay);
			arg1.enterState(CoreStateManager.GAMEPLAYSTATE, new FadeOutTransition(Color.lightGray) , null);
		}
		
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			i.clearMousePressedRecord();
			i.clearKeyPressedRecord();
			arg1.enterState(CoreStateManager.MAINMENUSTATE);
		}
	}

	private void loadResource() {
		//datums and things\
		unspoolData();
		
		iniButtons();
		
		//media stuffs
		mainScn_i = gdb.getIMG("montrBKC");
		greenFont = gdb.getFont("green");
		grayFont = gdb.getFont("gray");
	}

	/**
	 * checks the buttons list for changes and updates the displayship
	 * @param m
	 * @param i
	 */
	private void updateButtons(Rectangle m, Input i) {
		for (UIButton u : listedButtons.values()) {
			if (u.getRectangle().intersects(m)) {
				if (i.isMousePressed(0))
					if (u.getThing().getClass().equals(BasicGun.class)) {
						currentChoice=0;
						if ((BasicGun) u.getThing() != displayGun) {
							u.setState(true);
							listedButtons.get(displayGun.getName()).setState(
									false);
						}
						displayGun = (BasicGun) u.getThing();
					} else if (u.getThing().getClass().equals(BasicEngine.class)) {
						currentChoice=2;
						if ((BasicEngine) u.getThing() != displayEngine) {
							u.setState(true);
							listedButtons.get(displayEngine.getName())
									.setState(false);
						}
						displayEngine = (BasicEngine) u.getThing();
					} else if (u.getThing().getClass().equals(BasicShip.class)) {
						currentChoice=1;
						if ((BasicShip) u.getThing() != displayShip) {
							u.setState(true);
							listedButtons.get(displayShip.getName()).setState(
									false);
						}
						displayShip = (BasicShip) u.getThing();
						displayShip.setEngine(displayEngine);
						displayShip.setWeapon(displayGun);
					}
				if(u.getThing().getClass().equals(BasicGun.class)){
					tempGun = (BasicGun)u.getThing();
					rolloverChoice=0;
				}else if(u.getThing().getClass().equals(BasicEngine.class)){
					tempEng = (BasicEngine)u.getThing();
					rolloverChoice=2;
				}else if(u.getThing().getClass().equals(BasicShip.class)){
					tempShip = (BasicShip)u.getThing();
					rolloverChoice=1;
				}
			}
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	private void unspoolData() {
		int i = 0;
		guns = new HashMap<Integer, BasicGun>();
		for(BasicGun g : pc.getClientGuns().values()){
			guns.put(i, g);
			i++;
		}
		i=0;
		engines = new HashMap<Integer, BasicEngine>();
		for(BasicEngine e : pc.getClientEngines().values()){
			engines.put(i, e);
			i++;
		}
		
		i=0;
		ships = new HashMap<Integer, BasicShip>();
		for(BasicShip s : pc.getClientShips().values()){
			ships.put(i, s);
			i++;
		}
		ships.get(0).setWeapon(guns.get(0));
		ships.get(0).setEngine(engines.get(0));
		pc.setPlayShip(ships.get(0));
		displayShip = pc.getPlayShip();
		displayGun = pc.getPlayShip().getWeapon();
		displayEngine = pc.getPlayShip().getEngine();
	}
	
	private void iniButtons() {
		
		listedButtons = new HashMap<String, UIButton>();
		for(BasicGun g : guns.values()){
			Rectangle r = new Rectangle(4, 0, g.getUiName().length()*12+24,17);
			UIButton uib = new UIButton(g.getName(), false, g);
			uib.setRectangle(r);
			if(guns.get(0).getName().equals(g.getName())){
				uib.setState(true);
			}
			listedButtons.put(g.getName(), uib);
		}
		
		for(BasicEngine g : engines.values()){
			Rectangle r = new Rectangle(755,0,g.getUiName().length()*12+24,17);
			UIButton uib = new UIButton(g.getName(), false, g);
			uib.setRectangle(r);
			if(engines.get(0).getName().equals(g.getName())){
				uib.setState(true);
			}
			listedButtons.put(g.getName(), uib);
		}
		
		for(BasicShip g : ships.values()){
			Rectangle r = new Rectangle(0,308,g.getName().length()*12+24,17);
			UIButton uib = new UIButton(g.getName(), false, g);
			uib.setRectangle(r);
			if(ships.get(0).getName().equals(g.getName())){
				uib.setState(true);
			}
			listedButtons.put(g.getName(), uib);
		}
	}

	public void customInit(GameDatabase g, PlayerClient p){
		gdb = g;
		pc = p;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame stbg){
		mouse_rec.setX(gc.getInput().getMouseX());
		mouse_rec.setY(gc.getInput().getMouseY());
		this.inputStarted();
	}
	
	//public String getLevelToPlay(){
	//	return this.levelToPlay;
	//}

	public void setLevelToPlay(String levelName){
		this.levelToPlay = levelName;
	}
	
	@Override
	public void mouseClicked(int but, int x, int y, int cnt){
		gdb.getSound("click").play();
	}
}
