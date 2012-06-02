package level;

import org.newdawn.slick.Graphics;

/**
 * class that defines behavior inside of levels
 * @author proohr
 * @version 1.o
 */
public class BasicAction {

	private String name;
	private boolean ini;		//has the action been initialized? if false, run ini()
	private boolean update;		//does the action need to be updated? if true, run update()
	private boolean end;		//determines if the action is complete or not
	
	public BasicAction(){
		this.ini = true;
		this.update = false;
		this.end = false;
	}
	
	/**
	 * blank method, but all setup stuff for the action
	 * in here. Ini MUST end with setting the ini and update
	 * booleans to: false, and true respectively otherwise the 
	 * action will never update.
	 */
	public void ini(){
		this.ini = false;
		this.update = true;
	}
	
	/**
	 * the update method for the action; again, override at will
	 * but delta MUST be passed in if you want stuff to iterate.
	 * To kill an Action just set update=false, and end=true to let
	 * the game know when to end it.
	 * @param delta
	 */
	public void update(int delta){
		
	}
	
	/**
	 * the stock render method for the action, is called in ClientGameplay's
	 * render method. 
	 */
	public void render(Graphics gfx){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public boolean isIni() {
		return ini;
	}

	public void setIni(boolean ini) {
		this.ini = ini;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public boolean isDone() {
		return end;
	}

	public void setDone(boolean done) {
		this.end = done;
	}
	
}
