package ui.menustates;

import java.awt.Point;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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
import ents.EntityFactory;

public class HangarBayState extends BasicGameState {

	private int id;
	private PlayerClient pc;
	private Rectangle acceptBTN_rec, backBTN_rec, mouse_rec, weapons_rec, ships_rec, engines_rec, rollover, current;
	private BasicShip displayShip;
	private BasicGun displayGun;
	private BasicEngine displayEngine;
	private GameDatabase gdb;
	private Image mainScn_i;
	private UILib ulib;
	private Point center;
	private EntityFactory entFac;
	private HashMap<Integer, BasicGun> guns;
	private HashMap<Integer, BasicEngine> engines;
	private HashMap<Integer, BasicShip> ships;
	private HashMap<String, UIButton> listedButtons;
	private boolean backBool, accptBool, ini;
	private float mx,my;
	
	public HangarBayState(int i){
		id = i;
		ulib = new UILib();
		center = new Point(512,500);
		ini = true;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		backBTN_rec = new Rectangle(270,697,96,17);
		acceptBTN_rec = new Rectangle(630,697, 120,17);
		
		//container recs
//		weapons_rec, ships_rec, engines_rec, rollover, current
		weapons_rec = new Rectangle(70, 75, 227, 140);
		ships_rec = new Rectangle(326, 75, 372, 500);
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
		gdb.getFont("gray").drawString(512-((16*12)/2), 36, "=[Redshiftv1.0]=");
		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
		
		renderContainers(gfx);
		
		renderMainDisplay(gfx);
		
		renderEngines(gfx);
		
		renderWeapons(gfx);
		
		renderRollover(gfx);
		
		renderCurrent(gfx);
	}

	private void renderContainers(Graphics gfx){
		gfx.draw(weapons_rec);
		gfx.draw(ships_rec);
	}
	
	private void renderMainDisplay(Graphics gfx) {	
		gdb.getFont("green").drawString(182-(6*12/2), 280, "[SHIP]");
		gfx.setColor(Color.green);
		
		int x = 72, y = 310;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicShip.class)){
				u.getRectangle().setX(x);
				u.getRectangle().setY(y);
//				gfx.draw(u.getRectangle());
				
				if(u.isState()){
					gdb.getFont("green").drawString(x, y, "[" + u.getName() + "]");
				}else{
					gdb.getFont("gray").drawString(x, y, " "+u.getName()+" ",Color.white);
				}
				
				if(y>350){
					x=190;
					y=310;
				}else{
					y+=20;
				}
			}
		}
		
		if(backBool==true){
			gdb.getFont("green").drawString(270, 697, "[(Back)]");
		}else{
			gdb.getFont("green").drawString(270, 697, " (Back) ");
		}
		if(accptBool==true){
			gdb.getFont("green").drawString(630, 697, "[(Accept)]");
		}else{
			gdb.getFont("green").drawString(630, 697, " (Accept) ");
		}
	}
	
	private void renderWeapons(Graphics gfx) {
//		ulib.drawImageNextToImage(gfx, mainScn_i, wepScn_i, center, 0, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("wep_i"), 
				new Point(182,85));
		
		int y = 102, x = 70;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicGun.class)){
				u.getRectangle().setY(y);
				u.getRectangle().setX(x);
//				gfx.draw(u.getRectangle());
				
				if(u.isState()){
					gdb.getFont("green").drawString(x, y, "[" + gdb.getGun(u.getName()).getUiName() + "]");
				}else{
					gdb.getFont("gray").drawString(x, y, " "+gdb.getGun(u.getName()).getUiName()+" ", Color.white);
				}
				
				if(y>408){
					y=308;
					x=135;
				}else{
					y+=17;
				}
			}
		}
		
