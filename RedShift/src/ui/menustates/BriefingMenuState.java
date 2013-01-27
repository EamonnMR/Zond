package ui.menustates;

import java.awt.Point;
import java.util.Scanner;

import level.LevelDataModel;
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

public class BriefingMenuState extends BasicGameState {

	private int id;
	private UILib ulib;
	private GameDatabase gdb;
	private boolean ini, backBool, accptBool;
	private SpriteSheetFont greenFont, grayFont;
	private Image backdrop;
	private LevelDataModel ldm;
	private Rectangle mouse_rec, back_rec, accpt_rec;
	
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
		grayFont.drawString(512-((16*12)/2), 36, "=[Zondv1.2]=");
		
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseX()), 100, 10);
//		gfx.drawString(String.valueOf(arg0.getInput().getMouseY()), 200, 10);
		
		
		greenFont.drawString(90, 90, "Mission Briefing:");
		//int yOffset = 110;
		//int lineSpacing = 20; 
		//Scanner multiLineString = new Scanner(ldm.getUIDesc());
		//while(multiLineString.hasNextLine()){
		//	yOffset += lineSpacing;
		//	greenFont.drawString(138, yOffset, multiLineString.nextLine());
		//}
		//greenFont.drawString(138, 110, ldm.getUIDesc());
		if(!(ldm==null)){
			int y = drawMultilineString(greenFont, ldm.getUIDesc(), 138, 110, 20);
			//renderObjectives(gfx);
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
		for(LevelObjective o : ldm.getObjectives().values()){
			y = drawMultilineString(greenFont, i+": " + o.getTltip(), 90, y, 20);
			y = drawMultilineString(greenFont, o.getDesc(), 90, y, 20);
			y += 20;
			i++;
		}
	}

//	private void renderObjectives(Graphics gfx) {
//		int x=90,y=195, i=1;
//		//nuts, strings instead of ints
//		for(Map.Entry<String, LevelObjective> obj : ldm.getObjectives().entrySet()){
//			LevelObjective o = obj.getValue();
//			greenFont.drawString(x, y, i+": "+o.getTltip());
//			if(o.getDesc()!=null && o.getDesc().length() > rowLimiter){
//				y=prepareTextRow(o.getDesc(), y);
//				y+=40;
//			}else {
//				y=+40;
//			}
//			i++;
//		}
//	}

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
				ldm = null;
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
				hang.setLevelToPlay(ldm.getFilename());
				
				arg1.enterState(CoreStateManager.HANGARBAYSTATE);
			}
			accptBool=true;
		}else{
			accptBool=false;
		}
		
		if(i.isKeyPressed(Input.KEY_ESCAPE)){
			i.clearMousePressedRecord();
			i.clearKeyPressedRecord();
			ldm = null;
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

	public void setLevel(LevelDataModel ldm){
		this.ldm = ldm;
	}
	public void customInit(GameDatabase g){
		gdb = g;
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
