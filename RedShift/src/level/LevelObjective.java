package level;


/**
 * Basic Objective class that is held in the level data, it sorta has to be separate
 * from the Action and Trigger classes...sry EMR :| it's kinda necessary for now
 * @author proohr
 * @version 1.0
 */
public class LevelObjective {

	//vars
	private String name, 	//name of the objective
			tltip, 			//tltip is for in-hud use 
			desc, 			//desc is for during briefings
			target;			//targetname of trigger to fire off when done
	private boolean complete, active;	//derp
	
	public LevelObjective(String name, String tip, String descript, String targ, boolean state){
		this.name = name;
		this.tltip = tip;
		this.desc = descript;
		this.target = targ;
		this.complete = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTltip() {
		return tltip;
	}

	public void setTltip(String tltip) {
		this.tltip = tltip;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean getComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public void setActive(boolean act){
		this.active = act;
	}
	
	public boolean getActive(){
		return active;
	}
	
	
}
