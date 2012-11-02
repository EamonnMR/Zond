package ui.menustates;

import java.awt.Point;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import ui.UIButton;
import ui.UILib;
import core.ClientGameplayState;
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
	private GameDatabase gdb;
	private Image mainScn_i, wepScn_i, engScn_i, briefScn_i, backBTN_i;
	private UILib ulib;
	private Point center;
	private EntityFactory entFac;
	private HashMap<Integer, BasicGun> guns;
	private HashMap<Integer, BasicEngine> engines;
	private HashMap<Integer, BasicShip> ships;
	private HashMap<String, UIButton> listedButtons;
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
		
		backBTN_i = gdb.getIMG("bckBTN_n");
		backBTN_rec = new Rectangle(270,697,backBTN_i.getWidth(),backBTN_i.getHeight());
		
		acceptBTN_rec = new Rectangle(630,697, 120,17);
		
		//mouse...maybe there should be a mouse class next go around?
		mouse = new Rectangle(0,0,1,1);
		mx = arg0.getInput().getMouseX();
		my = arg0.getInput().getMouseY();
		mouse.setCenterX(mx);
		mouse.setCenterY(my);
		
		pc.setPlayShip(entFac.stockMercury());
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
				
		int x = 290;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicShip.class)){
				u.getRectangle().setX(x);
//				gfx.draw(u.getRectangle());
				
				if(u.isState()){
					gdb.getFont("green").drawString(x, 308, "[" + u.getName() + "]");
				}else{
					gdb.getFont("green").drawString(x, 308, u.getName());
				}
				x = x + u.getName().length()*12+32;
			}
		}
		
		gfx.drawImage(backBTN_i, 270,697);
		gdb.getFont("green").drawString(630, 697, "[(Accept)]");
	}
	

	private void renderWeapons(Graphics gfx) {
		ulib.drawImageNextToImage(gfx, mainScn_i, wepScn_i, center, 0, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("wep_i"), 
				new Point(center.x-((mainScn_i.getWidth()/2)+(wepScn_i.getWidth()/2))
						,center.y-(mainScn_i.getHeight()/2-10)));
		
		int y = 308;
		for(UIButton u : listedButtons.values()){
			if(u.getThing().getClass().equals(BasicGun.class)){
				u.getRectangle().setY(y);
//				gfx.draw(u.getRectangle());
				
				if(u.isState()){
					gdb.getFont("green").drawString(4, y, "[" + u.getName() + "]");
				}else{
					gdb.getFont("green").drawString(4, y, u.getName());
				}
				
				y+=17;
			}
		}
		
		gdb.getFont("green").drawString(4, 438, "Cooldown:"+(double)displayShip.getWeapon().getCoolDown()/1000+"sec");
		gdb.getFont("green").drawString(4, 457, "Size:    "+displayShip.getWeapon().getCost());
		gdb.getFont("green").drawString(4, 476, "Weight:  "+displayShip.getWeapon().getWeight()+"kg");
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
					gdb.getFont("green").drawString(760, y, u.getName());
				}
				y += 17;
			}
		}
		gdb.getFont("green").drawString(760, 438, "Thrust:"+displayShip.getEngine().getThrustX());
		gdb.getFont("green").drawString(760, 457, "Turn:"+displayShip.getEngine().getTurnrate());
		gdb.getFont("green").drawString(760, 476, "Strafe:"+displayShip.getEngine().getStrafeRate());
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException{
		Input i = arg0.getInput();
		mouse.setX(i.getMouseX());
		mouse.setY(i.getMouseY());
		
		updateButtons(mouse, i);
		
		if(backBTN_rec.intersects(mouse) && i.isMousePressed(0)){
			arg1.enterState(3);
		}else if(acceptBTN_rec.intersects(mouse) && i.isMousePressed(0)){
			
			pc.setPlayShip(displayShip);
			ClientGameplayState gamePlay = (ClientGameplayState)arg1.getState(1);
			gamePlay.setPlayerClient(pc);
			gamePlay.init(arg0, arg1);
			arg1.enterState(1);
		}
	}

	/**
	 * checks the buttons list for changes and updates the displayship
	 * @param m
	 * @param i
	 */
	private void updateButtons(Rectangle m, Input i) {
		for(UIButton u : listedButtons.values()){
			if(u.getRectangle().intersects(m)  && i.isMousePressed(0)){
				if(u.getThing().getClass().equals(BasicGun.class)){
					if((BasicGun)u.getThing()!=displayShip.getWeapon()){
						u.setState(true);
						listedButtons.get(displayShip.getWeapon().getName()).setState(false);
					}
					displayShip.setWeapon((BasicGun)u.getThing());
				}else if(u.getThing().getClass().equals(BasicEngine.class)){
					if((BasicEngine)u.getThing()!=displayShip.getEngine()){
						u.setState(true);
						listedButtons.get(displayShip.getEngine().getName()).setState(false);
					}
					displayShip.setEngine((BasicEngine)u.getThing());
				}else if(u.getThing().getClass().equals(BasicShip.class)){
					if((BasicShip)u.getThing()!=displayShip){
						u.setState(true);
						listedButtons.get(displayShip.getName()).setState(false);
					}
					displayShip=(BasicShip)u.getThing();		
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
		
		pc.setPlayShip(entFac.buildShip(ships.get(0).getName(), guns.get(0).getName(), engines.get(0).getName()));
		displayShip = pc.getPlayShip();
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


}