//		gdb.getFont("green").drawString(4, 438, "Damage:  "+displayGun.getProj().getDamage()+"kt");
//		gdb.getFont("green").drawString(4, 457, "Cooldown:"+(double)displayGun.getCoolDown()/1000+"sec");
//		gdb.getFont("green").drawString(4, 476, "Size:    "+displayGun.getCost());
		
	}

	private void renderEngines(Graphics gfx) {
//		ulib.drawImageNextToImage(gfx, mainScn_i, engScn_i, center, 1, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("engine_i"), 
				new Point(182,440));
		int y = 470, x=76;
		for(UIButton u : listedButtons.values()){
			if (u.getThing().getClass().equals(BasicEngine.class)) {
				u.getRectangle().setX(x);
				u.getRectangle().setY(y);
//				gfx.draw(u.getRectangle());

				if (u.isState()) {
					gdb.getFont("green").drawString(76, y,
							"[" + gdb.getEngine(u.getName()).getUiName() + "]");
				} else {
					gdb.getFont("gray").drawString(76, y, " "+gdb.getEngine(u.getName()).getUiName()+" ", Color.white);
				}
				y += 17;
			}
		}
//		gdb.getFont("green").drawString(760, 438, "Thrust:"+displayEngine.getThrustX());
//		gdb.getFont("green").drawString(760, 457, "Turn:  "+displayEngine.getTurnrate());
//		gdb.getFont("green").drawString(760 ,476, "Strafe:"+displayEngine.getStrafeRate());
//		gdb.getFont("green").drawString(760, 419, "Size:  "+displayEngine.getCost());
	}

	private void renderCurrent(Graphics gfx) {

		
	}

	private void renderRollover(Graphics gfx) {
		gdb.getFont("green").drawString(800, 75, "[INFO]");
		
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
				arg1.enterState(CoreStateManager.MAINMENUSTATE);
			}
		}else if(acceptBTN_rec.intersects(mouse_rec)){
			if(i.isMousePressed(0)){
				i.clearMousePressedRecord();
				i.clearKeyPressedRecord();
				pc.setPlayShip(displayShip);
				pc.getPlayShip().setEngine(displayEngine);
				pc.getPlayShip().setWeapon(displayGun);
				GameplayState gamePlay = (GameplayState)arg1.getState(CoreStateManager.GAMEPLAYSTATE);
				gamePlay.setPlayerClient(pc);
				arg1.enterState(CoreStateManager.GAMEPLAYSTATE, new FadeOutTransition(Color.lightGray) , null);
			}
			accptBool=true;
		}else{
			backBool=false;
			accptBool=false;
		}
		if(i.isKeyPressed(Input.KEY_ENTER)){
			i.clearMousePressedRecord();
			i.clearKeyPressedRecord();
			pc.setPlayShip(displayShip);
			pc.getPlayShip().setEngine(displayEngine);
			pc.getPlayShip().setWeapon(displayGun);
			GameplayState gamePlay = (GameplayState)arg1.getState(CoreStateManager.GAMEPLAYSTATE);
			gamePlay.setPlayerClient(pc);
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
//		wepScn_i = gdb.getIMG("small_scrn");
//		engScn_i = gdb.getIMG("small_scrn");
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
						if ((BasicGun) u.getThing() != displayGun) {
							u.setState(true);
							listedButtons.get(displayGun.getName()).setState(
									false);
						}
						displayGun = (BasicGun) u.getThing();
					} else if (u.getThing().getClass()
							.equals(BasicEngine.class)) {
						if ((BasicEngine) u.getThing() != displayEngine) {
							u.setState(true);
							listedButtons.get(displayEngine.getName())
									.setState(false);
						}
						displayEngine = (BasicEngine) u.getThing();
					} else if (u.getThing().getClass().equals(BasicShip.class)) {
						if ((BasicShip) u.getThing() != displayShip) {
							u.setState(true);
							listedButtons.get(displayShip.getName()).setState(
									false);
						}
						displayShip = (BasicShip) u.getThing();
						displayShip.setEngine(displayEngine);
						displayShip.setWeapon(displayGun);
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

	public void customInit(GameDatabase g, PlayerClient p, EntityFactory ef){
		gdb = g;
		pc = p;
		entFac = ef;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame stbg){
		mouse_rec.setX(gc.getInput().getMouseX());
		mouse_rec.setY(gc.getInput().getMouseY());
		this.inputStarted();
	}


}
