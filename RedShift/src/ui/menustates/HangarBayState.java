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
import core.GameplayState;
import core.GameDatabase;
import core.PlayerClient;
import ents.BasicEngine;
import ents.BasicGun;
import ents.BasicShip;
import ents.EntityFactory;

public class HangarBayState extends BasicGameState {

	private int id;
	private PlayerClient pc;
	private Rectangle acceptBTN_rec, backBTN_rec, mouse;
	private BasicShip displayShip;
	private BasicGun displayGun;
	private BasicEngine displayEngine;
	private GameDatabase gdb;
	private Image mainScn_i, wepScn_i, engScn_i, briefScn_i;
	private UILib ulib;
	private Point center;
	private EntityFactory entFac;
	private HashMap<Integer, BasicGun> guns;
	private HashMap<Integer, BasicEngine> engines;
	private HashMap<Integer, BasicShip> ships;
	private HashMap<String, UIButton> listedButtons;
	private boolean backBool, accptBool;
	private float mx,my;
	
	public HangarBayState(int i, GameDatabase g, PlayerClient p, EntityFactory ef){
		id = i;
		gdb = g;
		pc = p;
		ulib = new UILib();
		center = new Point(512,500);
		entFac = ef;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		//datums and things\
		unspoolData();
		
		iniButtons();
		
		//media stuffs
		mainScn_i = gdb.getIMG("montrBKC");
		wepScn_i = gdb.getIMG("small_scrn");
		engScn_i = gdb.getIMG("small_scrn");
		
		backBTN_rec = new Rectangle(270,697,96,17);
		acceptBTN_rec = new Rectangle(630,697, 120,17);
		
		//mouse...maybe there should be a mouse class next go around?
		mouse = new Rectangle(0,0,1,1);
		mx = arg0.getInput().getMouseX();
		my = arg0.getInput().getMouseY();
		mouse.setCenterX(mx);
		mouse.setCenterY(my);
	}


	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics gfx)
			throws SlickException {
		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
		
		renderMainDisplay(gfx);
		
		renderEngines(gfx);
		
		renderWeapons(gfx);
	}
	
	private void renderMainDisplay(Graphics gfx) {
		ulib.drawImageCenteredOnPoint(gfx, mainScn_i, center);
		
		gdb.getFont("green").drawString(center.x-(6*12/2), 278, "[SHIP]");
				
		int x = 290, y = 308;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicShip.class)){
				u.getRectangle().setX(x);
				u.getRectangle().setY(y);
//				gfx.draw(u.getRectangle());
				
				if(u.isState()){
					gdb.getFont("green").drawString(x, y, "[" + u.getName() + "]");
				}else{
					gdb.getFont("green").drawString(x, y, " "+u.getName()+" ");
				}
				
				if(x>580){
					y+=29;
					x=290;
				}else{
					x = x + u.getName().length()*12+32;
				}
			}
		}
//		gfx.draw(backBTN_rec);
		
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
		ulib.drawImageNextToImage(gfx, mainScn_i, wepScn_i, center, 0, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("wep_i"), 
				new Point(center.x-((mainScn_i.getWidth()/2)+(wepScn_i.getWidth()/2))
						,center.y-(mainScn_i.getHeight()/2-10)));
		
		int y = 308, x = 4;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicGun.class)){
				u.getRectangle().setY(y);
				u.getRectangle().setX(x);
//				gfx.draw(u.getRectangle());
				
				if(u.isState()){
					gdb.getFont("green").drawString(x, y, "[" + u.getName() + "]");
				}else{
					gdb.getFont("green").drawString(x, y, " "+u.getName()+" ");
				}
				
				if(y>408){
					y=308;
					x=135;
				}else{
					y+=17;
				}
			}
		}
		
		gdb.getFont("green").drawString(4, 438, "Damage:  "+displayGun.getProj().getDamage()+"kt");
		gdb.getFont("green").drawString(4, 457, "Cooldown:"+(double)displayGun.getCoolDown()/1000+"sec");
		gdb.getFont("green").drawString(4, 476, "Size:    "+displayGun.getCost());
		
	}

	private void renderEngines(Graphics gfx) {
		ulib.drawImageNextToImage(gfx, mainScn_i, engScn_i, center, 1, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("engine_i"), 
				new Point(center.x+((mainScn_i.getWidth()/2)+(engScn_i.getWidth()/2))
						,center.y-(mainScn_i.getHeight()/2-10)));
		int y = 308;
		for(UIButton u : listedButtons.values()){
			if (u.getThing().getClass().equals(BasicEngine.class)) {
				u.getRectangle().setY(y);
//				gfx.draw(u.getRectangle());

				if (u.isState()) {
					gdb.getFont("green").drawString(760, y,
							"[" + u.getName() + "]");
				} else {
					gdb.getFont("green").drawString(760, y, " "+u.getName()+" ");
				}
				y += 17;
			}
		}
		gdb.getFont("green").drawString(760, 438, "Thrust:"+displayEngine.getThrustX());
		gdb.getFont("green").drawString(760, 457, "Turn:  "+displayEngine.getTurnrate());
		gdb.getFont("green").drawString(760 ,476, "Strafe:"+displayEngine.getStrafeRate());
		gdb.getFont("green").drawString(760, 419, "Size:  "+displayEngine.getCost());
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException{
		Input i = arg0.getInput();
		mouse.setX(i.getMouseX());
		mouse.setY(i.getMouseY());
		
		updateButtons(mouse, i);
		
		if(backBTN_rec.intersects(mouse)){
			backBool=true;
			if(i.isMousePressed(0)){
				arg1.enterState(CoreStateManager.MAINMENUSTATE, new FadeOutTransition(Color.lightGray) , null);
			}
		}else if(acceptBTN_rec.intersects(mouse)){
			if(i.isMousePressed(0)){
				i.clearMousePressedRecord();
				pc.setPlayShip(displayShip);
				pc.getPlayShip().setEngine(displayEngine);
				pc.getPlayShip().setWeapon(displayGun);
				GameplayState gamePlay = (GameplayState)arg1.getState(CoreStateManager.PLAYSTATE);
				gamePlay.setPlayerClient(pc);
//				gamePlay.init(arg0, arg1);
				arg1.enterState(CoreStateManager.PLAYSTATE, new FadeOutTransition(Color.lightGray) , null);
			}
			accptBool=true;
		}else{
			backBool=false;
			accptBool=false;
		}
		
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			arg1.enterState(CoreStateManager.MAINMENUSTATE, new FadeOutTransition(Color.lightGray) , null);
		}
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
			Rectangle r = new Rectangle(4, 0, g.getName().length()*12+24,17);
			UIButton uib = new UIButton(g.getName(), false, g);
			uib.setRectangle(r);
			if(guns.get(0).getName().equals(g.getName())){
				uib.setState(true);
			}
			listedButtons.put(g.getName(), uib);
		}
		
		for(BasicEngine g : engines.values()){
			Rectangle r = new Rectangle(755,0,g.getName().length()*12+24,17);
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

	@Override
	public void enter(GameContainer gc, StateBasedGame stbg){
		mouse.setX(gc.getInput().getMouseX());
		mouse.setY(gc.getInput().getMouseY());
		this.inputStarted();
	}


}
