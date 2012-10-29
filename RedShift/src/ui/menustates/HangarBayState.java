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

import ui.UILib;
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
	private HashMap<BasicGun, HashMap<Rectangle, Boolean>> gunBTN;
	private HashMap<BasicEngine, HashMap<Rectangle, Boolean>> engBTN;
	private HashMap<BasicShip, HashMap<Rectangle, Boolean>> shpBTN;
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
		for (BasicShip g : shpBTN.keySet()) {
			for(Rectangle r : shpBTN.get(g).keySet()){
				r.setX(x);
//				gfx.draw(r);
			}
			for(Boolean b : shpBTN.get(g).values()){
				if (b == true) {
					gdb.getFont("green").drawString(x, 308, "[" + g.getName() + "]");
				} else {
					gdb.getFont("green").drawString(x, 308, g.getName());
				}
			}
			x = x + g.getName().length()*12+24;
		}
		
	}
	

	private void renderWeapons(Graphics gfx) {
		ulib.drawImageNextToImage(gfx, mainScn_i, wepScn_i, center, 0, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("wep_i"), 
				new Point(center.x-((mainScn_i.getWidth()/2)+(wepScn_i.getWidth()/2))
						,center.y-(mainScn_i.getHeight()/2-10)));
		
		int y = 308;
		for (BasicGun g : gunBTN.keySet()) {
			for(Rectangle r : gunBTN.get(g).keySet()){
				r.setY(y);
//				gfx.draw(r);
			}
			for(Boolean b : gunBTN.get(g).values()){
				if (b == true) {
					gdb.getFont("green").drawString(4, y, "[" + g.getName() + "]");
				} else {
					gdb.getFont("green").drawString(4, y, g.getName());
				}
			}
			y = y + 15;
		}
	}

	private void renderEngines(Graphics gfx) {
		ulib.drawImageNextToImage(gfx, mainScn_i, engScn_i, center, 1, 0);
		ulib.drawImageCenteredOnPoint(gfx, 
				gdb.getIMG("engine_i"), 
				new Point(center.x+((mainScn_i.getWidth()/2)+(engScn_i.getWidth()/2))
						,center.y-(mainScn_i.getHeight()/2-10)));
		
		int y = 308;
		for (BasicEngine g : engBTN.keySet()) {
			for(Rectangle r : engBTN.get(g).keySet()){
				r.setY(y);
//				gfx.draw(r);
			}
			for(Boolean b : engBTN.get(g).values()){
				if (b == true) {
					gdb.getFont("green").drawString(755, y, "[" + g.getName() + "]");
				} else {
					gdb.getFont("green").drawString(755, y, g.getName());
				}
			}
			y = y + 15;
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException{
		Input i = arg0.getInput();
		mouse.setCenterX(i.getMouseX());
		mouse.setCenterY(i.getMouseY());
		
		updateWeaponButtons(mouse, i);
	}

	
	private void updateWeaponButtons(Rectangle m, Input i) {
		for(HashMap<Rectangle, Boolean> k : gunBTN.values()){
			for(Rectangle r : k.keySet()){
				if(r.intersects(m)&&i.isMousePressed(0)){
					if(k.get(r)==true){
						k.put(r, false);
					}else if(k.get(r)==false){
						k.put(r, true);
					}
				}
			}
		}
		
		for(HashMap<Rectangle, Boolean> k : engBTN.values()){
			for(Rectangle r : k.keySet()){
				if(r.intersects(m)&&i.isMousePressed(0)){
					if(k.get(r)==true){
						k.put(r, false);
					}else if(k.get(r)==false){
						k.put(r, true);
					}
				}
			}
		}
		
		for(HashMap<Rectangle, Boolean> k : shpBTN.values()){
			for(Rectangle r : k.keySet()){
				if(r.intersects(m)&&i.isMousePressed(0)){
					if(k.get(r)==true){
						k.put(r, false);
					}else if(k.get(r)==false){
						k.put(r, true);
					}
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
	}
	
	private void iniButtons() {
		gunBTN = new HashMap<BasicGun, HashMap<Rectangle,Boolean>>();
		for(BasicGun g : guns.values()){
			HashMap<Rectangle, Boolean> p = new HashMap<Rectangle, Boolean>();
			p.put(new Rectangle(4,0,g.getName().length()*12+24,17), false);
			gunBTN.put(g, p);
		}
		
		engBTN = new HashMap<BasicEngine, HashMap<Rectangle,Boolean>>();
		for(BasicEngine g : engines.values()){
			HashMap<Rectangle, Boolean> p = new HashMap<Rectangle, Boolean>();
			p.put(new Rectangle(755,0,g.getName().length()*12+24,17), false);
			engBTN.put(g, p);
		}
		
		shpBTN = new HashMap<BasicShip, HashMap<Rectangle, Boolean>>();
		for(BasicShip g : ships.values()){
			HashMap<Rectangle, Boolean> p = new HashMap<Rectangle, Boolean>();
			p.put(new Rectangle(0,308,g.getName().length()*12+24,17), false);
			shpBTN.put(g, p);
		}
	}


}
